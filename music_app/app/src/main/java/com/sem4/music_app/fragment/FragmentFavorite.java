package com.sem4.music_app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sem4.music_app.R;
import com.sem4.music_app.adapter.AdapterAllSongList;
import com.sem4.music_app.interfaces.ClickListenerPlaylist;
import com.sem4.music_app.interfaces.OnClickListener;
import com.sem4.music_app.item.ItemAlbums;
import com.sem4.music_app.item.ItemSong;
import com.sem4.music_app.network.ApiManager;
import com.sem4.music_app.network.Common;
import com.sem4.music_app.response.BasePaginate;
import com.sem4.music_app.response.BaseResponse;
import com.sem4.music_app.service.PlayerService;
import com.sem4.music_app.utils.Constant;
import com.sem4.music_app.utils.GlobalBus;
import com.sem4.music_app.utils.Methods;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFavorite extends Fragment {

    private Methods methods;
    private RecyclerView rv;
    private AdapterAllSongList adapter;
    private ArrayList<ItemSong> arrayList;
    private CircularProgressBar progressBar;
    private FrameLayout frameLayout;
    private SearchView searchView;
    private String addedFrom = "playlist";
    private ApiManager apiManager;
    String errr_msg;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_song_by_category, container, false);
        apiManager = Common.getAPI();
        methods = new Methods(getActivity(), new OnClickListener() {
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

                Intent intent = new Intent(getActivity(), PlayerService.class);
                intent.setAction(PlayerService.ACTION_PLAY);
                getActivity().startService(intent);
            }
        });

        arrayList = new ArrayList<>();

        frameLayout = rootView.findViewById(R.id.fl_empty);
        progressBar = rootView.findViewById(R.id.pb_song_by_cat);
        progressBar.setVisibility(View.GONE);

        rv = rootView.findViewById(R.id.rv_song_by_cat);
        LinearLayoutManager llm_banner = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm_banner);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);
        rv.setNestedScrollingEnabled(false);

        loadSongs();

        setHasOptionsMenu(true);
        return rootView;
    }

    private void setAdapter(){
        adapter = new AdapterAllSongList(getActivity(), arrayList, new ClickListenerPlaylist() {
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
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
//        inflater.inflate(R.menu.menu_search, menu);
//
//        MenuItem item = menu.findItem(R.id.menu_search);
//        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
//        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
//        searchView.setOnQueryTextListener(queryTextListener);
//        super.onCreateOptionsMenu(menu, inflater);
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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEquilizerChange(ItemAlbums itemAlbums) {
        adapter.notifyDataSetChanged();
        GlobalBus.getBus().removeStickyEvent(itemAlbums);
    }

    @Override
    public void onStart() {
        super.onStart();
        GlobalBus.getBus().register(this);
    }

    @Override
    public void onStop() {
        GlobalBus.getBus().unregister(this);
        super.onStop();
    }

    private void loadSongs() {
        if (methods.isNetworkAvailable()) {
            if (arrayList.size() == 0) {
                arrayList.clear();
                frameLayout.setVisibility(View.GONE);
                rv.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }
            apiManager.getPlaylistFavorite(Constant.itemUser.getId())
                    .enqueue(new Callback<BaseResponse<BasePaginate<ItemSong>>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<BasePaginate<ItemSong>>> call, Response<BaseResponse<BasePaginate<ItemSong>>> response) {
                            if(response.body().getContent() != null){
                                if (response.body().getContent().getSongs().size() == 0) {
                                    errr_msg = getString(R.string.err_no_songs_found);
                                    setEmpty();
                                } else {
                                    arrayList.addAll(response.body().getContent().getSongs());
                                    setAdapter();
                                }
                            }
                            else {
                                errr_msg = getString(R.string.err_no_songs_found);
                                setEmpty();
                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<BasePaginate<ItemSong>>> call, Throwable t) {
                            errr_msg = getString(R.string.err_server);
                            setEmpty();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }
        else{
            errr_msg = getString(R.string.err_internet_not_conn);
            setEmpty();
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
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
}