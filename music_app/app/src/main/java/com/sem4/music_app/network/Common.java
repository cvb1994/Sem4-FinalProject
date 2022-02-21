package com.sem4.music_app.network;

public class Common {
    private static final String BASE_URL = "https://music-ner3yu5pda-as.a.run.app/";

    public static ApiManager getAPI(){
        return RetrofitClient.getClient(BASE_URL).create(ApiManager.class);
    }
}
