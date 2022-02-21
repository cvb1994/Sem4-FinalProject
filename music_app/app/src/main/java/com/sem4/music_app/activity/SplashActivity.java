package com.sem4.music_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.sem4.music_app.R;

public class SplashActivity extends BaseActivity {

//    SharedPref sharedPref;
//    Methods methods;
//    DBHelper dbHelper;

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }, 3000);
    }




    private void openLoginActivity() {
//        Intent intent;
//        if (Constant.isLoginOn && sharedPref.getIsFirst()) {
//            sharedPref.setIsFirst(false);
//            intent = new Intent(SplashActivity.this, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.putExtra("from", "");
//        } else {
//            intent = new Intent(SplashActivity.this, MainActivity.class);
//        }
//        startActivity(intent);
//        finish();
    }

    private void openMainActivity() {
//        Intent intent;
//        if (Constant.isFromPush && !Constant.pushCID.equals("0")) {
//            intent = new Intent(SplashActivity.this, SongByCatActivity.class);
//            intent.putExtra("isPush", true);
//            intent.putExtra("type", getString(R.string.categories));
//            intent.putExtra("id", Constant.pushCID);
//            intent.putExtra("name", Constant.pushCName);
//        } else if (Constant.isFromPush && !Constant.pushArtID.equals("0")) {
//            intent = new Intent(SplashActivity.this, SongByCatActivity.class);
//            intent.putExtra("isPush", true);
//            intent.putExtra("type", getString(R.string.artist));
//            intent.putExtra("id", Constant.pushArtID);
//            intent.putExtra("name", Constant.pushArtNAME);
//        } else if (Constant.isFromPush && !Constant.pushAlbID.equals("0")) {
//            intent = new Intent(SplashActivity.this, SongByCatActivity.class);
//            intent.putExtra("isPush", true);
//            intent.putExtra("type", getString(R.string.albums));
//            intent.putExtra("id", Constant.pushAlbID);
//            intent.putExtra("name", Constant.pushAlbNAME);
//        } else {
//            intent = new Intent(SplashActivity.this, MainActivity.class);
//        }
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finish();
    }

    private void hideStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}