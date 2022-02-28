package com.sem4.music_app.adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.sem4.music_app.R;
import com.sem4.music_app.item.ItemAlbums;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterAlbumsHome extends RecyclerView.Adapter<AdapterAlbumsHome.MyViewHolder> {

    private ArrayList<ItemAlbums> arrayList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView iv_song;
        TextView tv_title;

        MyViewHolder(View view) {
            super(view);
            iv_song = view.findViewById(R.id.iv_albums);
            tv_title = view.findViewById(R.id.tv_album_name);
        }
    }

    public AdapterAlbumsHome(ArrayList<ItemAlbums> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_albums_home, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_title.setTypeface(holder.tv_title.getTypeface(), Typeface.BOLD);
        holder.tv_title.setText(arrayList.get(position).getName());
        Picasso.get()
                .load(arrayList.get(position).getAvatar())
                .placeholder(R.drawable.placeholder_song)
                .into(holder.iv_song);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}