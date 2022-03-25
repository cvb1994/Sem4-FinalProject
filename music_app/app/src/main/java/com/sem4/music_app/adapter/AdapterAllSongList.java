package com.sem4.music_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.sem4.music_app.R;
import com.sem4.music_app.interfaces.ClickListenerPlaylist;
import com.sem4.music_app.item.ItemArtist;
import com.sem4.music_app.item.ItemSong;
import com.sem4.music_app.network.ApiManager;
import com.sem4.music_app.network.Common;
import com.sem4.music_app.response.BaseResponse;
import com.sem4.music_app.service.PlayerService;
import com.sem4.music_app.utils.Constant;
import com.sem4.music_app.utils.Methods;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import es.claucookie.miniequalizerlibrary.EqualizerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterAllSongList extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<ItemSong> arrayList;
    private ArrayList<ItemSong> filteredArrayList;
    private ClickListenerPlaylist recyclerClickListener;
    private NameFilter filter;
    private String type;
    private Methods methods;
    private String playlistId;
    ApiManager apiManager = Common.getAPI();

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public AdapterAllSongList(Context context, ArrayList<ItemSong> arrayList, ClickListenerPlaylist recyclerClickListener, String type) {
        this.arrayList = arrayList;
        this.filteredArrayList = arrayList;
        this.context = context;
        this.type = type;
        this.recyclerClickListener = recyclerClickListener;
        methods = new Methods(context);
    }

    public AdapterAllSongList(String playlistId, Context context, ArrayList<ItemSong> arrayList, ClickListenerPlaylist recyclerClickListener, String type) {
        this.playlistId = playlistId;
        this.arrayList = arrayList;
        this.filteredArrayList = arrayList;
        this.context = context;
        this.type = type;
        this.recyclerClickListener = recyclerClickListener;
        methods = new Methods(context);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_song, textView_duration, textView_catname, tv_views, tv_fav, tv_vip;
        EqualizerView equalizer;
        ImageView imageView, imageView_option, iv_fav_icon;
        RelativeLayout rl;

        MyViewHolder(View view) {
            super(view);
            rl = view.findViewById(R.id.ll_songlist);
            tv_views = view.findViewById(R.id.tv_songlist_views);
//            tv_fav = view.findViewById(R.id.tv_songlist_fav);
            textView_song = view.findViewById(R.id.tv_songlist_name);
            tv_vip = view.findViewById(R.id.tv_songlist_vip);
            textView_duration = view.findViewById(R.id.tv_songlist_duration);
            equalizer = view.findViewById(R.id.equalizer_view);
            textView_catname = view.findViewById(R.id.tv_songlist_cat);
            imageView = view.findViewById(R.id.iv_songlist);
            imageView_option = view.findViewById(R.id.iv_songlist_option);
//            iv_fav_icon = view.findViewById(R.id.iv_fav_icon);
        }
    }

    private static class ProgressViewHolder extends RecyclerView.ViewHolder {
        private static ProgressBar progressBar;

        private ProgressViewHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.progressBar);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recent_songs, parent, false);
            return new MyViewHolder(itemView);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_progressbar, parent, false);
            return new ProgressViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {

            ((MyViewHolder) holder).tv_views.setText(methods.format(arrayList.get(position).getListenCount()));
            ((MyViewHolder) holder).tv_vip.setVisibility(arrayList.get(position).isVipOnly() ? View.VISIBLE : View.GONE);
//            ((MyViewHolder) holder).tv_download.setText(methods.format(Double.parseDouble(arrayList.get(position).getDownloads())));

            ((MyViewHolder) holder).textView_song.setText(arrayList.get(position).getTitle());
            ((MyViewHolder) holder).textView_duration.setText(arrayList.get(position).getDuration());
            Picasso.get()
                    .load(arrayList.get(position).getImageBig())
                    .placeholder(R.drawable.placeholder_song)
                    .into(((MyViewHolder) holder).imageView);

            if (PlayerService.getIsPlayling() && Constant.arrayList_play.get(Constant.playPos).getId().equals(arrayList.get(position).getId())) {
                ((MyViewHolder) holder).imageView.setVisibility(View.GONE);
                ((MyViewHolder) holder).equalizer.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).equalizer.animateBars();
            } else {
                ((MyViewHolder) holder).imageView.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).equalizer.setVisibility(View.GONE);
                ((MyViewHolder) holder).equalizer.stopBars();
            }

            if(arrayList.get(position).getArtists() != null){
                if (arrayList.get(position).getArtists().size() != 0) {
                    StringBuilder artists = new StringBuilder();
                    List<ItemArtist> artistList = arrayList.get(position).getArtists();
                    for (int i = 0; i < artistList.size(); i++) {
                        if(i != 0){
                            artists.append(", ").append(artistList.get(i).getName());
                        }else{
                            artists.append(artistList.get(i).getName());
                        }
                    }
                    ((MyViewHolder) holder).textView_catname.setText(artists);
                }
            }

            ((MyViewHolder) holder).rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(arrayList.size() > holder.getAdapterPosition()) {
                        recyclerClickListener.onClick(getPosition(arrayList.get(holder.getAdapterPosition()).getId()));
                    }
                }
            });

            if(arrayList.get(position).isVipOnly()){
                if(Constant.itemUser.isVip()){
                    ((MyViewHolder) holder).imageView_option.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).imageView_option.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openOptionPopUp(((MyViewHolder) holder).imageView_option, holder.getAdapterPosition());
                        }
                    });
                }else {
                    ((MyViewHolder) holder).imageView_option.setVisibility(View.GONE);
                }
            }else{
                ((MyViewHolder) holder).imageView_option.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).imageView_option.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openOptionPopUp(((MyViewHolder) holder).imageView_option, holder.getAdapterPosition());
                    }
                });
            }

        }
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (arrayList.get(position) != null) {
            return VIEW_ITEM;
        } else {
            return VIEW_PROG;
        }
    }

    private int getPosition(String id) {
        int count=0;
        for(int i=0;i<filteredArrayList.size();i++) {
            if(id.equals(filteredArrayList.get(i).getId())) {
                count = i;
                break;
            }
        }
        return count;
    }

    private void openOptionPopUp(ImageView imageView, final int pos) {
        if(arrayList.get(pos).isVipOnly()){
            if(Constant.itemUser.isVip()){
                PopupMenu popup = new PopupMenu(context, imageView);
                popup.getMenuInflater().inflate(R.menu.popup_song, popup.getMenu());
                if (type.equals("playlist")) {
                    popup.getMenu().findItem(R.id.popup_add_song).setTitle(context.getString(R.string.delete));
                }
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.popup_add_song:
                                switch (type) {
                                    case "playlist":
                                        apiManager.removeFromPlaylist(playlistId , arrayList.get(pos).getId())
                                                .enqueue(new Callback<BaseResponse>() {
                                                    @Override
                                                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                                        Toast.makeText(context, context.getString(R.string.remove_from_playlist), Toast.LENGTH_SHORT).show();
                                                        arrayList.remove(pos);
                                                        notifyItemRemoved(pos);
                                                        if (arrayList.size() == 0) {
                                                            recyclerClickListener.onItemZero();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<BaseResponse> call, Throwable t) {

                                                    }
                                                });

                                        break;
                                    default:
                                        methods.openPlaylists(arrayList.get(pos), true);
                                        break;
                                }
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        }else{
            PopupMenu popup = new PopupMenu(context, imageView);
            popup.getMenuInflater().inflate(R.menu.popup_song, popup.getMenu());
            if (type.equals("playlist")) {
                popup.getMenu().findItem(R.id.popup_add_song).setTitle(context.getString(R.string.delete));
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.popup_add_song:
                            switch (type) {
                                case "playlist":
                                    apiManager.removeFromPlaylist(playlistId , arrayList.get(pos).getId())
                                            .enqueue(new Callback<BaseResponse>() {
                                                @Override
                                                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                                    Toast.makeText(context, context.getString(R.string.remove_from_playlist), Toast.LENGTH_SHORT).show();
                                                    arrayList.remove(pos);
                                                    notifyItemRemoved(pos);
                                                    if (arrayList.size() == 0) {
                                                        recyclerClickListener.onItemZero();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<BaseResponse> call, Throwable t) {

                                                }
                                            });

                                    break;
                                default:
                                    methods.openPlaylists(arrayList.get(pos), true);
                                    break;
                            }
                            break;
                    }
                    return true;
                }
            });
            popup.show();
        }
    }

    public Filter getFilter() {
        if (filter == null) {
            filter = new NameFilter();
        }
        return filter;
    }

    private class NameFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (constraint.toString().length() > 0) {
                ArrayList<ItemSong> filteredItems = new ArrayList<>();

                for (int i = 0, l = filteredArrayList.size(); i < l; i++) {
                    String nameList = filteredArrayList.get(i).getTitle();
                    if (nameList.toLowerCase().contains(constraint))
                        filteredItems.add(filteredArrayList.get(i));
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            } else {
                synchronized (this) {
                    result.values = filteredArrayList;
                    result.count = filteredArrayList.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            arrayList = (ArrayList<ItemSong>) results.values;
            notifyDataSetChanged();
        }
    }
}