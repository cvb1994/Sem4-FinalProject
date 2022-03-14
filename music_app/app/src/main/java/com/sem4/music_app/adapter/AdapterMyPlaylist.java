package com.sem4.music_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.sem4.music_app.R;
import com.sem4.music_app.interfaces.ClickListenerPlaylist;
import com.sem4.music_app.item.ItemMyPlayList;
import com.sem4.music_app.network.ApiManager;
import com.sem4.music_app.network.Common;
import com.sem4.music_app.response.BaseResponse;
import com.sem4.music_app.utils.Methods;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterMyPlaylist extends RecyclerView.Adapter<AdapterMyPlaylist.MyViewHolder> {

    private Context context;
    private ArrayList<ItemMyPlayList> arrayList;
    private ArrayList<ItemMyPlayList> filteredArrayList;
    private NameFilter filter;
    private ClickListenerPlaylist clickListenerPlayList;
    private int columnWidth = 0;
    private Boolean isOnline;
    private Methods methods;
    ApiManager apiManager = Common.getAPI();

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView_more, imageView1, imageView2, imageView3, imageView4;
        RelativeLayout rl;

        MyViewHolder(View view) {
            super(view);
            rl = view.findViewById(R.id.rl_myplaylist);
            textView = view.findViewById(R.id.tv_myplaylist);
            imageView_more = view.findViewById(R.id.iv_more_myplaylist);
            imageView1 = view.findViewById(R.id.iv_myplaylist1);
            imageView2 = view.findViewById(R.id.iv_myplaylist2);
            imageView3 = view.findViewById(R.id.iv_myplaylist3);
            imageView4 = view.findViewById(R.id.iv_myplaylist4);
        }
    }

    public AdapterMyPlaylist(Context context, ArrayList<ItemMyPlayList> arrayList, ClickListenerPlaylist clickListenerPlayList, Boolean isOnline) {
        this.arrayList = arrayList;
        this.filteredArrayList = arrayList;
        this.context = context;
        this.isOnline = isOnline;
        this.clickListenerPlayList = clickListenerPlayList;
        methods = new Methods(context);
        columnWidth = methods.getColumnWidth(2, 5);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_my_playlist, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.textView.setText(arrayList.get(position).getName());

        if(isOnline) {
            if(arrayList.get(position).getSongs().size() > 0){
                if(arrayList.get(position).getSongs().size() == 1){
                    Picasso.get()
                            .load(arrayList.get(position).getSongs().get(0).getImageBig())
                            .into(holder.imageView1);
                    Picasso.get()
                            .load(arrayList.get(position).getSongs().get(0).getImageBig())
                            .into(holder.imageView2);
                    Picasso.get()
                            .load(arrayList.get(position).getSongs().get(0).getImageBig())
                            .into(holder.imageView3);
                    Picasso.get()
                            .load(arrayList.get(position).getSongs().get(0).getImageBig())
                            .into(holder.imageView4);
                }else if(arrayList.get(position).getSongs().size() == 2){
                    Picasso.get()
                            .load(arrayList.get(position).getSongs().get(0).getImageBig())
                            .into(holder.imageView1);
                    Picasso.get()
                            .load(arrayList.get(position).getSongs().get(1).getImageBig())
                            .into(holder.imageView2);
                    Picasso.get()
                            .load(arrayList.get(position).getSongs().get(1).getImageBig())
                            .into(holder.imageView3);
                    Picasso.get()
                            .load(arrayList.get(position).getSongs().get(0).getImageBig())
                            .into(holder.imageView4);
                }else if (arrayList.get(position).getSongs().size() == 3){
                    Picasso.get()
                            .load(arrayList.get(position).getSongs().get(0).getImageBig())
                            .into(holder.imageView1);
                    Picasso.get()
                            .load(arrayList.get(position).getSongs().get(1).getImageBig())
                            .into(holder.imageView2);
                    Picasso.get()
                            .load(arrayList.get(position).getSongs().get(2).getImageBig())
                            .into(holder.imageView3);
                    Picasso.get()
                            .load(arrayList.get(position).getSongs().get(0).getImageBig())
                            .into(holder.imageView4);
                }else {
                    Picasso.get()
                            .load(arrayList.get(position).getSongs().get(0).getImageBig())
                            .into(holder.imageView1);
                    Picasso.get()
                            .load(arrayList.get(position).getSongs().get(1).getImageBig())
                            .into(holder.imageView2);
                    Picasso.get()
                            .load(arrayList.get(position).getSongs().get(2).getImageBig())
                            .into(holder.imageView3);
                    Picasso.get()
                            .load(arrayList.get(position).getSongs().get(3).getImageBig())
                            .into(holder.imageView4);
                }
            }else{
                Picasso.get()
                        .load(R.drawable.placeholder_song)
                        .into(holder.imageView1);
                Picasso.get()
                        .load(R.drawable.placeholder_song)
                        .into(holder.imageView2);
                Picasso.get()
                        .load(R.drawable.placeholder_song)
                        .into(holder.imageView3);
                Picasso.get()
                        .load(R.drawable.placeholder_song)
                        .into(holder.imageView4);
            }
        }

        holder.rl.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, columnWidth));

        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListenerPlayList.onClick(holder.getAdapterPosition());
            }
        });

        holder.imageView_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOptionPopUp(holder.imageView_more, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public ItemMyPlayList getItem(int pos) {
        return arrayList.get(pos);
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
                ArrayList<ItemMyPlayList> filteredItems = new ArrayList<>();

                for (int i = 0, l = filteredArrayList.size(); i < l; i++) {
                    String nameList = filteredArrayList.get(i).getName();
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

            arrayList = (ArrayList<ItemMyPlayList>) results.values;
            notifyDataSetChanged();
        }
    }

    private void openOptionPopUp(ImageView imageView, final int pos) {
        PopupMenu popup = new PopupMenu(context, imageView);
        popup.getMenuInflater().inflate(R.menu.popup_playlist, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popup_option_playlist:
                        apiManager.deletePlaylist(arrayList.get(pos).getId())
                                .enqueue(new Callback<BaseResponse<List<ItemMyPlayList>>>() {
                                    @Override
                                    public void onResponse(Call<BaseResponse<List<ItemMyPlayList>>> call, Response<BaseResponse<List<ItemMyPlayList>>> response) {
                                        Toast.makeText(context, context.getString(R.string.remove_playlist), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<BaseResponse<List<ItemMyPlayList>>> call, Throwable t) {
                                        Toast.makeText(context, context.getString(R.string.err_server), Toast.LENGTH_SHORT).show();
                                    }
                                });
                        arrayList.remove(pos);
                        notifyItemRemoved(pos);

                        if (arrayList.size() == 0) {
                            clickListenerPlayList.onItemZero();
                        }
                        break;
                }
                return true;
            }
        });
        popup.show();
    }
}