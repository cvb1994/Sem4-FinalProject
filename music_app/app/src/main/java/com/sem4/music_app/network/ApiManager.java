package com.sem4.music_app.network;

import com.sem4.music_app.item.ItemAlbums;
import com.sem4.music_app.item.ItemArtist;
import com.sem4.music_app.item.ItemGenre;
import com.sem4.music_app.item.ItemHome;
import com.sem4.music_app.item.ItemMyPlayList;
import com.sem4.music_app.item.ItemSong;
import com.sem4.music_app.item.ItemUser;
import com.sem4.music_app.response.ApiResponse;
import com.sem4.music_app.response.BasePaginate;
import com.sem4.music_app.response.BaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiManager {

    //region User
    @POST("user/login")
    @FormUrlEncoded
    Call<BaseResponse<ItemUser>> userLogin(@Field("username") String username,
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

    @GET("api/album/byArtistId/{artistId}")
    Call<BaseResponse<BasePaginate<ItemAlbums>>> listAlbumsByArtist(@Path("artistId") int artistId,
                                                                    @Query("page") int page,
                                                                    @Query("size") int size);
    //endregion

    //region Song
    @GET("api/song/byArtistId/{artistId}")
    Call<BaseResponse<BasePaginate<ItemSong>>> listSongByArtist(@Path("artistId") int artistId,
                                                                  @Query("page") int page,
                                                                  @Query("size") int size);

    @GET("api/song/byAlbumId/{albumId}")
    Call<BaseResponse<BasePaginate<ItemSong>>> listSongByAlbum(@Path("albumId") int albumId,
                                                                @Query("page") int page,
                                                                @Query("size") int size);

    @GET("api/song/byGenreId/{genreId}")
    Call<BaseResponse<BasePaginate<ItemSong>>> listSongByGenre(@Path("genreId") int genreId,
                                                               @Query("page") int page,
                                                               @Query("size") int size);
    //endregion

    //region Playlist
    @GET("api/playlist/list/{userId}")
    Call<BaseResponse<List<ItemMyPlayList>>> getAllPlaylist(@Path("userId") String userId);

    @POST("api/playlist")
    @FormUrlEncoded
    Call<BaseResponse<List<ItemMyPlayList>>> addPlaylist(@Field("name") String name,
                                                         @Field("userId") String userId);

    @DELETE("api/playlist/{playlistId}")
    Call<BaseResponse<List<ItemMyPlayList>>> deletePlaylist(@Path("playlistId") String playlistId);

    @GET("api/playlist/like")
    Call<BaseResponse<BasePaginate<ItemSong>>> checkFavorite(@Query("userId") String userId,
                                                             @Query("songId") String songId);

    @POST("api/playlist/like")
    @FormUrlEncoded
    Call<BaseResponse<BasePaginate<ItemSong>>> addRemoveFavorite(@Field("userId") String userId,
                                                                 @Field("songId") String songId);

    @GET("api/playlist/like/{userId}")
    Call<BaseResponse<BasePaginate<ItemSong>>> getPlaylistFavorite(@Path("userId") String userId);
    //endregion
}
