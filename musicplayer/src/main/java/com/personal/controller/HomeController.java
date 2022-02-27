package com.personal.controller;

import com.personal.dto.*;
import com.personal.service.IAlbumService;
import com.personal.service.IArtistService;
import com.personal.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class HomeController {
    @Autowired
    private IAlbumService iAlbumService;

    @Autowired
    private ISongService iSongService;

    @Autowired
    private IArtistService iArtistService;


    @GetMapping("home")
    public ResponseDto getDataHomePage(){
        ResponseDto res = new ResponseDto();
        List<SongDto> listTrending = iSongService.ListTrending();
        List<AlbumDto> banner = iAlbumService.getTop5ByModifiedDateDesc();
        List<AlbumDto> listNewBanner =  iAlbumService.getTop10ByModifiedDateDesc();
        List<ArtistDto> listNewArtist = iArtistService.getTop10ByModifiedDateDesc();
        HomeDto data =  new HomeDto(banner, listNewArtist, listTrending, listNewBanner);
        res.setContent(data);
        return res;
    }


}
