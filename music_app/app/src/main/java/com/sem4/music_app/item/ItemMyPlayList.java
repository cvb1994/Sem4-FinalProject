package com.sem4.music_app.item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemMyPlayList implements Serializable {

    private String id, name;
    private ArrayList<String> arrayListUrl;
    private ArrayList<ItemSong> songs;

    public ItemMyPlayList(String id, String name, ArrayList<String> arrayListUrl) {
        this.id = id;
        this.name = name;
        this.arrayListUrl = arrayListUrl;
    }

    public ItemMyPlayList(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getArrayListUrl() {
        return arrayListUrl;
    }

    public ArrayList<ItemSong> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<ItemSong> songs) {
        this.songs = songs;
    }
}
