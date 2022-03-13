package com.sem4.music_app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.sem4.music_app.adapter.AdapterOfflineSongList;
import com.sem4.music_app.interfaces.ClickListenerPlaylist;
import com.sem4.music_app.interfaces.OnClickListener;
import com.sem4.music_app.item.ItemAlbums;
import com.sem4.music_app.service.PlayerService;
import com.sem4.music_app.utils.Constant;
import com.sem4.music_app.utils.GlobalBus;
import com.sem4.music_app.utils.Methods;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class FragmentOfflineSong extends Fragment {

    private Methods methods;
    private RecyclerView rv;
    private AdapterOfflineSongList adapter;
    private CircularProgressBar progressBar;

    private FrameLayout frameLayout;
    private String errr_msg = "";
    private SearchView searchView;
    private String addedFrom = "offSong";

    public static FragmentOfflineSong newInstance(int sectionNumber) {
        FragmentOfflineSong fragment = new FragmentOfflineSong();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_song_by_category, container, false);

        methods = new Methods(getActivity(), new OnClickListener() {
            @Override
            public void onClick(int position, String type) {
                try {
                    Constant.isOnline = false;
                    if (!Constant.addedFrom.equals(addedFrom)) {
                        Constant.arrayList_play.clear();
                        Constant.arrayList_play.addAll(Constant.arrayListOfflineSongs);
                        Constant.addedFrom = addedFrom;
                        Constant.isNewAdded = true;
                    }
                    Constant.playPos = position;

                    Intent intent = new Intent(getActivity(), PlayerService.class);
                    intent.setAction(PlayerService.ACTION_PLAY);
                    getActivity().startService(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        errr_msg = getString(R.string.err_no_songs_found);

        progressBar = rootView.findViewById(R.id.pb_song_by_cat);
        frameLayout = rootView.findViewById(R.id.fl_empty);

        rv = rootView.findViewById(R.id.rv_song_by_cat);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);

        new LoadOfflineSongs().execute();

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(queryTextListener);
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

    class LoadOfflineSongs extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            frameLayout.setVisibility(View.GONE);
            rv.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            if (Constant.arrayListOfflineSongs.size() == 0) {
                methods.getListOfflineSongs();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (getActivity() != null) {
                setAdapter();
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    private void setAdapter() {
        adapter = new AdapterOfflineSongList(getActivity(), Constant.arrayListOfflineSongs, new ClickListenerPlaylist() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onItemZero() {

            }
        }, "");
        rv.setAdapter(adapter);
        setEmpty();
    }

    public void setEmpty() {
        if (Constant.arrayListOfflineSongs.size() > 0) {
            rv.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
        } else {
            rv.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);

            frameLayout.removeAllViews();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View myView = inflater.inflate(R.layout.layout_err_nodata, null);

            TextView textView = myView.findViewById(R.id.tv_empty_msg);
            textView.setText(errr_msg);
            myView.findViewById(R.id.btn_empty_try).setVisibility(View.GONE);


            frameLayout.addView(myView);
        }
    }

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
}