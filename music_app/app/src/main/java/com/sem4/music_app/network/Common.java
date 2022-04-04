package com.sem4.music_app.network;

public class Common {
    /*
    * For deploy running
    */
    private static final String BASE_URL = "https://music-ner3yu5pda-as.a.run.app/";

    /*
     * For local running
     */
//    private static final String BASE_URL = "http://localhost:8080";

    public static ApiManager getAPI(){
        return RetrofitClient.getClient(BASE_URL).create(ApiManager.class);
    }
}
