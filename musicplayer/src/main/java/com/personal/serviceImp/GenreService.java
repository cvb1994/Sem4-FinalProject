package com.personal.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.personal.common.FileTypeEnum;
import com.personal.common.FolderTypeEnum;
import com.personal.common.SystemParamEnum;
import com.personal.dto.GenreDto;
import com.personal.dto.GenreReportDto;
import com.personal.dto.PageDto;
import com.personal.dto.ResponseDto;
import com.personal.entity.Genre;
import com.personal.entity.Song;
import com.personal.entity.SystemParam;
import com.personal.mapper.GenreMapper;
import com.personal.musicplayer.specification.GenreSpecification;
import com.personal.repository.GenreRepository;
import com.personal.repository.SystemParamRepository;
import com.personal.service.IGenreService;
import com.personal.utils.CloudStorageUtils;
import com.personal.utils.UploadToDrive;
import com.personal.utils.Utilities;

@Service
public class GenreService implements IGenreService{
	@Autowired
	private GenreRepository genreRepo;
	@Autowired
	private GenreSpecification genreSpec;
	@Autowired
	private SystemParamRepository systemRepo;
	@Autowired
	private GenreMapper genreMapper;
	@Autowired
	Utilities util;
	@Autowired
	private UploadToDrive uploadDrive;
	@Autowired
	private CloudStorageUtils uploadCloudStorage;

	@Override
	public ResponseDto getAll() {
		ResponseDto res = new ResponseDto();
		List<GenreDto> list = genreRepo.findAll().stream().map(genreMapper::entityToDto).collect(Collectors.toList());
		res.setStatus(true);
		res.setContent(list);
		return res;
	}
	
	@Override
	public ResponseDto gets(GenreDto criteria) {
		ResponseDto res = new ResponseDto();
		Page<Genre> page = genreRepo.findAll(genreSpec.filter(criteria) ,PageRequest.of(criteria.getPage(), criteria.getSize(),Sort.by("id").descending()));
		List<GenreDto> list = page.getContent().stream().map(genreMapper::entityToDto).collect(Collectors.toList());
		
		PageDto pageDto = new PageDto();
		pageDto.setContent(list);
		pageDto.setNumber(page.getNumber());
		pageDto.setNumberOfElements(page.getNumberOfElements());
		pageDto.setPage(page.getNumber());
		pageDto.setSize(page.getSize());
		pageDto.setTotalPages(page.getTotalPages());
		if(page.getNumber() == 0) {
			pageDto.setFirst(true);
		}
		if(page.getNumber() == page.getTotalPages()-1) {
			pageDto.setLast(true);
		}
		res.setStatus(true);
		res.setContent(pageDto);
		return res;
	}

	@Override
	public ResponseDto getById(int genreId) {
		ResponseDto res = new ResponseDto();
		GenreDto genre = genreRepo.findById(genreId).map(genreMapper::entityToDto).orElse(null);
		res.setStatus(true);
		res.setContent(genre);
		return res;
	}

	@Override
	public ResponseDto getByName(String name) {
		ResponseDto res = new ResponseDto();
		GenreDto genre = genreRepo.findByName(name).map(genreMapper::entityToDto).orElse(null);
		res.setStatus(true);
		res.setContent(genre);
		return res;
	}

	@Override
	public ResponseDto create(GenreDto model) {
		ResponseDto res = new ResponseDto();
		
		Optional<Genre> checkGenre = genreRepo.findByNameIgnoreCase(model.getName());
		if(checkGenre.isPresent()) {
			res.setStatus(false);
			res.setMessage("Tên đã tồn tại");
			return res;
		}
		
		Genre genre = Optional.ofNullable(model).map(genreMapper::dtoToEntity).orElse(null);
		if(genre == null) {
			res.setMessage("Dữ liệu không đúng");
			res.setStatus(false);
			return res;
		}
		
		if(model.getFile() == null) {
			Optional<SystemParam> optParam = systemRepo.findByParamName(SystemParamEnum.GENRE_IMAGE_DEFAULT.name);
			if(optParam.isPresent()) {
				genre.setAvatar(optParam.get().getParamValue());
			} else {
				res.setMessage("Không tìm thấy ảnh đại diện mặc định");
				res.setStatus(false);
				return res;
			}
		} else {
			String extension = util.getFileExtension(model.getFile());
			if(extension != null) {
				String name = util.nameIdentifier(model.getName(), extension);
//				String imageUrl = uploadDrive.uploadImageFile(model.getFile(),FileTypeEnum.GENRE_IMAGE.name, name);
				String imageUrl = uploadCloudStorage.uploadObject(model.getFile(), name, FolderTypeEnum.GENRE_IMAGE_FOLDER.name);
				if(imageUrl == null) {
					res.setMessage("Lỗi trong quá trình upload file");
					res.setStatus(false);
					return res;
				}
				genre.setAvatar(imageUrl);
			} else {
				res.setMessage("File không hợp lệ");
				res.setStatus(false);
				return res;
			}
		}
		
		
		Genre savedGenre = genreRepo.save(genre);
		if(savedGenre != null) {
			res.setStatus(true);
			res.setMessage("Tạo mới thành công");
			return res;
		}
		
		res.setMessage("Không thể tạo thể loại mới");
		res.setStatus(false);
		return res;
	}
	
