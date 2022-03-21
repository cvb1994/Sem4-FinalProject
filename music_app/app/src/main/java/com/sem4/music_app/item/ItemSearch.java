package com.sem4.music_app.item;

import java.util.List;

public class ItemSearch {
    private List<ItemAlbums> searchAlbum;
    private List<ItemArtist> searchArtist;
    private List<ItemSong> searchSong;

    public List<ItemAlbums> getSearchAlbum() {
        return searchAlbum;
    }

    public void setSearchAlbum(List<ItemAlbums> searchAlbum) {
        this.searchAlbum = searchAlbum;
    }

    public List<ItemArtist> getSearchArtist() {
        return searchArtist;
    }

    public void setSearchArtist(List<ItemArtist> searchArtist) {
        this.searchArtist = searchArtist;
    }

    public List<ItemSong> getSearchSong() {
        return searchSong;
    }

    public void setSearchSong(List<ItemSong> searchSong) {
        this.searchSong = searchSong;
    }
}
