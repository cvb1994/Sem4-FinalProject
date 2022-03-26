package com.sem4.music_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.sem4.music_app.R;
import com.sem4.music_app.adapter.AdapterAllSongList;
import com.sem4.music_app.interfaces.ClickListenerPlaylist;
import com.sem4.music_app.interfaces.OnClickListener;
import com.sem4.music_app.item.ItemAlbums;
import com.sem4.music_app.item.ItemMyPlayList;
import com.sem4.music_app.item.ItemSong;
import com.sem4.music_app.item.ItemUser;
import com.sem4.music_app.network.ApiManager;
import com.sem4.music_app.network.Common;
import com.sem4.music_app.response.BasePaginate;
import com.sem4.music_app.response.BaseResponse;
import com.sem4.music_app.service.PlayerService;
import com.sem4.music_app.utils.Constant;
import com.sem4.music_app.utils.GlobalBus;
import com.sem4.music_app.utils.Methods;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongByPlaylistActivity extends DrawerActivity {

    AppBarLayout appBarLayout;
    Toolbar toolbar_playlist;
    Methods methods;
    RecyclerView rv;
    ItemMyPlayList itemMyPlayList;
    AdapterAllSongList adapter;
    ArrayList<ItemSong> arrayList;
    CircularProgressBar progressBar;
    FrameLayout frameLayout;
    ImageView iv_playlist, iv_playlist2;
    TextView tv_no_song;
    String addedFrom = "myplay";
    String errr_msg = "";
    SearchView searchView;
    CollapsingToolbarLayout collapsing_play;
    ApiManager apiManager;
    ItemMyPlayList item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiManager = Common.getAPI();
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_song_by_playlist, contentFrameLayout);

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        itemMyPlayList = (ItemMyPlayList) getIntent().getSerializableExtra("item");
        addedFrom = addedFrom + itemMyPlayList.getName();


        methods = new Methods(this, new OnClickListener() {
            @Override
            public void onClick(int position, String type) {
                Constant.isOnline = true;
                if (!Constant.addedFrom.equals(addedFrom)) {
                    Constant.arrayList_play.clear();
                    Constant.arrayList_play.addAll(arrayList);
                    Constant.addedFrom = addedFrom;
                    Constant.isNewAdded = true;
                }
                Constant.playPos = position;

                if(Constant.itemUser != null){
                    if(!Constant.itemUser.isVip()){
                        boolean isContainVipSong = false;
                        for (int i = 0; i < Constant.arrayList_play.size(); i++) {
                            if (Constant.arrayList_play.get(i).isVipOnly()) {
                                isContainVipSong = true;
                                break;
                            }
                        }
                        if(isContainVipSong){
                            openExtendVipDialog();
                        }else {
                            Intent intent = new Intent(SongByPlaylistActivity.this, PlayerService.class);
                            intent.setAction(PlayerService.ACTION_PLAY);
                            startService(intent);
                        }
                    }else {
                        Intent intent = new Intent(SongByPlaylistActivity.this, PlayerService.class);
                        intent.setAction(PlayerService.ACTION_PLAY);
                        startService(intent);
                    }
                }else{
                    boolean isContainVipSong = false;
                    for (int i = 0; i < Constant.arrayList_play.size(); i++) {
                        if (Constant.arrayList_play.get(i).isVipOnly()) {
                            isContainVipSong = true;
                            break;
                        }
                    }
                    if(isContainVipSong){
                        openExtendVipDialog();
                    }else {
                        Intent intent = new Intent(SongByPlaylistActivity.this, PlayerService.class);
                        intent.setAction(PlayerService.ACTION_PLAY);
                        startService(intent);
                    }
                }
            }
        });
        methods.forceRTLIfSupported(getWindow());

        toolbar.setVisibility(View.GONE);

        appBarLayout = findViewById(R.id.mainappbar);
        toolbar_playlist = findViewById(R.id.toolbar_playlist);
        setSupportActionBar(toolbar_playlist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arrayList = new ArrayList<>();

        frameLayout = findViewById(R.id.fl_empty);
        progressBar = findViewById(R.id.pb_song_by_playlist);
        progressBar.setVisibility(View.GONE);
        rv = findViewById(R.id.rv_song_by_playlist);
        LinearLayoutManager llm_banner = new LinearLayoutManager(this);
        rv.setLayoutManager(llm_banner);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);

        arrayList = new ArrayList<>();
        loadSongs();

        iv_playlist = findViewById(R.id.iv_collapse_playlist);
        iv_playlist2 = findViewById(R.id.iv_collapse_playlist2);
        tv_no_song = findViewById(R.id.tv_playlist_no_song);
        collapsing_play = findViewById(R.id.collapsing_play);

        AppBarLayout appBarLayout = findViewById(R.id.mainappbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                tv_no_song.setAlpha(1 - Math.abs((float) verticalOffset / appBarLayout.getTotalScrollRange()));
                iv_playlist.setAlpha(1 - Math.abs((float) verticalOffset / appBarLayout.getTotalScrollRange()));
                iv_playlist2.setAlpha(1 - Math.abs((float) verticalOffset / appBarLayout.getTotalScrollRange()));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Constant.isLoginOn){
            if(Constant.isLogged){
                apiManager.userInfo(Constant.itemUser.getId())
                        .enqueue(new Callback<BaseResponse<ItemUser>>() {
                            @Override
                            public void onResponse(Call<BaseResponse<ItemUser>> call, Response<BaseResponse<ItemUser>> response) {
                                Constant.itemUser.setVip(response.body().getContent().isVip());
                                Constant.itemUser.setVipExpireDate(response.body().getContent().getVipExpireDate());
                            }

                            @Override
                            public void onFailure(Call<BaseResponse<ItemUser>> call, Throwable t) {

                            }
                        });
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(queryTextListener);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appBarLayout.setExpanded(false);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            if (adapter != null) {
                if (!searchView.isIconified()) {
                    adapter.getFilter().filter(s);
                    adapter.notifyDataSetChanged();
                }
            }
            return true;
        }
    };

    private void setAdapter() {
        adapter = new AdapterAllSongList(itemMyPlayList.getId(), SongByPlaylistActivity.this, arrayList, new ClickListenerPlaylist() {
            @Override
            public void onClick(int position) {
                methods.onClick(position, "");
            }

            @Override
            public void onItemZero() {
                setEmpty();
            }
        }, "playlist");
        rv.setAdapter(adapter);
        setEmpty();
    }

    public void setEmpty() {
        tv_no_song.setText(arrayList.size() + " " + getString(R.string.songs));
        if (arrayList.size() > 0) {
            rv.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
        } else {
            rv.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);

            frameLayout.removeAllViews();
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View myView = null;
            if (errr_msg.equals(getString(R.string.err_no_songs_found))) {
                myView = inflater.inflate(R.layout.layout_err_nodata, null);
            } else if (errr_msg.equals(getString(R.string.err_internet_not_conn))) {
                myView = inflater.inflate(R.layout.layout_err_internet, null);
            } else if (errr_msg.equals(getString(R.string.err_server))) {
                myView = inflater.inflate(R.layout.layout_err_server, null);
            }

            TextView textView = myView.findViewById(R.id.tv_empty_msg);
            textView.setText(errr_msg);

            myView.findViewById(R.id.btn_empty_try).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadSongs();
                }
            });
            frameLayout.addView(myView);
        }
    }

    @Override
    public void onBackPressed() {
        if (mLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED)) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else if (dialog_desc != null && dialog_desc.isShowing()) {
            dialog_desc.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEquilizerChange(ItemAlbums itemAlbums) {
        adapter.notifyDataSetChanged();
        GlobalBus.getBus().removeStickyEvent(itemAlbums);
    }

    private void loadSongs() {
        if (methods.isNetworkAvailable()) {
            if (arrayList.size() == 0) {
                arrayList.clear();
                frameLayout.setVisibility(View.GONE);
                rv.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }
            apiManager.getPlaylistById(itemMyPlayList.getId())
                    .enqueue(new Callback<BaseResponse<ItemMyPlayList>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<ItemMyPlayList>> call, Response<BaseResponse<ItemMyPlayList>> response) {
                            collapsing_play.setTitle(response.body().getContent().getName());
                            if (response.body().getContent().getSongs().size() == 0){
                                errr_msg = getString(R.string.err_no_songs_found);
                                setEmpty();
                            }else{
                                arrayList.addAll(response.body().getContent().getSongs());
                                Picasso.get()
                                        .load(arrayList.get(0).getImageBig())
                                        .into(iv_playlist);
                                Picasso.get()
                                        .load(arrayList.get(0).getImageBig())
                                        .into(iv_playlist2);
                                setAdapter();
                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<ItemMyPlayList>> call, Throwable t) {
                            errr_msg = getString(R.string.err_server);
                            setEmpty();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        } else {
            errr_msg = getString(R.string.err_internet_not_conn);
            setEmpty();
        }
    }
}