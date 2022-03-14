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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sem4.music_app.R;
import com.sem4.music_app.activity.MainActivity;
import com.sem4.music_app.activity.SongByCategoryActivity;
import com.sem4.music_app.adapter.AdapterAlbumsHome;
import com.sem4.music_app.adapter.AdapterArtistHome;
import com.sem4.music_app.adapter.AdapterRecent;
import com.sem4.music_app.adapter.HomePagerAdapter;
import com.sem4.music_app.interfaces.ClickListenerPlaylist;
import com.sem4.music_app.interfaces.OnClickListener;
import com.sem4.music_app.item.ItemAlbums;
import com.sem4.music_app.item.ItemArtist;
import com.sem4.music_app.item.ItemHome;
import com.sem4.music_app.item.ItemSong;
import com.sem4.music_app.network.ApiManager;
import com.sem4.music_app.network.Common;
import com.sem4.music_app.response.BaseResponse;
import com.sem4.music_app.service.PlayerService;
import com.sem4.music_app.utils.Constant;
import com.sem4.music_app.utils.Methods;
import com.sem4.music_app.utils.RecyclerItemClickListener;
import com.tiagosantos.enchantedviewpager.EnchantedViewPager;

import java.util.ArrayList;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment {

    private Methods methods;
    private EnchantedViewPager enchantedViewPager;
    private HomePagerAdapter homePagerAdapter;
    private RecyclerView rv_artist, rv_songs, rv_albums, rv_recent;
    private ArrayList<ItemSong> arrayList_recent;
    private ArrayList<ItemAlbums> arrayList_banner;
    private ArrayList<ItemArtist> arrayList_artist;
    private ArrayList<ItemAlbums> arrayList_albums;
    private ArrayList<ItemSong> arrayList_trend_songs;
    private AdapterRecent adapterTrending, adapterRecent;
    private AdapterArtistHome adapterArtistHome;
    private AdapterAlbumsHome adapterAlbumsHome;
    private CircularProgressBar progressBar;
    private FrameLayout frameLayout;
    private String addedFrom = "";

    private LinearLayout ll_trending, ll_artist, ll_albums, ll_recent;
    private TextView tv_songs_all, tv_recent_all, tv_albums_all, tv_artist_all;
    private LinearLayout ll_home;
    private String errr_msg;
    private ApiManager apiManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        apiManager = Common.getAPI();
        methods = new Methods(getActivity(), new OnClickListener() {
            @Override
            public void onClick(int position, String type) {
                if (type.equals(getString(R.string.songs))) {
                    Constant.isOnline = true;
                    addedFrom = "trend";
                    if (!Constant.addedFrom.equals(addedFrom)) {
                        Constant.arrayList_play.clear();
                        Constant.arrayList_play.addAll(arrayList_trend_songs);
                        Constant.addedFrom = addedFrom;
                        Constant.isNewAdded = true;
                    }
                    Constant.playPos = position;

                    Intent intent = new Intent(getActivity(), PlayerService.class);
                    intent.setAction(PlayerService.ACTION_PLAY);
                    getActivity().startService(intent);
                } else if (type.equals(getString(R.string.recent))) {
                    addedFrom = "recent";
                    Constant.isOnline = true;
                    if (!Constant.addedFrom.equals(addedFrom)) {
                        Constant.arrayList_play.clear();
                        Constant.arrayList_play.addAll(arrayList_recent);
                        Constant.addedFrom = addedFrom;
                        Constant.isNewAdded = true;
                    }
                    Constant.playPos = position;

                    Intent intent = new Intent(getActivity(), PlayerService.class);
                    intent.setAction(PlayerService.ACTION_PLAY);
                    getActivity().startService(intent);
                } else if (type.equals(getString(R.string.artist))) {

                    FragmentAlbumsByArtist f_all_songs = new FragmentAlbumsByArtist();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("item", arrayList_artist.get(position));
                    f_all_songs.setArguments(bundle);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.hide(getFragmentManager().getFragments().get(getFragmentManager().getBackStackEntryCount()));
                    ft.add(R.id.fragment, f_all_songs, getString(R.string.albums));
                    ft.addToBackStack(getString(R.string.albums));
                    ft.commit();
                    ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.albums));

                } else if (type.equals(getString(R.string.albums))) {
                    Intent intent = new Intent(getActivity(), SongByCategoryActivity.class);
                    intent.putExtra("type", getString(R.string.albums));
                    intent.putExtra("id", arrayList_albums.get(position).getId().toString());
                    intent.putExtra("name", arrayList_albums.get(position).getName());
                    startActivity(intent);
                }
//                else if (type.equals(getString(R.string.banner))) {
//                    Intent intent = new Intent(getActivity(), SongByCategoryActivity.class);
//                    intent.putExtra("type", getString(R.string.banner));
//                    intent.putExtra("id", arrayList_banner.get(position).getId());
//                    intent.putExtra("name", arrayList_banner.get(position).getName());
////                    intent.putExtra("songs", arrayList_banner.get(position).getArrayListSongs());
//                    startActivity(intent);
//                }
            }
        });

        arrayList_recent = new ArrayList<>();
        arrayList_artist = new ArrayList<>();
        arrayList_banner = new ArrayList<>();
        arrayList_albums = new ArrayList<>();
        arrayList_trend_songs = new ArrayList<>();

        progressBar = rootView.findViewById(R.id.pb_home);
        frameLayout = rootView.findViewById(R.id.fl_empty);

        LayoutInflater inflat = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View myView = inflat.inflate(R.layout.layout_err_internet, null);
        frameLayout.addView(myView);
        myView.findViewById(R.id.btn_empty_try).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHome();
            }
        });

        enchantedViewPager = rootView.findViewById(R.id.viewPager_home);
        enchantedViewPager.useAlpha();
        enchantedViewPager.useScale();

        rv_artist = rootView.findViewById(R.id.rv_home_artist);
        LinearLayoutManager llm_artist = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_artist.setLayoutManager(llm_artist);
        rv_artist.setItemAnimator(new DefaultItemAnimator());
        rv_artist.setHasFixedSize(true);

        rv_albums = rootView.findViewById(R.id.rv_home_albums);
        LinearLayoutManager llm_albums = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_albums.setLayoutManager(llm_albums);
        rv_albums.setItemAnimator(new DefaultItemAnimator());
        rv_albums.setHasFixedSize(true);

        rv_songs = rootView.findViewById(R.id.rv_home_songs);
        LinearLayoutManager llm_songs = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_songs.setLayoutManager(llm_songs);
        rv_songs.setItemAnimator(new DefaultItemAnimator());
        rv_songs.setHasFixedSize(true);

        rv_recent = rootView.findViewById(R.id.rv_home_recent);
        LinearLayoutManager llm_recent = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_recent.setLayoutManager(llm_recent);
        rv_recent.setItemAnimator(new DefaultItemAnimator());
        rv_recent.setHasFixedSize(true);

        ll_home = rootView.findViewById(R.id.ll_home);

        ll_trending = rootView.findViewById(R.id.ll_trending);
        ll_artist = rootView.findViewById(R.id.ll_artist);
        ll_albums = rootView.findViewById(R.id.ll_albums);
        ll_recent = rootView.findViewById(R.id.ll_recent);

        tv_artist_all = rootView.findViewById(R.id.tv_home_artist_all);
        tv_songs_all = rootView.findViewById(R.id.tv_home_songs_all);
        tv_albums_all = rootView.findViewById(R.id.tv_home_albums_all);

        loadHome();

        rv_artist.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                methods.onClick(position, getString(R.string.artist));
            }
        }));

        rv_albums.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                methods.onClick(position, getString(R.string.albums));
            }
        }));

        tv_artist_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentArtist f_art = new FragmentArtist();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.hide(getFragmentManager().getFragments().get(getFragmentManager().getBackStackEntryCount()));
                ft.add(R.id.fragment, f_art, getString(R.string.artist));
                ft.addToBackStack(getString(R.string.artist));
                ft.commit();
                ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.artist));
            }
        });

        tv_albums_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentAlbums f_albums = new FragmentAlbums();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.hide(getFragmentManager().getFragments().get(getFragmentManager().getBackStackEntryCount()));
                ft.add(R.id.fragment, f_albums, getString(R.string.albums));
                ft.addToBackStack(getString(R.string.albums));
                ft.commit();
                ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.albums));
            }
        });

        tv_songs_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentSongs f_all_songs = new FragmentSongs();
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                ft.hide(getFragmentManager().getFragments().get(getFragmentManager().getBackStackEntryCount()));
//                ft.add(R.id.fragment, f_all_songs, getString(R.string.all_songs));
//                ft.addToBackStack(getString(R.string.all_songs));
//                ft.commit();
//                ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.all_songs));
            }
        });
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(queryTextListener);
        super.onCreateOptionsMenu(menu, inflater);
    }

    SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