	@Override
	public ResponseDto update(GenreDto model) {
		ResponseDto res = new ResponseDto();
		
		Optional<Genre> checkGenre = genreRepo.findByNameIgnoreCaseAndIdNot(model.getName(), model.getId());
		if(checkGenre.isPresent()) {
			res.setStatus(false);
			res.setMessage("Tên đã tồn tại");
			return res;
		}
		
		Genre genre = Optional.ofNullable(model).map(genreMapper::dtoToEntity).orElse(null);
		if(genre == null) {
			res.setMessage("Dữ liệu không đúng");
			res.setStatus(false);
			return res;
		}
		
		if(model.getFile() == null) {
			Optional<Genre> optGenre = genreRepo.findById(model.getId());
			if(optGenre.isPresent()) genre.setAvatar(optGenre.get().getAvatar());
		} else {
			String extension = util.getFileExtension(model.getFile());
			if(extension != null) {
				String name = util.nameIdentifier(model.getName(), extension);
				String imageUrl = uploadCloudStorage.uploadObject(model.getFile(), name, FolderTypeEnum.GENRE_IMAGE_FOLDER.name);
				if(imageUrl == null) {
					res.setMessage("Lỗi trong quá trình upload file");
					res.setStatus(false);
					return res;
				}
				genre.setAvatar(imageUrl);
			} else {
				res.setMessage("File không hợp lệ");
				res.setStatus(false);
				return res;
			}
		}
		
		
		Genre savedGenre = genreRepo.save(genre);
		if(savedGenre != null) {
			res.setStatus(true);
			res.setMessage("Cập nhật thành công");
			return res;
		}
		
		res.setMessage("Không thể cập nhật");
		res.setStatus(false);
		return res;
	}

	@Override
	public ResponseDto delete(int genreId) {
		ResponseDto res = new ResponseDto();
		Optional<Genre> optGenre = genreRepo.findById(genreId);
		if(optGenre.isPresent()) {
			Genre genre = optGenre.get();
			genre.setDeleted(true);
			genreRepo.save(genre);
			res.setStatus(true);
			res.setMessage("Xóa thành công");
			return res;
		}
		
		res.setMessage("Không tìm thấy thể loại");
		res.setStatus(false);
		return res;
	}

	@Override
	public ResponseDto getAllOrderByName() {
		ResponseDto res = new ResponseDto();
		List<GenreDto> list = genreRepo.findAll(Sort.by(Sort.Direction.ASC, "name")).stream().map(genreMapper::entityToDto).collect(Collectors.toList());
		res.setStatus(true);
		res.setContent(list);
		return res;
	}

	@Override
	public Long countGenre() {
		return genreRepo.count();
	}

	@Override
	public ResponseDto genreReport() {
		ResponseDto res = new ResponseDto();
		List<GenreReportDto> listReport = new ArrayList<>();
		
		List<Genre> list = genreRepo.findAll();
		for(Genre g : list) {
			List<Song> listSong = g.getSongs();
			int total = 0;
			for(Song s : listSong) {
				total = total + s.getListenCountReset();
			}
			GenreReportDto dto = new GenreReportDto();
			dto.setName(g.getName());
			dto.setCount(total);
			
			listReport.add(dto);
		}

		res.setContent(listReport);
		res.setStatus(true);
		return res;
	}
}
