package com.sem4.music_app.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sem4.music_app.R;
import com.sem4.music_app.item.ItemMyPlayList;
import com.sem4.music_app.item.ItemSong;

import java.util.ArrayList;
import java.util.Collections;

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
        try {
            db.execSQL(CREATE_TABLE_DOWNLOAD);
            db.execSQL(CREATE_TABLE_ABOUT);
            db.execSQL(CREATE_TABLE_FAV);
            db.execSQL(CREATE_TABLE_PLAYLIST);
            addPlayListMyPlay(db, context.getString(R.string.playlist), true);
            db.execSQL(CREATE_TABLE_PLAYLIST_OFFLINE);
            db.execSQL(CREATE_TABLE_PLAYLIST_SONG);
            db.execSQL(CREATE_TABLE_PLAYLIST_SONG_OFFLINE);
            db.execSQL(CREATE_TABLE_RECENT);
            db.execSQL(CREATE_TABLE_RECENT_OFFLINE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void removeFromPlayList(String id, Boolean isOnline) {
        String table;
        if (isOnline) {
            table = TABLE_PLAYLIST_SONG;
        } else {
            table = TABLE_PLAYLIST_SONG_OFFLINE;
        }
        db.delete(table, TAG_SID + "=" + id, null);
    }

    public ArrayList<ItemMyPlayList> loadPlayList(Boolean isOnline) {
        ArrayList<ItemMyPlayList> arrayList = new ArrayList<>();

        String tableName;
        if (isOnline) {
            tableName = TABLE_PLAYLIST;
        } else {
            tableName = TABLE_PLAYLIST_OFFLINE;
        }

        try {
            Cursor cursor = db.query(tableName, columns_playlist, null, null, null, null, TAG_PLAYLIST_NAME + " ASC");
            if (cursor != null && cursor.moveToFirst()) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(TAG_PLAYLIST_ID));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(TAG_PLAYLIST_NAME));

                    ItemMyPlayList objItem = new ItemMyPlayList(id, name, loadPlaylistImages(id, isOnline));
                    arrayList.add(objItem);

                    cursor.moveToNext();
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    public ArrayList<String> loadPlaylistImages(String pid, Boolean isOnline) {
        String table;
        if (isOnline) {
            table = TABLE_PLAYLIST_SONG;
        } else {
            table = TABLE_PLAYLIST_SONG_OFFLINE;
        }
        ArrayList<String> arrayList = new ArrayList<>();

        Cursor cursor = db.query(table, new String[]{TAG_IMAGE_SMALL}, TAG_PID + "=" + pid, null, null, null, "");

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < 4; i++) {

                try {
                    @SuppressLint("Range") String imagesmall = methods.decrypt(cursor.getString(cursor.getColumnIndex(TAG_IMAGE_SMALL)));
                    arrayList.add(imagesmall);
                    cursor.moveToNext();
                } catch (Exception e) {
                    cursor.moveToFirst();
                    @SuppressLint("Range") String imagesmall = methods.decrypt(cursor.getString(cursor.getColumnIndex(TAG_IMAGE_SMALL)));
                    arrayList.add(imagesmall);
                }
            }
            Collections.reverse(arrayList);
            cursor.close();
        } else {
            arrayList.add("1");
            arrayList.add("1");
            arrayList.add("1");
            arrayList.add("1");
        }
        return arrayList;
    }

    public void addToPlayList(ItemSong itemSong, String pid, Boolean isOnline) {
        String tableName;
        if (isOnline) {
            tableName = TABLE_PLAYLIST_SONG;
        } else {
            tableName = TABLE_PLAYLIST_SONG_OFFLINE;

        }
        if (checkPlaylist(itemSong.getId(), isOnline)) {
            db.delete(tableName, TAG_SID + "=" + itemSong.getId(), null);
        }
        String description = DatabaseUtils.sqlEscapeString(itemSong.getDescription());
        String name = itemSong.getTitle().replace("'", "%27");
        String cat_name = itemSong.getCatName().replace("'", "%27");

        String imageBig = methods.encrypt(itemSong.getImageBig().replace(" ", "%20"));
        String imageSmall = methods.encrypt(itemSong.getImageSmall().replace(" ", "%20"));
        String url = methods.encrypt(itemSong.getUrl());

        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_SID, itemSong.getId());
        contentValues.put(TAG_PID, pid);
        contentValues.put(TAG_TITLE, name);
        contentValues.put(TAG_DESCR, description);
        contentValues.put(TAG_ARTIST, itemSong.getArtist());
        contentValues.put(TAG_DURATION, itemSong.getDuration());
        contentValues.put(TAG_URL, url);
        contentValues.put(TAG_IMAGE, imageBig);
        contentValues.put(TAG_IMAGE_SMALL, imageSmall);
        contentValues.put(TAG_CID, itemSong.getCatId());
        contentValues.put(TAG_CNAME, cat_name);
        contentValues.put(TAG_TOTAL_RATE, itemSong.getTotalRate());
        contentValues.put(TAG_AVG_RATE, itemSong.getAverageRating());
        contentValues.put(TAG_VIEWS, itemSong.getViews());
        contentValues.put(TAG_DOWNLOADS, itemSong.getDownloads());

        db.insert(tableName, null, contentValues);
    }

    private Boolean checkPlaylist(String id, Boolean isOnline) {
        String table = "";
        if (isOnline) {
            table = TABLE_PLAYLIST_SONG;
        } else {
            table = TABLE_PLAYLIST_SONG_OFFLINE;
        }

        Cursor cursor = db.query(table, columns_playlist_song, TAG_SID + "=" + id, null, null, null, null);
        Boolean isFav = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        return isFav;
    }

    public ArrayList<ItemMyPlayList> addPlayList(String playlist, Boolean isOnline) {
        String table;
        if (isOnline) {
            table = TABLE_PLAYLIST;
        } else {
            table = TABLE_PLAYLIST_OFFLINE;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_PLAYLIST_NAME, playlist);
        db.insert(table, null, contentValues);

        return loadPlayList(isOnline);
    }

    public void removeFromDownload(String id) {
        db.delete(TABLE_DOWNLOAD_SONG, TAG_SID + "=" + id, null);
    }

    public void addToRecent(ItemSong itemSong, Boolean isOnline) {
        Cursor cursor_delete = db.query(TABLE_RECENT, columns_song, null, null, null, null, null);
        if (cursor_delete != null && cursor_delete.getCount() > 20) {
            cursor_delete.moveToFirst();
            db.delete(TABLE_RECENT, TAG_SID + "=" + cursor_delete.getString(cursor_delete.getColumnIndex(TAG_SID)), null);
        }
        cursor_delete.close();

        String table, imageBig, imageSmall, url;
        if (isOnline) {
            table = TABLE_RECENT;
        } else {
            table = TABLE_RECENT_OFFLINE;
        }
        if (checkRecent(itemSong.getId(), isOnline)) {
            db.delete(table, TAG_SID + "=" + itemSong.getId(), null);
        }

        imageBig = methods.encrypt(itemSong.getImageBig().replace(" ", "%20"));
        imageSmall = methods.encrypt(itemSong.getImageSmall().replace(" ", "%20"));
        url = methods.encrypt(itemSong.getUrl());

        String description = DatabaseUtils.sqlEscapeString(itemSong.getDescription());
        String name = itemSong.getTitle().replace("'", "%27");
        String cat_name = itemSong.getCatName().replace("'", "%27");

        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_SID, itemSong.getId());
        contentValues.put(TAG_TITLE, name);
        contentValues.put(TAG_DESC, description);
        contentValues.put(TAG_ARTIST, itemSong.getArtist());
        contentValues.put(TAG_DURATION, itemSong.getDuration());
        contentValues.put(TAG_URL, url);
        contentValues.put(TAG_IMAGE, imageBig);
        contentValues.put(TAG_IMAGE_SMALL, imageSmall);
        contentValues.put(TAG_CID, itemSong.getCatId());
        contentValues.put(TAG_CNAME, cat_name);
        contentValues.put(TAG_TOTAL_RATE, itemSong.getTotalRate());
        contentValues.put(TAG_AVG_RATE, itemSong.getAverageRating());
        contentValues.put(TAG_VIEWS, itemSong.getViews());
        contentValues.put(TAG_DOWNLOADS, itemSong.getDownloads());

        db.insert(table, null, contentValues);
    }

    private Boolean checkRecent(String id, Boolean isOnline) {
        String table;
        if (isOnline) {
            table = TABLE_RECENT;
        } else {
            table = TABLE_RECENT_OFFLINE;
        }
        Cursor cursor = db.query(table, columns_song, TAG_SID + "=" + id, null, null, null, null);
        Boolean isFav = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        return isFav;
    }

    public ArrayList<ItemMyPlayList> addPlayListMyPlay(SQLiteDatabase db, String playlist, Boolean isOnline) {
        String table;
        if (isOnline) {
            table = TABLE_PLAYLIST;
        } else {
            table = TABLE_PLAYLIST_OFFLINE;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_PLAYLIST_NAME, playlist);
        db.insert(table, null, contentValues);

        return loadPlayList(isOnline);
    }
}
