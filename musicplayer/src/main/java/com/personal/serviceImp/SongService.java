package com.personal.serviceImp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.personal.common.FileTypeEnum;
import com.personal.common.RoleEnum;
import com.personal.common.SystemParamEnum;
import com.personal.dto.PageDto;
import com.personal.dto.ResponseDto;
import com.personal.dto.SongDto;
import com.personal.entity.Album;
import com.personal.entity.Artist;
import com.personal.entity.Genre;
import com.personal.entity.Payment;
import com.personal.entity.Song;
import com.personal.entity.SystemParam;
import com.personal.entity.User;
import com.personal.mapper.SongMapper;
import com.personal.repository.AlbumRepository;
import com.personal.repository.ArtistRepository;
import com.personal.repository.GenreRepository;
import com.personal.repository.PaymentRepository;
import com.personal.repository.SongRepository;
import com.personal.repository.SystemParamRepository;
import com.personal.repository.UserRepository;
import com.personal.service.ISongService;
import com.personal.utils.CloudStorageUtils;
import com.personal.utils.UploadToDrive;
import com.personal.utils.Utilities;

@Service
public class SongService implements ISongService{
	@Autowired
	private SongRepository songRepo;
	@Autowired
	private AlbumRepository	albumRepo;
	@Autowired
	private ArtistRepository artistRepo;
	@Autowired
	private GenreRepository genreRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PaymentRepository paymentRepo;
	@Autowired
	private SongMapper songMapper;
	@Autowired
	private SystemParamRepository systemRepo;
	@Autowired
	private Utilities util;
	@Autowired
	private UploadToDrive uploadDrive;
	@Autowired
	private CloudStorageUtils uploadCloudStorage;
	
	@Override
	public List<SongDto> getAll() {
		List<SongDto> list = songRepo.findAll().stream().map(songMapper::entityToDto).collect(Collectors.toList());
		return list;
	}
	
	@Override
	public PageDto gets(SongDto criteria, Authentication auth) {
		Page<Song> page = songRepo.findAll(PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by("id").descending()));
		List<SongDto> list = page.getContent().stream().map(songMapper::entityToDto).collect(Collectors.toList());
		
