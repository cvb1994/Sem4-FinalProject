package com.sem4.music_app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sem4.music_app.R;
import com.sem4.music_app.activity.AllMusicActivity;
import com.sem4.music_app.activity.DrawerActivity;
import com.sem4.music_app.adapter.AdapterAllSongList;
import com.sem4.music_app.interfaces.ClickListenerPlaylist;
import com.sem4.music_app.interfaces.OnClickListener;
import com.sem4.music_app.item.ItemAlbums;
import com.sem4.music_app.item.ItemHome;
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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentChart extends Fragment {

    private Methods methods;
    private RecyclerView rv;
    private AdapterAllSongList adapter;
    private ArrayList<ItemSong> arrayList;
    private CircularProgressBar progressBar;
    private FrameLayout frameLayout;
    private ScrollView scrollView;
    private String addedFrom = "lat";

    private String errr_msg;
    private SearchView searchView;
    private int page = 1;
    private Boolean isOver = false, isScroll = false, isLoading = false;
    ApiManager apiManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);
        setHasOptionsMenu(true);
        apiManager = Common.getAPI();
        methods = new Methods(getActivity(), new OnClickListener() {
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

                if (Constant.itemUser != null) {
                    if(!Constant.itemUser.isVip()){
                        boolean isContainVipSong = false;
                        for (int i = 0; i < Constant.arrayList_play.size(); i++) {
                            if (Constant.arrayList_play.get(i).isVipOnly()) {
                                isContainVipSong = true;
                                break;
                            }
                        }
                        if(isContainVipSong){
                            ((DrawerActivity)getActivity()).openRegisterVipDialog();
                        }else {
                            Intent intent = new Intent( getActivity(), PlayerService.class);
                            intent.setAction(PlayerService.ACTION_PLAY);
                            getActivity().startService(intent);
                        }
                    }else {
                        Intent intent = new Intent( getActivity(), PlayerService.class);
                        intent.setAction(PlayerService.ACTION_PLAY);
                        getActivity().startService(intent);
                    }
                }
                else{
                    boolean isContainVipSong = false;
                    for (int i = 0; i < Constant.arrayList_play.size(); i++) {
                        if (Constant.arrayList_play.get(i).isVipOnly()) {
                            isContainVipSong = true;
                            break;
                        }
                    }
                    if(isContainVipSong){
                        ((DrawerActivity)getActivity()).openRegisterVipDialog();
                    }else {
                        Intent intent = new Intent( getActivity(), PlayerService.class);
                        intent.setAction(PlayerService.ACTION_PLAY);
                        getActivity().startService(intent);
                    }
                }
            }
        });

        arrayList = new ArrayList<>();

        progressBar = rootView.findViewById(R.id.pb_chart);
        rv = rootView.findViewById(R.id.rv_chart);
        scrollView = rootView.findViewById(R.id.sv_chart);
        LinearLayoutManager llm_banner = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm_banner);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);
        rv.setNestedScrollingEnabled(false);

        frameLayout = rootView.findViewById(R.id.fl_empty);
        ((DrawerActivity)getActivity()).getSupportActionBar().setTitle("BXH tháng ".concat(String.valueOf(LocalDate.now().getMonthValue())));
        loadLatestSongs();

        return rootView;
    }

    @Override
    public void onResume() {
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

    private void loadLatestSongs() {
        if (methods.isNetworkAvailable()) {
            apiManager.getChart()
                    .enqueue(new Callback<BaseResponse<List<ItemSong>>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<List<ItemSong>>> call, Response<BaseResponse<List<ItemSong>>> response) {
                            if (response.body().getContent().size() == 0){
                                isOver = true;
                                errr_msg = getString(R.string.err_no_songs_found);
                                setEmpty();
                            }else{
                                arrayList.addAll(response.body().getContent());
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
                        public void onFailure(Call<BaseResponse<List<ItemSong>>> call, Throwable t) {
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
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public void setEmpty() {
        if (arrayList.size() > 0) {
            scrollView.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
        } else {
            scrollView.setVisibility(View.GONE);
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
                    loadLatestSongs();
                }
            });

            frameLayout.addView(myView);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEquilizerChange(ItemAlbums itemAlbums) {
        try {
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
