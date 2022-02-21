package com.sem4.music_app.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sem4.music_app.item.ItemSong;

public class DBHelper extends SQLiteOpenHelper {

    private Methods methods;
    private static String DB_NAME = "mp3.db";

    private static String TAG_PLAYLIST_ID = "id";
    private static String TAG_PLAYLIST_NAME = "name";

    private static String TAG_ID = "id";
    private static String TAG_SID = "sid";
    private static String TAG_PID = "pid";
    private static String TAG_TITLE = "title";
    private static String TAG_DESC = "desc";
    private static String TAG_ARTIST = "artist";
    private static String TAG_DURATION = "duration";
    private static String TAG_URL = "url";
    private static String TAG_IMAGE = "image";
    private static String TAG_IMAGE_SMALL = "image_small";
    private static String TAG_CID = "cid";
    private static String TAG_CNAME = "cname";
    private static String TAG_TOTAL_RATE = "total_rate";
    private static String TAG_AVG_RATE = "avg_rate";
    private static String TAG_VIEWS = "views";
    private static String TAG_DOWNLOADS = "downloads";
    private static final String TAG_DESCR = "descr";
    private static final String TAG_TEMP_NAME = "tempid";
    private SQLiteDatabase db;
    private final Context context;

    // Table Name
    private static final String TABLE_ABOUT = "about";
    private static final String TABLE_PLAYLIST = "playlist";
    private static final String TABLE_PLAYLIST_OFFLINE = "playlist_offline";
    private static final String TABLE_PLAYLIST_SONG = "playlistsong";
    private static final String TABLE_PLAYLIST_SONG_OFFLINE = "playlistsong_offline";
    private static final String TABLE_RECENT = "recent";
    private static final String TABLE_RECENT_OFFLINE = "recent_off";
    private static final String TABLE_FAV_SONG = "song";
    private static final String TABLE_DOWNLOAD_SONG = "download";

    // Table columns_quotes

    private static final String TAG_ABOUT_NAME = "name";
    private static final String TAG_ABOUT_LOGO = "logo";
    private static final String TAG_ABOUT_VERSION = "version";
    private static final String TAG_ABOUT_AUTHOR = "author";
    private static final String TAG_ABOUT_CONTACT = "contact";
    private static final String TAG_ABOUT_EMAIL = "email";
    private static final String TAG_ABOUT_WEBSITE = "website";
    private static final String TAG_ABOUT_DESC = "desc";
    private static final String TAG_ABOUT_DEVELOPED = "developed";
    private static final String TAG_ABOUT_PRIVACY = "privacy";
    private static final String TAG_ABOUT_PUB_ID = "ad_pub";
    private static final String TAG_ABOUT_BANNER_ID = "ad_banner";
    private static final String TAG_ABOUT_INTER_ID = "ad_inter";
    private static final String TAG_ABOUT_IS_BANNER = "isbanner";
    private static final String TAG_ABOUT_IS_INTER = "isinter";
    private static final String TAG_ABOUT_CLICK = "click";
    private static final String TAG_ABOUT_IS_DOWNLOAD = "isdownload";

    private String[] columns_song = new String[]{TAG_ID, TAG_SID, TAG_TITLE, TAG_DESC, TAG_ARTIST, TAG_DURATION, TAG_URL, TAG_IMAGE,
            TAG_IMAGE_SMALL, TAG_CID, TAG_CNAME, TAG_TOTAL_RATE, TAG_AVG_RATE, TAG_VIEWS, TAG_DOWNLOADS};

    private String[] columns_playlist_song = new String[]{TAG_ID, TAG_SID, TAG_TITLE, TAG_DESCR, TAG_ARTIST, TAG_DURATION, TAG_URL, TAG_IMAGE,
            TAG_IMAGE_SMALL, TAG_CID, TAG_CNAME, TAG_TOTAL_RATE, TAG_AVG_RATE, TAG_VIEWS, TAG_DOWNLOADS};