		if(auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(RoleEnum.USER.name))) {
			User user = userRepo.findByUsername(auth.getName()).get();
			Optional<Payment> optPayment = paymentRepo.findByUserIdAndStatusActiveTrue(user.getId());
			if(!optPayment.isPresent()) {
				list.stream().forEach(s -> {
					if(s.getVipOnly()) {
						s.setMediaUrl(null);
					}
				});
			}
		} else if(auth == null) {
			list.stream().forEach(s -> {
				if(s.getVipOnly()) {
					s.setMediaUrl(null);
				}
			});
		}
		
		PageDto pageDto = new PageDto();
		pageDto.setContent(list);
		pageDto.setNumber(page.getNumber());
		pageDto.setNumberOfElements(page.getNumberOfElements());
		pageDto.setPage(page.getNumber());
		pageDto.setSize(page.getSize());
		pageDto.setTotalPages(page.getTotalPages());
		return pageDto;
	}

	@Override
	public SongDto getById(int songId, Authentication auth) {
		SongDto song = songRepo.findById(songId).map(songMapper::entityToDto).orElse(null);
		if(auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(RoleEnum.USER.name))) {
			if(song.getVipOnly()) {
				User user = userRepo.findByUsername(auth.getName()).get();
				Optional<Payment> optPayment = paymentRepo.findByUserIdAndStatusActiveTrue(user.getId());
				if(!optPayment.isPresent()) {
					song.setMediaUrl(null);
					return song;
				}
			}
		} else if(auth == null) {
			if(song.getVipOnly()) {
				song.setMediaUrl(null);
				return song;
			}
		}
		
		return song;
	}

	@Override
	public ResponseDto save(SongDto model) {
		ResponseDto res = new ResponseDto();
		Song song = Optional.ofNullable(model).map(songMapper::dtoToEntity).orElse(null);
		if(song == null) {
			res.setError("Dữ liệu không đúng");
			res.setIsSuccess(false);
			return res;
		}
		boolean isAlbumExist = false;
		
		if(model.getAlbumId() != 0) {
			Optional<Album> optAlbum = albumRepo.findById(model.getAlbumId());
			if(optAlbum.isPresent()) {
				song.setAlbum(optAlbum.get());
				song.setImage(optAlbum.get().getAvatar());
				isAlbumExist = true;
			}
		} 
		
		List<Artist> listArtist = new ArrayList<>();
		for(Integer i : model.getArtistIds()) {
			Optional<Artist> artist = artistRepo.findById(i.intValue());
			if(artist.isPresent()) listArtist.add(artist.get());
		}
		if(listArtist.size() == 0) {
			res.setError("Không tìm thấy nghệ sĩ");
			res.setIsSuccess(false);
			return res;
		} else {
			song.setArtists(listArtist);
		}
		
		List<Genre> listGenre = new ArrayList<>();
		for(Integer i : model.getGenreIds()) {
			Optional<Genre> genre = genreRepo.findById(i.intValue());
			if(genre.isPresent()) listGenre.add(genre.get());
		}
		if(listGenre.size() == 0) {
			res.setError("Không tìm thấy thể loại");
			res.setIsSuccess(false);
			return res;
		} else {
			song.setGenres(listGenre);
		}
		
		if(model.getFile() == null && !isAlbumExist) {
			Optional<SystemParam> optParam = systemRepo.findByParamName(SystemParamEnum.ALBUM_IMAGE_DEFAULT.name);
			if(optParam.isPresent()) {
				song.setImage(optParam.get().getParamValue());
			} else {
				res.setError("Không tìm thấy ảnh đại diện mặc định");
				res.setIsSuccess(false);
				return res;
			}
		} else if(!isAlbumExist){
			String extension = util.getFileExtension(model.getFile());
			if(extension != null) {
				String name = util.nameIdentifier(model.getTitle(), extension);
				String imageUrl = uploadDrive.uploadImageFile(model.getFile(),FileTypeEnum.SONG_IMAGE.name, name);
				if(imageUrl == null) {
					res.setError("Lỗi trong quá trình upload file");
					res.setIsSuccess(false);
					return res;
				}
				song.setImage(imageUrl);
			} else {
				res.setError("File không hợp lệ");
				res.setIsSuccess(false);
				return res;
			}
		}
		
		if(model.getMp3() == null) {
			res.setError("File không tồn tại");
			res.setIsSuccess(false);
			return res;
		}
		String extension = util.getFileExtension(model.getMp3());
		if(extension != null) {
			String name = util.nameIdentifier(model.getTitle(), extension);
			String audioUrl = uploadCloudStorage.uploadObject(model.getMp3(), name);
			if(audioUrl == null) {
				res.setError("Lỗi trong quá trình upload file");
				res.setIsSuccess(false);
				return res;
			}
			song.setMediaUrl(audioUrl);
		} else {
			res.setError("File không hợp lệ");
			res.setIsSuccess(false);
			return res;
		}
		
		Song savedSong = songRepo.save(song);
		if(savedSong != null) {
			res.setIsSuccess(true);
			return res;
		}
		
		res.setError("Không thể tạo mới bài hát");
		res.setIsSuccess(false);
		return res;
		
	}

	@Override
	public ResponseDto delete(int songId) {
		ResponseDto res = new ResponseDto();
		Optional<Song> optSong = songRepo.findById(songId);
		if(optSong.isPresent()) {
			Song song = optSong.get();
			song.setDeleted(true);
			songRepo.save(song);
			res.setIsSuccess(true);
			return res;
		}
		
		res.setError("Không thể tìm thấy bài hát");
		res.setIsSuccess(false);
		return res;
	}
	
	@Override
	@Transactional
	public void increase(int songId) {
		Optional<Song> optSong = songRepo.findById(songId);
		if(optSong.isPresent()) {
			Song song = optSong.get();
			song.setListenCount(song.getListenCount() + 1);
			song.setListenCountReset(song.getListenCountReset() + 1);
			songRepo.save(song);
		}
	}
	
	@Override
	public List<SongDto> getTopSongByGenre(String genreName){
//		Optional<Genre> optGenre = genreRepo.findByNameIgnoreCase(genreName);
//		if(optGenre.isPresent()) {
//			return songRepo.findTop10ByGenreOrderByListenCountReset(optGenre.get()).stream().map(songMapper::entityToDto).collect(Collectors.toList());
//		}
		return null;
	}

}
