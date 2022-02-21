package com.sem4.music_app.item;

public class ItemAlbums {
    private Integer id;
    private String name;
    private String avatar;
    private ItemArtist artist;

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
}
