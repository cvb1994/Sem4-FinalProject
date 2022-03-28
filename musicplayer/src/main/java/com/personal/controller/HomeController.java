package com.personal.controller;

import com.personal.dto.*;
import com.personal.service.IAlbumService;
import com.personal.service.IArtistService;
import com.personal.service.ISongService;
import com.personal.serviceImp.PaymentService;
import com.personal.serviceImp.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "*")
public class HomeController {
    @Autowired
    private IAlbumService iAlbumService;

    @Autowired
    private ISongService iSongService;

    @Autowired
    private IArtistService iArtistService;
    
    @Autowired
    private PaymentService paymentSer;
    
    @Autowired
    private UserService userSer;
    
    @GetMapping("home")
    public ResponseDto getDataHomePage(Authentication auth){
        ResponseDto res = new ResponseDto();
        List<SongDto> listTrending = iSongService.ListTrending(auth);
        List<AlbumDto> banner = iAlbumService.getTop5ByModifiedDateDesc();
        List<AlbumDto> listNewBanner =  iAlbumService.getTop10ByModifiedDateDesc();
        List<ArtistDto> listNewArtist = iArtistService.getTop10ByModifiedDateDesc();
        HomeDto data =  new HomeDto();
        data.setBanner(banner);
        data.setListArtist(listNewArtist);
        data.setListTrending(listTrending);
        data.setListAlbum(listNewBanner);
        res.setContent(data);
        return res;
    }
    
    @GetMapping("/topTrending")
    public ResponseDto getTopTrending() {
    	ResponseDto res = new ResponseDto();
    	List<SongDto> listTrending = iSongService.ListTrending(null);
    	res.setContent(listTrending);
    	return res;
    }
    
    @GetMapping("web/home")
    public ResponseDto getWebHome(Authentication auth) {
    	ResponseDto res = new ResponseDto();
    	AlbumDto top1Album = iAlbumService.top1Album();
    	List<SongDto> listTrending = iSongService.ListTop15Trending(auth);
    	List<SongDto> newlySong = iSongService.newlySong();
    	List<ArtistDto> topArtist = iArtistService.getTopArtist();
    	List<AlbumDto> newlyAlbum =  iAlbumService.getTop10ByModifiedDateDesc();
    	HomeDto data =  new HomeDto();
    	data.setListTrending(listTrending);
    	data.setTop1Album(top1Album);
    	data.setNewlySong(newlySong);
    	data.setTopArtist(topArtist);
    	data.setNewlyAlbum(newlyAlbum);
    	res.setContent(data);
    	return res;
    }
    
    @GetMapping("web/search")
    public ResponseDto searchWeb(@RequestParam("keyword") String search, Authentication auth) {
    	ResponseDto res = new ResponseDto();
    	List<SongDto> listSong = iSongService.searchSong(search, auth);
    	List<ArtistDto> listArtist = iArtistService.searchArtist(search);
    	List<AlbumDto> listAlbum = iAlbumService.searchAlbum(search);
    	HomeDto data =  new HomeDto();
    	data.setSearchArtist(listArtist);
    	data.setSearchSong(listSong);
    	data.setSearchAlbum(listAlbum);
    	res.setContent(data);
    	return res;
    }

    @GetMapping("/admin/dashboard")
    public ResponseDto getDataDashboard(Authentication auth){
        ResponseDto res = new ResponseDto();
        List<SongDto> listTrending = iSongService.ListTrending(auth);
        Long totalSong = iSongService.countSong();
        Long totalAlbum = iAlbumService.coutAlbum();
        Long totalUser = userSer.countUser();
        
        int newSong = iSongService.countSongNewInMonth();
        int newAlbum = iAlbumService.countAlbumNewInMonth();
        int newUser = userSer.countUserNewInMonth();
        int newPayment = paymentSer.countPaymentNewInMonth();
        DashboardDto data = new DashboardDto();
        data.setListTrending(listTrending);
        data.setTotalAlbum(totalAlbum);
        data.setTotalSong(totalSong);
        data.setTotalUser(totalUser);
        data.setNewAlbumInMonth(newAlbum);
        data.setNewSongInMonth(newSong);
        data.setNewUserInMonth(newUser);
        data.setNewPaymentInMonth(newPayment);
        res.setContent(data);
        return res;
    }
    
    @GetMapping("/admin/profit-report")
    public ResponseEntity<?> getProfitReport(@RequestParam("monthAmount") int monthAmount) {
    	return ResponseEntity.ok(paymentSer.profitInMonths(monthAmount));
    }
}