    private String[] columns_download_song = new String[]{TAG_ID, TAG_SID, TAG_TITLE, TAG_DESC, TAG_ARTIST, TAG_DURATION, TAG_URL, TAG_IMAGE,
            TAG_IMAGE_SMALL, TAG_CID, TAG_CNAME, TAG_TOTAL_RATE, TAG_AVG_RATE, TAG_VIEWS, TAG_DOWNLOADS, TAG_TEMP_NAME};

    private String[] columns_playlist = new String[]{TAG_PLAYLIST_ID, TAG_PLAYLIST_NAME};

    private String[] columns_about = new String[]{TAG_ABOUT_NAME, TAG_ABOUT_LOGO, TAG_ABOUT_VERSION, TAG_ABOUT_AUTHOR, TAG_ABOUT_CONTACT,
            TAG_ABOUT_EMAIL, TAG_ABOUT_WEBSITE, TAG_ABOUT_DESC, TAG_ABOUT_DEVELOPED, TAG_ABOUT_PRIVACY, TAG_ABOUT_PUB_ID,
            TAG_ABOUT_BANNER_ID, TAG_ABOUT_INTER_ID, TAG_ABOUT_IS_BANNER, TAG_ABOUT_IS_INTER, TAG_ABOUT_CLICK, TAG_ABOUT_IS_DOWNLOAD};

    // Creating table about
    private static final String CREATE_TABLE_ABOUT = "create table " + TABLE_ABOUT + "(" + TAG_ABOUT_NAME
            + " TEXT, " + TAG_ABOUT_LOGO + " TEXT, " + TAG_ABOUT_VERSION + " TEXT, " + TAG_ABOUT_AUTHOR + " TEXT" +
            ", " + TAG_ABOUT_CONTACT + " TEXT, " + TAG_ABOUT_EMAIL + " TEXT, " + TAG_ABOUT_WEBSITE + " TEXT, " + TAG_ABOUT_DESC + " TEXT" +
            ", " + TAG_ABOUT_DEVELOPED + " TEXT, " + TAG_ABOUT_PRIVACY + " TEXT, " + TAG_ABOUT_PUB_ID + " TEXT, " + TAG_ABOUT_BANNER_ID + " TEXT" +
            ", " + TAG_ABOUT_INTER_ID + " TEXT, " + TAG_ABOUT_IS_BANNER + " TEXT, " + TAG_ABOUT_IS_INTER + " TEXT, " + TAG_ABOUT_CLICK + " TEXT, " + TAG_ABOUT_IS_DOWNLOAD + " TEXT);";

    // Creating table playlist
    private static final String CREATE_TABLE_PLAYLIST = "create table " + TABLE_PLAYLIST + "(" + TAG_PLAYLIST_ID
            + " integer PRIMARY KEY AUTOINCREMENT, " + TAG_PLAYLIST_NAME + " TEXT);";
    // Creating table playlist offline
    private static final String CREATE_TABLE_PLAYLIST_OFFLINE = "create table " + TABLE_PLAYLIST_OFFLINE + "(" + TAG_PLAYLIST_ID
            + " integer PRIMARY KEY AUTOINCREMENT, " + TAG_PLAYLIST_NAME + " TEXT);";

    // Creating table query
    private static final String CREATE_TABLE_PLAYLIST_SONG = "create table " + TABLE_PLAYLIST_SONG + "(" +
            TAG_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            TAG_SID + " TEXT," +
            TAG_TITLE + " TEXT," +
            TAG_DESCR + " TEXT," +
            TAG_ARTIST + " TEXT," +
            TAG_DURATION + " TEXT," +
            TAG_URL + " TEXT," +
            TAG_IMAGE + " TEXT," +
            TAG_IMAGE_SMALL + " TEXT," +
            TAG_CID + " TEXT," +
            TAG_CNAME + " TEXT," +
            TAG_PID + " TEXT," +
            TAG_TOTAL_RATE + " TEXT," +
            TAG_AVG_RATE + " TEXT," +
            TAG_VIEWS + " TEXT," +
            TAG_DOWNLOADS + " TEXT);";

