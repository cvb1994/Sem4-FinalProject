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

import com.personal.dto.PageDto;
import com.personal.dto.PlayListDto;
import com.personal.dto.ResponseDto;
import com.personal.entity.PlayList;
import com.personal.entity.Song;
import com.personal.entity.User;
import com.personal.repository.PlayListRepository;
import com.personal.repository.SongRepository;
import com.personal.repository.UserRepository;
import com.personal.service.IPlayListService;
import com.personal.mapper.PlayListMapper;

@Service
public class PlayListService implements IPlayListService{
	@Autowired
	private PlayListRepository playListRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private SongRepository songRepo;
	@Autowired
	private PlayListMapper playListMapper;
	
	private final String PLAYLIST_LIKE = "playlist-like";

	@Override
	public ResponseDto findByUser(int userId) {
		ResponseDto res = new ResponseDto();
		List<PlayListDto> list = playListRepo.findByUser(userId).stream().map(playListMapper::entityToDto).collect(Collectors.toList());
		res.setStatus(true);
		res.setContent(list);
		return res;
	}

	@Override
	public ResponseDto gets(PlayListDto criteria) {
		ResponseDto res = new ResponseDto();
		Page<PlayList> page = playListRepo.findAll(PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by("id").descending()));
		List<PlayListDto> list = page.getContent().stream().map(playListMapper::entityToDto).collect(Collectors.toList());
		
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
	public ResponseDto getById(int id) {
		ResponseDto res = new ResponseDto();
		PlayListDto dto =  playListRepo.findById(id).map(playListMapper::entityToDto).orElse(null);
		res.setStatus(true);
		res.setContent(dto);
		return res;
	}

	@Override
	public ResponseDto create(PlayListDto model) {
		ResponseDto res = new ResponseDto();
		PlayList playList = Optional.ofNullable(model).map(playListMapper::dtoToEntity).orElse(null);
		if(playList == null) {
			res.setMessage("Dữ liệu không đúng");
			res.setStatus(false);
			return res;
		}
		
		Optional<User> optUser = userRepo.findById(model.getUserId());
		if(optUser.isPresent()) {
			playList.setUser(optUser.get());
		} else {
			res.setMessage("Không tìm thấy user");
			res.setStatus(false);
			return res;
		}
		
		List<Song> list = new ArrayList<>();
		if(model.getListSongIds() != null && model.getListSongIds().size() > 0) {
			for(Integer i : model.getListSongIds()) {
				Optional<Song> optSong = songRepo.findById(i);
				if(optSong.isPresent()) {
					list.add(optSong.get());
				}
			}
			playList.setSongs(list);
		}
		
		PlayList savedPlayList = playListRepo.save(playList);
		if(savedPlayList != null) {
			res.setStatus(true);
			res.setMessage("Tạo mới thành công");
			res.setContent(savedPlayList.getId());
			return res;
		}
		res.setMessage("Không thể tạo mới playlist");
		res.setStatus(false);
		return res;
	}
	
	@Override
	public ResponseDto update(PlayListDto model) {
		ResponseDto res = new ResponseDto();
		PlayList playList = Optional.ofNullable(model).map(playListMapper::dtoToEntity).orElse(null);
		if(playList == null) {
			res.setMessage("Dữ liệu không đúng");
			res.setStatus(false);
			return res;
		}
		
		Optional<User> optUser = userRepo.findById(model.getUserId());
		if(optUser.isPresent()) {
			playList.setUser(optUser.get());
		} else {
			res.setMessage("Không tìm thấy user");
			res.setStatus(false);
			return res;
		}
		
		List<Song> list = new ArrayList<>();
		if(model.getListSongIds().size() > 0) {
			for(Integer i : model.getListSongIds()) {
				Optional<Song> optSong = songRepo.findById(i);
				if(optSong.isPresent()) {
					list.add(optSong.get());
				}
			}
			playList.setSongs(list);
		}
		
		PlayList savedPlayList = playListRepo.save(playList);
		if(savedPlayList != null) {
			res.setStatus(true);
			res.setMessage("Cập nhật thành công");
			return res;
		}
		res.setMessage("Không thể cập nhật playlist");
		res.setStatus(false);
		return res;
	}

