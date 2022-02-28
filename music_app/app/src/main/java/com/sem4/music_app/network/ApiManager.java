package com.sem4.music_app.network;

import com.sem4.music_app.item.ItemAlbums;
import com.sem4.music_app.item.ItemArtist;
import com.sem4.music_app.item.ItemGenre;
import com.sem4.music_app.item.ItemHome;
import com.sem4.music_app.response.ApiResponse;
import com.sem4.music_app.response.BasePaginate;
import com.sem4.music_app.response.BaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiManager {

    //region User
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
    //endregion

    //region Artist
    @POST("api/artist/list")
    @FormUrlEncoded
    Call<BaseResponse<BasePaginate<ItemArtist>>> listArtist(@Field("page") int page,
                                                            @Field("size") int size);
    //endregion

    //region Home
    @GET("api/v1/home")
    Call<BaseResponse<ItemHome>> homePage();
    //endregion

    //region Genre
    @POST("api/genre/list")
    @FormUrlEncoded
    Call<BaseResponse<BasePaginate<ItemGenre>>> listGenre(@Field("page") int page,
                                                          @Field("size") int size);
    //endregion

    //region Album
    @POST("api/album/list")
    @FormUrlEncoded
    Call<BaseResponse<BasePaginate<ItemAlbums>>> listAlbums(@Field("page") int page,
                                                            @Field("size") int size);

    @GET("api/album")
    Call<BaseResponse<ItemAlbums>> listAlbumsByArtist();
    //endregion
}
