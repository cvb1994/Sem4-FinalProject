package com.sem4.music_app.item;

import java.util.List;

public class ItemHome {
    private List<ItemAlbums> banner;
    private List<ItemAlbums> listAlbum;
    private List<ItemArtist> listArtist;
    private List<ItemSong> listTrending;

    public List<ItemAlbums> getBanner() {
        return banner;
    }

    public void setBanner(List<ItemAlbums> banner) {
        this.banner = banner;
    }

    public List<ItemAlbums> getListAlbum() {
        return listAlbum;
    }

    public void setListAlbum(List<ItemAlbums> listAlbum) {
        this.listAlbum = listAlbum;
    }

    public List<ItemArtist> getListArtist() {
        return listArtist;
    }

    public void setListArtist(List<ItemArtist> listArtist) {
        this.listArtist = listArtist;
    }

    public List<ItemSong> getListTrending() {
        return listTrending;
    }

    public void setListTrending(List<ItemSong> listTrending) {
        this.listTrending = listTrending;
    }
}