	@Override
	public ResponseDto delete(int id) {
		ResponseDto res = new ResponseDto();
		Optional<PlayList> optPlayList = playListRepo.findById(id);
		if(optPlayList.isPresent()) {
			playListRepo.delete(optPlayList.get());
			res.setStatus(true);
			res.setMessage("Xóa thành công");
			return res;
		}
		res.setMessage("Không tìm thấy playlist");
		res.setStatus(false);
		return res;
	}

	@Override
	public ResponseDto addRemoveToFavoritePlaylist(PlayListDto model) {
		ResponseDto res = new ResponseDto();
		
		Song song = songRepo.findById(model.getSongId()).get();
		
		Optional<PlayList> optPlayList = playListRepo.findByUserIdAndName(model.getUserId(), PLAYLIST_LIKE);
		if(optPlayList.isPresent()) {
			PlayList playlist = optPlayList.get();
			List<Song> listSong = playlist.getSongs();
			if(listSong.contains(song)) {
				listSong.remove(song);
			} else {
				listSong.add(song);
			}
			playlist.setSongs(listSong);
			playListRepo.save(playlist);
		} else {
			PlayList playlist = new PlayList();
			playlist.setName(PLAYLIST_LIKE);
			
			User user = userRepo.findById(model.getUserId()).get();
			playlist.setUser(user);
			
			List<Song> list = new ArrayList<>();
			list.add(song);
			playlist.setSongs(list);
			playListRepo.save(playlist);
		}
		
		res.setStatus(true);
		return res;
	}

	@Override
	public ResponseDto getFavoritePlaylistUser(int userId) {
		ResponseDto res = new ResponseDto();
		Optional<PlayList> optPlayList = playListRepo.findByUserIdAndName(userId, PLAYLIST_LIKE);
		if(optPlayList.isPresent()) {
			PlayListDto playlistDto = optPlayList.map(playListMapper::entityToDto).orElse(null);
			res.setStatus(true);
			res.setContent(playlistDto);
			return res;
		}
		
		res.setStatus(false);
		res.setMessage("Chưa có danh sách yêu thích");
		return res;
	}

	@Override
	public ResponseDto checkFavoriteSong(int userId, int songId) {
		ResponseDto res = new ResponseDto();
		Song song = songRepo.findById(songId).get();
		Optional<PlayList> optPlayList = playListRepo.findByUserIdAndName(userId, PLAYLIST_LIKE);
		if(optPlayList.isPresent()) {
			PlayList playList = optPlayList.get();
			List<Song> listSong = playList.getSongs();
			if(listSong.contains(song)) {
				res.setStatus(true);
			} else {
				res.setStatus(false);
			}
			
			return res;
		}
		res.setStatus(false);
		return res;
	}

	@Override
	public ResponseDto addtoPlaylist(int songId, int playlistId) {
		ResponseDto res = new ResponseDto();
		PlayList playlist = playListRepo.findById(playlistId).get();
		Song song = songRepo.findById(songId).get();
		List<Song> listSong = playlist.getSongs();
		if(listSong.contains(song)) {
			res.setStatus(false);
			res.setMessage("Bài Hát Đã Có Trong Playlist");
			return res;
		} 
		listSong.add(song);
		
		playlist.setSongs(listSong);
		playListRepo.save(playlist);
		res.setStatus(true);
		res.setMessage("Đã Thêm Vào PlayList");
		return res;
	}

	@Override
	public ResponseDto removeFromPlaylist(int songId, int playlistId) {
		ResponseDto res = new ResponseDto();
		PlayList playlist = playListRepo.findById(playlistId).get();
		Song song = songRepo.findById(songId).get();
		List<Song> listSong = playlist.getSongs();
		if(listSong.contains(song)) {
			listSong.remove(song);
			playlist.setSongs(listSong);
			playListRepo.save(playlist);
			res.setStatus(true);
			res.setMessage("Đã Xóa Khỏi PlayList");
			return res;
		}
		
		res.setStatus(false);
		res.setMessage("Bài Hát Không Có Trong Playlist");
		return res;
	}

}