    // Creating table query
    private static final String CREATE_TABLE_PLAYLIST_SONG_OFFLINE = "create table " + TABLE_PLAYLIST_SONG_OFFLINE + "(" +
            TAG_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            TAG_SID + " TEXT," +
            TAG_TITLE + " TEXT," +
            TAG_DESCR + " TEXT," +
            TAG_ARTIST + " TEXT," +
            TAG_DURATION + " TEXT," +
            TAG_URL + " TEXT," +
            TAG_IMAGE + " TEXT," +
            TAG_IMAGE_SMALL + " TEXT," +
            TAG_CID + " TEXT," +
            TAG_CNAME + " TEXT," +
            TAG_PID + " TEXT," +
            TAG_TOTAL_RATE + " TEXT," +
            TAG_AVG_RATE + " TEXT," +
            TAG_VIEWS + " TEXT," +
            TAG_DOWNLOADS + " TEXT);";

    // Creating table query
    private static final String CREATE_TABLE_RECENT = "create table " + TABLE_RECENT + "(" +
            TAG_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            TAG_SID + " TEXT," +
            TAG_TITLE + " TEXT," +
            TAG_DESC + " TEXT," +
            TAG_ARTIST + " TEXT," +
            TAG_DURATION + " TEXT," +
            TAG_URL + " TEXT," +
            TAG_IMAGE + " TEXT," +
            TAG_IMAGE_SMALL + " TEXT," +
            TAG_CID + " TEXT," +
            TAG_CNAME + " TEXT," +
            TAG_TOTAL_RATE + " TEXT," +
            TAG_AVG_RATE + " TEXT," +
            TAG_VIEWS + " TEXT," +
            TAG_DOWNLOADS + " TEXT);";

    // Creating table query
    private static final String CREATE_TABLE_RECENT_OFFLINE = "create table " + TABLE_RECENT_OFFLINE + "(" +
            TAG_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            TAG_SID + " TEXT," +
            TAG_TITLE + " TEXT," +
            TAG_DESC + " TEXT," +
            TAG_ARTIST + " TEXT," +
            TAG_DURATION + " TEXT," +
            TAG_URL + " TEXT," +
            TAG_IMAGE + " TEXT," +
            TAG_IMAGE_SMALL + " TEXT," +
            TAG_CID + " TEXT," +
            TAG_CNAME + " TEXT," +
            TAG_TOTAL_RATE + " TEXT," +
            TAG_AVG_RATE + " TEXT," +
            TAG_VIEWS + " TEXT," +
            TAG_DOWNLOADS + " TEXT);";

    // Creating table query
    private static final String CREATE_TABLE_FAV = "create table " + TABLE_FAV_SONG + "(" +
            TAG_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            TAG_SID + " TEXT," +
            TAG_TITLE + " TEXT," +
            TAG_DESC + " TEXT," +
            TAG_ARTIST + " TEXT," +
            TAG_DURATION + " TEXT," +
            TAG_URL + " TEXT," +
            TAG_IMAGE + " TEXT," +
            TAG_IMAGE_SMALL + " TEXT," +
            TAG_CID + " TEXT," +
            TAG_CNAME + " TEXT," +
            TAG_TOTAL_RATE + " TEXT," +
            TAG_AVG_RATE + " TEXT," +
            TAG_VIEWS + " TEXT," +
            TAG_DOWNLOADS + " TEXT);";

    // Creating table query
    private static final String CREATE_TABLE_DOWNLOAD = "create table " + TABLE_DOWNLOAD_SONG + "(" +
            TAG_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            TAG_SID + " TEXT," +
            TAG_TITLE + " TEXT," +
            TAG_DESC + " TEXT," +
            TAG_ARTIST + " TEXT," +
            TAG_DURATION + " TEXT," +
            TAG_URL + " TEXT," +
            TAG_IMAGE + " TEXT," +
            TAG_IMAGE_SMALL + " TEXT," +
            TAG_CID + " TEXT," +
            TAG_CNAME + " TEXT," +
            TAG_TOTAL_RATE + " TEXT," +
            TAG_AVG_RATE + " TEXT," +
            TAG_VIEWS + " TEXT," +
            TAG_DOWNLOADS + " TEXT," +
            TAG_TEMP_NAME + " TEXT);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 5);
        methods = new Methods(context, false);
        this.context = context;
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
