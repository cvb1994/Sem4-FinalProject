package com.sem4.music_app.fragment;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.app.Dialog;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sem4.music_app.R;
import com.sem4.music_app.activity.SongByPlaylistActivity;
import com.sem4.music_app.adapter.AdapterMyPlaylist;
import com.sem4.music_app.interfaces.ClickListenerPlaylist;
import com.sem4.music_app.interfaces.OnClickListener;
import com.sem4.music_app.item.ItemMyPlayList;
import com.sem4.music_app.network.ApiManager;
import com.sem4.music_app.network.Common;
import com.sem4.music_app.response.BaseResponse;
import com.sem4.music_app.utils.Constant;
import com.sem4.music_app.utils.Methods;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPlaylist extends Fragment {

    private Methods methods;
    private RecyclerView rv;
    private Button button_add;
    private AdapterMyPlaylist adapterMyPlaylist;
    private ArrayList<ItemMyPlayList> arrayList;
    private FrameLayout frameLayout;
    private SearchView searchView;
    String errr_msg = "";
    ApiManager apiManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_playlist, container, false);

        apiManager = Common.getAPI();
        methods = new Methods(getActivity(), new OnClickListener() {
            @Override
            public void onClick(int position, String type) {
                Intent intent = new Intent(getActivity(), SongByPlaylistActivity.class);
                intent.putExtra("item", adapterMyPlaylist.getItem(position));
                startActivity(intent);
            }
        });

        arrayList = new ArrayList<>();

        button_add = rootView.findViewById(R.id.button_add_myplaylist);
        frameLayout = rootView.findViewById(R.id.fl_empty);

        rv = rootView.findViewById(R.id.rv_myplaylist);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rv.setLayoutManager(gridLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);
        rv.setNestedScrollingEnabled(false);

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddPlaylistDialog();
            }
        });

        loadPlaylists();

        setHasOptionsMenu(true);
        return rootView;
    }

    private void setAdapter(){
        adapterMyPlaylist = new AdapterMyPlaylist(getActivity(), arrayList, new ClickListenerPlaylist() {
            @Override
            public void onClick(int position) {
                methods.onClick(position, "");
            }

            @Override
            public void onItemZero() {
                setEmpty();
            }
        }, true);

        rv.setAdapter(adapterMyPlaylist);
        setEmpty();
    }

    private void loadPlaylists(){
        if (methods.isNetworkAvailable()) {
            if (arrayList.size() == 0) {
                arrayList.clear();
                frameLayout.setVisibility(View.GONE);
                rv.setVisibility(View.GONE);
            }
            apiManager.getAllPlaylist(Constant.itemUser.getId())
                    .enqueue(new Callback<BaseResponse<List<ItemMyPlayList>>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<List<ItemMyPlayList>>> call, Response<BaseResponse<List<ItemMyPlayList>>> response) {
                            if (response.body().getContent().size() == 0) {
                                errr_msg = getString(R.string.err_no_playlist_found);
                                setEmpty();
                            } else {
                                List<ItemMyPlayList> itemMyPlayLists = response.body().getContent();
                                for (int i = 0; i < itemMyPlayLists.size(); i++) {
                                    if(!itemMyPlayLists.get(i).getName().equals(getString(R.string.playlist_like))){
                                        arrayList.add(itemMyPlayLists.get(i));
                                    }
                                }
                                setAdapter();
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<List<ItemMyPlayList>>> call, Throwable t) {
                            errr_msg = getString(R.string.err_server);
                            setEmpty();
                        }
                    });
        }
        else{
            errr_msg = getString(R.string.err_internet_not_conn);
            setEmpty();
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(queryTextListener);
        super.onCreateOptionsMenu(menu, inflater);
    }

    SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            if (adapterMyPlaylist != null) {
                if (!searchView.isIconified()) {
                    adapterMyPlaylist.getFilter().filter(s);
                    adapterMyPlaylist.notifyDataSetChanged();
                }
            }
            return true;
        }
    };

    private void setEmpty() {
        if (arrayList.size() > 0) {
            frameLayout.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        } else {
            frameLayout.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);

            frameLayout.removeAllViews();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View myView = inflater.inflate(R.layout.layout_err_nodata, null);
            if (errr_msg.equals(getString(R.string.err_no_playlist_found))) {
                myView = inflater.inflate(R.layout.layout_err_nodata, null);
            } else if (errr_msg.equals(getString(R.string.err_internet_not_conn))) {
                myView = inflater.inflate(R.layout.layout_err_internet, null);
            } else if (errr_msg.equals(getString(R.string.err_server))) {
                myView = inflater.inflate(R.layout.layout_err_server, null);
            }

            TextView textView = myView.findViewById(R.id.tv_empty_msg);
            textView.setText(errr_msg.equals("") ? getString(R.string.err_no_playlist_found) : errr_msg);

            myView.findViewById(R.id.btn_empty_try).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadPlaylists();
                }
            });
            frameLayout.addView(myView);
        }
    }

    private void openAddPlaylistDialog() {
        final InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_add_playlist);

        final EditText editText = dialog.findViewById(R.id.et_dialog_addplay);
        final ImageView iv_close = dialog.findViewById(R.id.iv_addplay_close);
        final Button button = dialog.findViewById(R.id.button_addplay_add);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText.getText().toString().trim().isEmpty()) {
                    apiManager.addPlaylist(editText.getText().toString().trim(), Constant.itemUser.getId())
                            .enqueue(new Callback<BaseResponse<Integer>>() {
                                @Override
                                public void onResponse(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
                                    arrayList.add(new ItemMyPlayList(response.body().getContent().toString(), editText.getText().toString().trim()));
                                    Toast.makeText(getActivity(), getString(R.string.playlist_added), Toast.LENGTH_SHORT).show();
                                    adapterMyPlaylist.notifyDataSetChanged();
                                    setAdapter();
                                }

                                @Override
                                public void onFailure(Call<BaseResponse<Integer>> call, Throwable t) {
                                    Toast.makeText(getActivity(), getString(R.string.err_server), Toast.LENGTH_SHORT).show();
                                }
                            });

                    dialog.dismiss();
                }
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        new Handler().post(
                new Runnable() {
                    public void run() {
                        inputMethodManager.showSoftInput(editText, 0);
                        editText.requestFocus();
                    }
                });
    }
}
