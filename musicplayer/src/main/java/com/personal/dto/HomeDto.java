package com.personal.dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HomeDto {
    private List<AlbumDto> banner;
    private List<ArtistDto> listArtist;
    private List<SongDto>  listTrending;
    private List<AlbumDto> listAlbum;
    private AlbumDto top1Album;
    private List<SongDto> newlySong;
    private List<ArtistDto> topArtist;
    private List<AlbumDto> newlyAlbum;

}
