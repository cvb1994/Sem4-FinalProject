package com.personal.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DashboardDto {
    private List<SongDto> listTrending;
    private Long totalArtist;
    private Long totalAlbum;
    private Long totalGenre;
    private Long totalSong;
}
