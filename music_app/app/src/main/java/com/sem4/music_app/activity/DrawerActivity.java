package com.sem4.music_app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sem4.music_app.R;
import com.sem4.music_app.item.ItemArtist;
import com.sem4.music_app.item.ItemMyPlayList;
import com.sem4.music_app.item.ItemSong;
import com.sem4.music_app.network.ApiManager;
import com.sem4.music_app.network.Common;
import com.sem4.music_app.response.BasePaginate;
import com.sem4.music_app.response.BaseResponse;
import com.sem4.music_app.service.PlayerService;
import com.sem4.music_app.utils.Constant;
import com.sem4.music_app.utils.GlobalBus;
import com.sem4.music_app.utils.MessageEvent;
import com.sem4.music_app.utils.Methods;
import com.sem4.music_app.utils.PauseableRotateAnimation;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class DrawerActivity extends AppCompatActivity implements View.OnClickListener{

    Methods methods;
    DrawerLayout drawer;
    public ViewPager viewpager;
    ImagePagerAdapter adapter;
    SlidingUpPanelLayout mLayout;
    NavigationView navigationView;
    AudioManager am;
    Toolbar toolbar;
    Boolean isExpand = false, isRotateAnim = false;
    BottomSheetDialog dialog_desc;
    RelativeLayout rl_min_header;
    LinearLayout ll_max_header;
    RelativeLayout rl_music_loading;
    private Handler seekHandler = new Handler();
    PauseableRotateAnimation rotateAnimation;
    String deviceId;

    SeekBar seekBar_music, seekbar_min;
    View view_playlist, view_round;
    TextView tv_min_title, tv_min_artist, tv_max_title, tv_max_artist, tv_music_title, tv_music_artist, tv_song_count,
            tv_current_time, tv_total_time;
    RoundedImageView iv_max_song, iv_min_song, imageView_pager;
    ImageView iv_music_bg, iv_min_previous, iv_min_play, iv_min_next, iv_max_fav, iv_music_shuffle,
            iv_music_repeat, iv_music_previous, iv_music_next, iv_music_play, iv_music_add2playlist, iv_music_share,
            iv_music_download, imageView_heart;
    Animation anim_slideup, anim_slidedown, anim_slideup_music, anim_slidedown_music;
    ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        apiManager = Common.getAPI();
        Constant.context = this;
        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        methods = new Methods(this);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        mLayout = findViewById(R.id.sliding_layout);
        toolbar = findViewById(R.id.toolbar_offline_music);
        setSupportActionBar(toolbar);
        methods.forceRTLIfSupported(getWindow());

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        adapter = new ImagePagerAdapter();

        navigationView = findViewById(R.id.nav_view);
        viewpager = findViewById(R.id.viewPager_song);
        viewpager.setOffscreenPageLimit(5);
        rl_min_header = findViewById(R.id.rl_min_header);
        ll_max_header = findViewById(R.id.ll_max_header);
        rl_music_loading = findViewById(R.id.rl_music_loading);
        seekBar_music = findViewById(R.id.seekbar_music);
        seekbar_min = findViewById(R.id.seekbar_min);
        seekbar_min.setPadding(0, 0, 0, 0);

        RelativeLayout rl = findViewById(R.id.rl);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        iv_music_bg = findViewById(R.id.iv_music_bg);
        iv_music_play = findViewById(R.id.iv_music_play);
        iv_music_next = findViewById(R.id.iv_music_next);
        iv_music_previous = findViewById(R.id.iv_music_previous);
        iv_music_shuffle = findViewById(R.id.iv_music_shuffle);
        iv_music_repeat = findViewById(R.id.iv_music_repeat);
        iv_music_add2playlist = findViewById(R.id.iv_music_add2playlist);
        view_playlist = findViewById(R.id.view_music_playlist);
        view_round = findViewById(R.id.vBgLike);

        iv_min_song = findViewById(R.id.iv_min_song);
        iv_max_song = findViewById(R.id.iv_max_song);
        iv_min_previous = findViewById(R.id.iv_min_previous);
        iv_min_play = findViewById(R.id.iv_min_play);
        iv_min_next = findViewById(R.id.iv_min_next);
        iv_max_fav = findViewById(R.id.iv_max_fav);
        imageView_heart = findViewById(R.id.ivLike);

        tv_current_time = findViewById(R.id.tv_music_time);
        tv_total_time = findViewById(R.id.tv_music_total_time);
        tv_song_count = findViewById(R.id.tv_music_song_count);
        tv_music_title = findViewById(R.id.tv_music_title);
        tv_music_artist = findViewById(R.id.tv_music_artist);
        tv_min_title = findViewById(R.id.tv_min_title);
        tv_min_artist = findViewById(R.id.tv_min_artist);
        tv_max_title = findViewById(R.id.tv_max_title);
        tv_max_artist = findViewById(R.id.tv_max_artist);

        iv_max_fav.setOnClickListener(this);

        iv_min_play.setOnClickListener(this);
        iv_min_next.setOnClickListener(this);
        iv_min_previous.setOnClickListener(this);

        iv_music_play.setOnClickListener(this);
        iv_music_next.setOnClickListener(this);
        iv_music_previous.setOnClickListener(this);
        iv_music_shuffle.setOnClickListener(this);
        iv_music_repeat.setOnClickListener(this);
        iv_music_add2playlist.setOnClickListener(this);
        if (Constant.isLoginOn) {
            if (!Constant.isLogged) {
                iv_max_fav.setVisibility(View.GONE);
                iv_music_add2playlist.setVisibility(View.GONE);
            }
        } else {
            iv_max_fav.setVisibility(View.GONE);
            iv_music_add2playlist.setVisibility(View.GONE);
        }


        ImageView iv_white_blur = findViewById(R.id.iv_music_white_blur);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (50 * methods.getScreenHeight() / 100));
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        iv_white_blur.setLayoutParams(params);

        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if (slideOffset == 0.0f) {
                    isExpand = false;

                    rl_min_header.setVisibility(View.VISIBLE);
                    ll_max_header.setVisibility(View.INVISIBLE);
                } else if (slideOffset > 0.0f && slideOffset < 1.0f) {
                    rl_min_header.setVisibility(View.VISIBLE);
                    ll_max_header.setVisibility(View.VISIBLE);

                    if (isExpand) {
                        rl_min_header.setAlpha(1.0f - slideOffset);
                        ll_max_header.setAlpha(0.0f + slideOffset);
                    } else {
                        rl_min_header.setAlpha(1.0f - slideOffset);
                        ll_max_header.setAlpha(slideOffset);
                    }
                } else {
                    isExpand = true;

                    rl_min_header.setVisibility(View.INVISIBLE);
                    ll_max_header.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    try {
                        viewpager.setCurrentItem(Constant.playPos);
                    } catch (Exception e) {
                        adapter.notifyDataSetChanged();
                        viewpager.setCurrentItem(Constant.playPos);
                    }
                }
            }
        });

        seekBar_music.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                try {
                    Intent intent = new Intent(DrawerActivity.this, PlayerService.class);
                    intent.setAction(PlayerService.ACTION_SEEKTO);
                    intent.putExtra("seekto", methods.getSeekFromPercentage(progress, methods.calculateTime(Constant.arrayList_play.get(Constant.playPos).getDuration())));
                    startService(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        newRotateAnim();
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Constant.isScrolled = true;
            }

            @Override
            public void onPageSelected(int position) {
                changeTextPager(Constant.arrayList_play.get(position));

                View view = viewpager.findViewWithTag("myview" + position);
                if (view != null) {
                    ImageView iv = view.findViewById(R.id.iv_vp_play);
                    if (Constant.playPos == position) {
                        iv.setVisibility(View.GONE);
                    } else {
                        iv.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tv_current_time.setText("00:00");
    }

    public void openRegisterVipDialog(){
        if (Constant.isLoginOn) {
            if (Constant.isLogged) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent intent1 = new Intent(DrawerActivity.this, PlayerService.class);
                                intent1.setAction(PlayerService.ACTION_PLAY);
                                startService(intent1);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                Uri uri = Uri.parse("https://music-player-ner3yu5pda-as.a.run.app/payment/" + Constant.itemUser.getId());
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Trong danh sách có bài hát VIP. Đăng ký ngay?").setPositiveButton("Để sau", dialogClickListener)
                        .setNegativeButton("OK", dialogClickListener).show();
            } else {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent intent1 = new Intent(DrawerActivity.this, PlayerService.class);
                                intent1.setAction(PlayerService.ACTION_PLAY);
                                startService(intent1);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                methods.clickLogin();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Trong danh sách có bài hát VIP. Đăng nhập ngay?").setPositiveButton("Không", dialogClickListener)
                        .setNegativeButton("OK", dialogClickListener).show();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_search_home:
//                searchView.openSearch();
                break;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_min_play:
            case R.id.iv_music_play:
                playPause();
                break;
            case R.id.iv_min_next:
            case R.id.iv_music_next:
                next();
                break;
            case R.id.iv_min_previous:
            case R.id.iv_music_previous:
                previous();
                break;
            case R.id.iv_music_shuffle:
                setShuffle();
                break;
            case R.id.iv_music_repeat:
                setRepeat();
                break;
            case R.id.iv_max_fav:
                if (Constant.arrayList_play.size() > 0) {
                    if (Constant.isOnline) {
                        methods.animateHeartButton(view);
                        view.setSelected(!view.isSelected());
                        findViewById(R.id.ivLike).setSelected(view.isSelected());
                        fav();
                    }
                } else {
                    Toast.makeText(DrawerActivity.this, getResources().getString(R.string.err_no_songs_selected), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_music_add2playlist:
                if (Constant.arrayList_play.size() > 0) {
                    methods.openPlaylists(Constant.arrayList_play.get(viewpager.getCurrentItem()), Constant.isOnline);
                } else {
                    Toast.makeText(DrawerActivity.this, getResources().getString(R.string.err_no_songs_selected), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private Runnable run = new Runnable() {
        @Override
        public void run() {
            seekUpdation();
        }
    };

    public void seekUpdation() {
        try {
            seekbar_min.setProgress(methods.getProgressPercentage(PlayerService.exoPlayer.getCurrentPosition(), methods.calculateTime(Constant.arrayList_play.get(Constant.playPos).getDuration())));
            seekBar_music.setProgress(methods.getProgressPercentage(PlayerService.exoPlayer.getCurrentPosition(), methods.calculateTime(Constant.arrayList_play.get(Constant.playPos).getDuration())));
            tv_current_time.setText(methods.milliSecondsToTimer(PlayerService.exoPlayer.getCurrentPosition()));
            seekBar_music.setSecondaryProgress(PlayerService.exoPlayer.getBufferedPercentage());
            if (PlayerService.exoPlayer.getPlayWhenReady() && Constant.isAppOpen) {
                seekHandler.removeCallbacks(run);
                seekHandler.postDelayed(run, 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playPause() {
        if (Constant.arrayList_play.size() > 0) {
            Intent intent = new Intent(DrawerActivity.this, PlayerService.class);
            if (Constant.isPlayed) {
                intent.setAction(PlayerService.ACTION_TOGGLE);
                startService(intent);
            } else {
                if (!Constant.isOnline || methods.isNetworkAvailable()) {
                    intent.setAction(PlayerService.ACTION_PLAY);
                    startService(intent);
                } else {
                    Toast.makeText(DrawerActivity.this, getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(DrawerActivity.this, getResources().getString(R.string.err_no_songs_selected), Toast.LENGTH_SHORT).show();
        }
    }

    public void next() {
        if (Constant.arrayList_play.size() > 0) {
            if (!Constant.isOnline || methods.isNetworkAvailable()) {
                isRotateAnim = false;
                Intent intent = new Intent(DrawerActivity.this, PlayerService.class);
                intent.setAction(PlayerService.ACTION_NEXT);
                startService(intent);
            } else {
                Toast.makeText(DrawerActivity.this, getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(DrawerActivity.this, getResources().getString(R.string.err_no_songs_selected), Toast.LENGTH_SHORT).show();
        }
    }

    public void previous() {
        if (Constant.arrayList_play.size() > 0) {
            if (!Constant.isOnline || methods.isNetworkAvailable()) {
                isRotateAnim = false;
                Intent intent = new Intent(DrawerActivity.this, PlayerService.class);
                intent.setAction(PlayerService.ACTION_PREVIOUS);
                startService(intent);
            } else {
                Toast.makeText(DrawerActivity.this, getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(DrawerActivity.this, getResources().getString(R.string.err_no_songs_selected), Toast.LENGTH_SHORT).show();
        }
    }

    public void setRepeat() {
        if (Constant.isRepeat) {
            Constant.isRepeat = false;
            iv_music_repeat.setImageDrawable(getResources().getDrawable(R.mipmap.ic_repeat));
        } else {
            Constant.isRepeat = true;
            iv_music_repeat.setImageDrawable(getResources().getDrawable(R.mipmap.ic_repeat_hover));
        }
    }

    public void setShuffle() {
        if (Constant.isSuffle) {
            Constant.isSuffle = false;
            iv_music_shuffle.setColorFilter(ContextCompat.getColor(DrawerActivity.this, R.color.grey));
        } else {
            Constant.isSuffle = true;
            iv_music_shuffle.setColorFilter(ContextCompat.getColor(DrawerActivity.this, R.color.colorPrimary));
        }
    }

    public void fav() {
        apiManager.checkFavorite(Constant.itemUser.getId(), Constant.arrayList_play.get(Constant.playPos).getId())
                .enqueue(new retrofit2.Callback<BaseResponse<BasePaginate<ItemSong>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<BasePaginate<ItemSong>>> call, Response<BaseResponse<BasePaginate<ItemSong>>> response) {
                        apiManager.addRemoveFavorite(Constant.itemUser.getId(), Constant.arrayList_play.get(Constant.playPos).getId())
                                .enqueue(new retrofit2.Callback<BaseResponse<BasePaginate<ItemSong>>>() {
                                    @Override
                                    public void onResponse(Call<BaseResponse<BasePaginate<ItemSong>>> call, Response<BaseResponse<BasePaginate<ItemSong>>> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<BaseResponse<BasePaginate<ItemSong>>> call, Throwable t) {

                                    }
                                });
                        Toast.makeText(DrawerActivity.this, getResources().getString(response.body().isStatus() ? R.string.removed_fav : R.string.added_fav), Toast.LENGTH_SHORT).show();
                        changeFav(!response.body().isStatus());
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<BasePaginate<ItemSong>>> call, Throwable t) {

                    }
                });
    }

    public void changeFav(Boolean isFav) {
        if (isFav) {
            iv_max_fav.setImageDrawable(getResources().getDrawable(R.mipmap.ic_fav_hover));
        } else {
            iv_max_fav.setImageDrawable(getResources().getDrawable(R.mipmap.ic_fav));
        }
    }

    public void newRotateAnim() {
        if (rotateAnimation != null) {
            rotateAnimation.cancel();
        }
        rotateAnimation = new PauseableRotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(Constant.rotateSpeed);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setInterpolator(new LinearInterpolator());
    }

    public void changeImageAnimation(Boolean isPlay) {
        try {
            if (!isPlay) {
                rotateAnimation.pause();
            } else {
                if (!isRotateAnim) {
                    isRotateAnim = true;
                    if (imageView_pager != null) {
                        imageView_pager.setAnimation(null);
                    }
                    View view_pager = viewpager.findViewWithTag("myview" + Constant.playPos);
                    newRotateAnim();
                    imageView_pager = view_pager.findViewById(R.id.image);
                    imageView_pager.startAnimation(rotateAnimation);
                } else {
                    rotateAnimation.resume();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeTextPager(ItemSong itemSong) {
        StringBuilder artists = new StringBuilder();
        List<ItemArtist> artistList = itemSong.getArtists();
        for (int i = 0; i < artistList.size(); i++) {
            if(i != 0){
                artists.append(", ").append(artistList.get(i).getName());
            }else{
                artists.append(artistList.get(i).getName());
            }
        }
        tv_music_artist.setText(artists);
        tv_music_title.setText(itemSong.getTitle());
        tv_song_count.setText((viewpager.getCurrentItem() + 1) + "/" + Constant.arrayList_play.size());
    }

    public void changeText(final ItemSong itemSong, final String page) {
        StringBuilder artists = new StringBuilder();
        List<ItemArtist> artistList = itemSong.getArtists();
        for (int i = 0; i < artistList.size(); i++) {
            if(i != 0){
                artists.append(", ").append(artistList.get(i).getName());
            }else{
                artists.append(artistList.get(i).getName());
            }
        }
        tv_min_title.setText(itemSong.getTitle());
        tv_min_artist.setText(artists);

        tv_max_title.setText(itemSong.getTitle());

        tv_max_artist.setText(artists);
        tv_music_title.setText(itemSong.getTitle());
        tv_music_artist.setText(artists);

        tv_song_count.setText(Constant.playPos + 1 + "/" + Constant.arrayList_play.size());
        tv_total_time.setText(itemSong.getDuration());

        if(Constant.isLoginOn && Constant.isLogged){
            apiManager.checkFavorite(Constant.itemUser.getId(), itemSong.getId())
                    .enqueue(new retrofit2.Callback<BaseResponse<BasePaginate<ItemSong>>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<BasePaginate<ItemSong>>> call, Response<BaseResponse<BasePaginate<ItemSong>>> response) {
                            changeFav(response.body().isStatus());
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<BasePaginate<ItemSong>>> call, Throwable t) {

                        }
                    });
        }


        if (Constant.isOnline) {

            Picasso.get()
                    .load(itemSong.getImageBig())
                    .placeholder(R.drawable.placeholder_song)
                    .into(iv_min_song);

            Picasso.get()
                    .load(itemSong.getImageBig())
                    .placeholder(R.drawable.placeholder_song)
                    .into(iv_max_song);
        } else {
            Uri uri;
            if (Constant.isDownloaded) {
                iv_music_add2playlist.setVisibility(View.GONE);
                uri = Uri.fromFile(new File(itemSong.getImageBig()));
            } else {
                iv_music_add2playlist.setVisibility(View.VISIBLE);
                uri = methods.getAlbumArtUri(Integer.parseInt(itemSong.getImageBig()));
            }
            Picasso.get()
                    .load(uri)
                    .placeholder(R.drawable.placeholder_song)
                    .into(iv_min_song);

            Picasso.get()
                    .load(uri)
                    .placeholder(R.drawable.placeholder_song)
                    .into(iv_max_song);
        }

        if (viewpager.getAdapter() == null || Constant.isNewAdded || !Constant.addedFrom.equals(adapter.getIsLoadedFrom())) {
            viewpager.setAdapter(adapter);
            Constant.isNewAdded = false;
        }
        try {
            viewpager.setCurrentItem(Constant.playPos);
        } catch (Exception e) {
            adapter.notifyDataSetChanged();
            viewpager.setCurrentItem(Constant.playPos);
        }
    }

    public void changePlayPauseIcon(Boolean isPlay) {
        if (!isPlay) {
            iv_music_play.setImageDrawable(getResources().getDrawable(R.drawable.play));
            iv_min_play.setImageDrawable(getResources().getDrawable(R.mipmap.ic_play_grey));
        } else {
            iv_music_play.setImageDrawable(getResources().getDrawable(R.drawable.pause));
            iv_min_play.setImageDrawable(getResources().getDrawable(R.mipmap.ic_pause_grey));
        }
        seekUpdation();
    }

    public void isBuffering(Boolean isBuffer) {
        if (isBuffer) {
            rl_music_loading.setVisibility(View.VISIBLE);
            iv_music_play.setVisibility(View.INVISIBLE);
        } else {
            rl_music_loading.setVisibility(View.INVISIBLE);
            iv_music_play.setVisibility(View.VISIBLE);
            changePlayPauseIcon(!isBuffer);
//            seekUpdation();
        }
        iv_music_next.setEnabled(!isBuffer);
        iv_music_previous.setEnabled(!isBuffer);
        iv_min_next.setEnabled(!isBuffer);
        iv_min_previous.setEnabled(!isBuffer);
//        iv_music_download.setEnabled(!isBuffer);
        iv_min_play.setEnabled(!isBuffer);
        seekBar_music.setEnabled(!isBuffer);
    }

    private class ImagePagerAdapter extends PagerAdapter {

        private LayoutInflater inflater;
        private String loadedPage = "";

        private ImagePagerAdapter() {
            inflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return Constant.arrayList_play.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view.equals(object);
        }

        String getIsLoadedFrom() {
            return loadedPage;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            View imageLayout = inflater.inflate(R.layout.layout_viewpager, container, false);
            assert imageLayout != null;
            RoundedImageView imageView = imageLayout.findViewById(R.id.image);
            final ImageView imageView_play = imageLayout.findViewById(R.id.iv_vp_play);
            final ProgressBar spinner = imageLayout.findViewById(R.id.loading);

            loadedPage = Constant.addedFrom;

            if (Constant.playPos == position) {
                imageView_play.setVisibility(View.GONE);
            }

            if (Constant.isOnline) {
                Picasso.get()
                        .load(Constant.arrayList_play.get(position).getImageBig())
                        .resize(300, 300)
                        .placeholder(R.drawable.cd)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                spinner.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {
                                spinner.setVisibility(View.GONE);
                            }
                        });
            } else {
                Uri uri;
                if (Constant.isDownloaded) {
                    uri = Uri.fromFile(new File(Constant.arrayList_play.get(position).getImageSmall()));
                } else {
                    uri = methods.getAlbumArtUri(Integer.parseInt(Constant.arrayList_play.get(position).getImageBig()));
                }
                Picasso.get()
                        .load(uri)
                        .placeholder(R.drawable.placeholder_song)
                        .into(imageView);
                spinner.setVisibility(View.GONE);
            }

            imageView_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constant.playPos = viewpager.getCurrentItem();
                    isRotateAnim = false;
                    if (!Constant.isOnline || methods.isNetworkAvailable()) {
                        Intent intent = new Intent(DrawerActivity.this, PlayerService.class);
                        intent.setAction(PlayerService.ACTION_PLAY);
                        startService(intent);
                        imageView_play.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(DrawerActivity.this, getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            if (position == 0) {
                isRotateAnim = false;
                imageView_pager = imageView;
            }

            imageLayout.setTag("myview" + position);
            container.addView(imageLayout, 0);
            return imageLayout;

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSongChange(ItemSong itemSong) {
        changeText(itemSong, "home");
        Constant.context = DrawerActivity.this;
        changeImageAnimation(PlayerService.getInstance().getIsPlayling());
//        GlobalBus.getBus().removeStickyEvent(itemSong);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onBufferChange(MessageEvent messageEvent) {

        if (messageEvent.message.equals("buffer")) {
            isBuffering(messageEvent.flag);
        } else {
            changePlayPauseIcon(messageEvent.flag);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onViewPagerChanged(ItemMyPlayList itemMyPlayList) {
        adapter.notifyDataSetChanged();
        tv_song_count.setText(Constant.playPos + 1 + "/" + Constant.arrayList_play.size());
        GlobalBus.getBus().removeStickyEvent(itemMyPlayList);
    }

    @Override
    public void onUserInteraction() {
            super.onUserInteraction();
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

    public Boolean checkPer() {
        if ((ContextCompat.checkSelfPermission(DrawerActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE"}, 1);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean canUseExternalStorage = false;

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    canUseExternalStorage = true;
                }

                if (!canUseExternalStorage) {
                    Toast.makeText(DrawerActivity.this, getResources().getString(R.string.err_cannot_use_features), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        seekHandler.removeCallbacks(run);
        super.onPause();
    }

    private void setUpAnim() {
        anim_slideup = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        anim_slidedown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        anim_slideup.setInterpolator(new OvershootInterpolator(0.5f));
        anim_slidedown.setInterpolator(new OvershootInterpolator(0.5f));

        anim_slideup_music = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        anim_slidedown_music = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        anim_slideup_music.setInterpolator(new OvershootInterpolator(0.5f));
        anim_slidedown_music.setInterpolator(new OvershootInterpolator(0.5f));

        anim_slidedown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        anim_slideup.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        anim_slidedown_music.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rl_min_header.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        anim_slideup_music.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                rl_min_header.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}