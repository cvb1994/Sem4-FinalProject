package com.sem4.music_app.network;

import com.sem4.music_app.item.ItemAlbums;
import com.sem4.music_app.item.ItemArtist;
import com.sem4.music_app.item.ItemGenre;
import com.sem4.music_app.response.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiManager {

    @POST("user/login")
    @FormUrlEncoded
    Call<String> userLogin(@Field("username") String username,
                           @Field("password") String password);

    @POST("api/user")
    @FormUrlEncoded
    Call<ApiResponse> register(@Field("firstName") String firstName,
                               @Field("lastName") String lastName,
                               @Field("username") String username,
                               @Field("password") String password,
                               @Field("email") String email,
                               @Field("phone") String phone);

    @GET("api/user/password/resetlink")
    Call<ApiResponse> forgotPassword(@Query("email") String email);

    @GET("api/artist")
    Call<List<ItemArtist>> listArtist();

    @GET("api/genre")
    Call<List<ItemGenre>> listGenre();

    @GET("api/album")
    Call<List<ItemAlbums>> listAlbums();
}