//            Constant.search_item = s.replace(" ", "%20");
//            FragmentSearch fsearch = new FragmentSearch();
//            FragmentManager fm = getFragmentManager();
//            FragmentTransaction ft = fm.beginTransaction();
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            ft.hide(fm.findFragmentByTag(getString(R.string.home)));
//            ft.add(R.id.fragment, fsearch, getString(R.string.search));
//            ft.addToBackStack(getString(R.string.search));
//            ft.commit();
            return true;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    };

    private void loadHome() {
        if (methods.isNetworkAvailable()) {
            frameLayout.setVisibility(View.GONE);
            ll_home.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            apiManager.homePage()
                    .enqueue(new Callback<BaseResponse<ItemHome>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<ItemHome>> call, Response<BaseResponse<ItemHome>> response) {
                            arrayList_banner.addAll(response.body().getContent().getBanner());
                            arrayList_albums.addAll(response.body().getContent().getListAlbum());
                            arrayList_artist.addAll(response.body().getContent().getListArtist());
                            arrayList_trend_songs.addAll(response.body().getContent().getListTrending());
//                            arrayList_recent.addAll(dbHelper.loadDataRecent(true, "15"));
//                            if (Constant.arrayList_play.size() == 0 && arrayListSongs.size() > 0) {
//                                Constant.arrayList_play.addAll(arrayListSongs);
//                                ((DrawerActivity) getActivity()).changeText(Constant.arrayList_play.get(0), "home");
//                            }

                            loadEmpty();

                            homePagerAdapter = new HomePagerAdapter(getActivity(), arrayList_banner);
                            enchantedViewPager.setAdapter(homePagerAdapter);
                            if (homePagerAdapter.getCount() > 2) {
                                enchantedViewPager.setCurrentItem(1);
                            }

                            adapterArtistHome = new AdapterArtistHome(arrayList_artist);
                            rv_artist.setAdapter(adapterArtistHome);

                            adapterTrending = new AdapterRecent(getActivity(), arrayList_trend_songs, new ClickListenerPlaylist() {
                                @Override
                                public void onClick(int position) {
                                }
                                @Override
                                public void onItemZero() {
                                }
                            });
                            rv_songs.setAdapter(adapterTrending);
//                            rv_recent.setAdapter(adapterRecent);

                            adapterAlbumsHome = new AdapterAlbumsHome(arrayList_albums);
                            rv_albums.setAdapter(adapterAlbumsHome);
                            ll_home.setVisibility(View.VISIBLE);
                            errr_msg = getString(R.string.err_no_artist_found);
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<ItemHome>> call, Throwable t) {
                            errr_msg = getString(R.string.err_server);
                        }
                    });
        } else {
            errr_msg = getString(R.string.err_internet_not_conn);
            frameLayout.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }

    private void loadEmpty() {

        if (arrayList_recent.size() < 3) {
            ll_recent.setVisibility(View.GONE);
        } else {
            ll_recent.setVisibility(View.VISIBLE);
        }

        if (arrayList_trend_songs.size() == 0) {
            ll_trending.setVisibility(View.GONE);
        } else {
            ll_trending.setVisibility(View.VISIBLE);
        }

        if (arrayList_artist.size() == 0) {
            ll_artist.setVisibility(View.GONE);
        } else {
            ll_artist.setVisibility(View.VISIBLE);
        }

        if (arrayList_albums.size() == 0) {
            ll_albums.setVisibility(View.GONE);
        } else {
            ll_albums.setVisibility(View.VISIBLE);
        }
    }
}