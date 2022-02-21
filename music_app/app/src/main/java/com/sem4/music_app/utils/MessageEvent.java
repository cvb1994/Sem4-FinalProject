package com.sem4.music_app.utils;

public class MessageEvent {
    public String message;
    public Boolean flag;

    public MessageEvent(Boolean flag, String message) {
        this.message = message;
        this.flag = flag;
    }
}