package com.sem4.music_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.sem4.music_app.R;
import com.sem4.music_app.interfaces.ClickListenerPlaylist;
import com.sem4.music_app.item.ItemSong;
import com.sem4.music_app.utils.Constant;
import com.sem4.music_app.utils.Methods;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterRecent extends RecyclerView.Adapter<AdapterRecent.MyViewHolder> {

    private Methods methods;
    private Context context;
    private ArrayList<ItemSong> arrayList;
    private ClickListenerPlaylist clickListenerPlayList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView iv_song;
        ImageView iv_more;
        TextView tv_title, tv_cat;

        MyViewHolder(View view) {
            super(view);
            iv_song = view.findViewById(R.id.iv_recent);
            iv_more = view.findViewById(R.id.iv_recent_more);
            tv_title = view.findViewById(R.id.tv_recent_song);
            tv_cat = view.findViewById(R.id.tv_recent_cat);
        }
    }

    public AdapterRecent(Context context, ArrayList<ItemSong> arrayList, ClickListenerPlaylist clickListenerPlayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.clickListenerPlayList = clickListenerPlayList;
        methods = new Methods(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recent, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

//        holder.tv_title.setText(arrayList.get(position).getTitle());
//        holder.tv_cat.setText(arrayList.get(position).getArtists().get(0).getName());
//        Picasso.get()
//                .load(arrayList.get(position).getImage())
//                .placeholder(R.drawable.placeholder_song)
//                .into(holder.iv_song);
//
//        holder.iv_more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openOptionPopUp(holder.iv_more, holder.getAdapterPosition());
//            }
//        });
//
//        holder.iv_song.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clickListenerPlayList.onClick(holder.getAdapterPosition());
//            }
//        });
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void openOptionPopUp(ImageView imageView, final int pos) {
        PopupMenu popup = new PopupMenu(context, imageView);
        popup.getMenuInflater().inflate(R.menu.popup_song, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.popup_add_song:
//                        methods.openPlaylists(arrayList.get(pos), true);
//                        break;
//                    case R.id.popup_download:
//                        methods.download(arrayList.get(pos));
//                        break;
//                }
                return true;
            }
        });
        popup.show();
    }
}