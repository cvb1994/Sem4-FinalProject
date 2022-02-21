package com.sem4.music_app.item;

import java.util.List;

public class ItemSong {
    private Integer id;
    private String title;
    private String image;
    private String mediaUrl;
    private Integer listenCount;
    private List<ItemArtist> artists = null;
    private List<ItemGenre> genres = null;
    private Integer albumId;
    private String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public Integer getListenCount() {
        return listenCount;
    }

    public void setListenCount(Integer listenCount) {
        this.listenCount = listenCount;
    }

    public List<ItemArtist> getArtists() {
        return artists;
    }

    public void setArtists(List<ItemArtist> artists) {
        this.artists = artists;
    }

    public List<ItemGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<ItemGenre> genres) {
        this.genres = genres;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }
}
