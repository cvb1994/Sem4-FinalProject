package com.sem4.music_app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sem4.music_app.R;
import com.sem4.music_app.adapter.AdapterAllSongList;
import com.sem4.music_app.interfaces.ClickListenerPlaylist;
import com.sem4.music_app.interfaces.OnClickListener;
import com.sem4.music_app.item.ItemAlbums;
import com.sem4.music_app.item.ItemMyPlayList;
import com.sem4.music_app.item.ItemSong;
import com.sem4.music_app.network.ApiManager;
import com.sem4.music_app.network.Common;
import com.sem4.music_app.response.BasePaginate;
import com.sem4.music_app.response.BaseResponse;
import com.sem4.music_app.service.PlayerService;
import com.sem4.music_app.utils.Constant;
import com.sem4.music_app.utils.EndlessRecyclerViewScrollListener;
import com.sem4.music_app.utils.GlobalBus;
import com.sem4.music_app.utils.Methods;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllMusicActivity extends DrawerActivity{

    Methods methods;
    RecyclerView rv;
    AdapterAllSongList adapter;
    ArrayList<ItemSong> arrayList;
    CircularProgressBar progressBar;
    String id = "", name = "", type = "";
    FrameLayout frameLayout;

    String errr_msg;
    SearchView searchView;
    Boolean isFromPush = false;
    int page = 0;
    String addedFrom = "";
    Boolean isOver = false, isScroll = false, isLoading = false;
    private ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiManager = Common.getAPI();
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_song_by_category, contentFrameLayout);

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        methods = new Methods(AllMusicActivity.this, new OnClickListener() {
            @Override
            public void onClick(int position, String type) {
                Constant.isOnline = true;
                if(!Constant.addedFrom.equals(addedFrom)) {
                    Constant.arrayList_play.clear();
                    Constant.arrayList_play.addAll(arrayList);
                    Constant.addedFrom = addedFrom;
                    Constant.isNewAdded = true;
                }
                Constant.playPos = position;

                Intent intent = new Intent(AllMusicActivity.this, PlayerService.class);
                intent.setAction(PlayerService.ACTION_PLAY);
                startService(intent);
            }
        });
        methods.forceRTLIfSupported(getWindow());

        toolbar.setTitle(getString(R.string.all_music));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_back);

        frameLayout = findViewById(R.id.fl_empty);
        progressBar = findViewById(R.id.pb_song_by_cat);
        rv = findViewById(R.id.rv_song_by_cat);
        LinearLayoutManager llm_banner = new LinearLayoutManager(this);
        rv.setLayoutManager(llm_banner);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);

        arrayList = new ArrayList<>();
        loadSongs();

        rv.addOnScrollListener(new EndlessRecyclerViewScrollListener(llm_banner) {
            @Override
            public void onLoadMore(int p, int totalItemsCount) {
                if (!isOver) {
                    if (!isLoading) {
                        isLoading = true;

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isScroll = true;
                                loadSongs();
                            }
                        }, 0);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onCreateOptionsMenu(menu);
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

    private void loadSongs() {
        if (methods.isNetworkAvailable()) {
            if (arrayList.size() == 0) {
                arrayList.clear();
                frameLayout.setVisibility(View.GONE);
                rv.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }
                apiManager.listSong(page, 7)
                        .enqueue(new Callback<BaseResponse<BasePaginate<ItemSong>>>() {
                            @Override
                            public void onResponse(Call<BaseResponse<BasePaginate<ItemSong>>> call, Response<BaseResponse<BasePaginate<ItemSong>>> response) {
                                if (response.body().getContent().getContent().size() == 0){
                                    isOver = true;
                                    errr_msg = getString(R.string.err_no_songs_found);
                                    setEmpty();
                                }else{
                                    arrayList.addAll(response.body().getContent().getContent());
                                    if(isScroll && Constant.addedFrom.equals(addedFrom)) {
                                        Constant.arrayList_play.clear();
                                        Constant.arrayList_play.addAll(arrayList);
                                        try {
                                            GlobalBus.getBus().postSticky(new ItemMyPlayList("", "", null));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    page = page + 1;
                                    setAdapter();
                                }
                                progressBar.setVisibility(View.GONE);
                                isLoading = false;
                            }

                            @Override
                            public void onFailure(Call<BaseResponse<BasePaginate<ItemSong>>> call, Throwable t) {
                                errr_msg = getString(R.string.err_server);
                                setEmpty();
                                progressBar.setVisibility(View.GONE);
                                isLoading = false;
                            }
                        });
        } else {
            errr_msg = getString(R.string.err_internet_not_conn);
            setEmpty();
        }
    }

    private void setAdapter() {
        if (!isScroll) {
            adapter = new AdapterAllSongList(AllMusicActivity.this, arrayList, new ClickListenerPlaylist() {
                @Override
                public void onClick(int position) {
                    methods.onClick(position, "");
                }

                @Override
                public void onItemZero() {

                }
            }, "online");
            rv.setAdapter(adapter);
            setEmpty();
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public void setEmpty() {
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
        } else if (isFromPush) {
            Intent intent = new Intent(AllMusicActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEquilizerChange(ItemAlbums itemAlbums) {
        adapter.notifyDataSetChanged();
        GlobalBus.getBus().removeStickyEvent(itemAlbums);
    }
}
