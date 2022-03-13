package com.sem4.music_app.item;

import java.io.Serializable;
import java.util.List;

public class ItemAlbums implements Serializable {
    private Integer id;
    private String name;
    private String avatar;
    private ItemArtist artist;
    private List<ItemSong> songs;

    public ItemArtist getArtist() {
        return artist;
    }

    public void setArtist(ItemArtist artist) {
        this.artist = artist;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<ItemSong> getSongs() {
        return songs;
    }

    public void setSongs(List<ItemSong> songs) {
        this.songs = songs;
    }
}
