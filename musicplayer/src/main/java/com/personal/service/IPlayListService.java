package com.personal.service;

import com.personal.dto.PlayListDto;
import com.personal.dto.ResponseDto;

public interface IPlayListService {
	public ResponseDto findByUser(int userId);
	public ResponseDto gets(PlayListDto criteria);
	public ResponseDto getById(int id);
	public ResponseDto create(PlayListDto model);
	public ResponseDto update(PlayListDto model);
	public ResponseDto delete(int id);
	public ResponseDto addRemoveToFavoritePlaylist(PlayListDto model);
	public ResponseDto getFavoritePlaylistUser(int userId);
	public ResponseDto checkFavoriteSong(int userId, int songId);
	public ResponseDto addtoPlaylist(int songId, int playlistId);
	public ResponseDto removeFromPlaylist(int songId, int playlistId);
	
}
