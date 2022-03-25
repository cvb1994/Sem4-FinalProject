package com.sem4.music_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sem4.music_app.R;
import com.sem4.music_app.item.ItemUser;
import com.sem4.music_app.network.ApiManager;
import com.sem4.music_app.network.Common;
import com.sem4.music_app.response.BaseResponse;
import com.sem4.music_app.utils.Constant;
import com.sem4.music_app.utils.Methods;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileActivity extends AppCompatActivity {

    Methods methods;
    Toolbar toolbar;
    TextView textView_name, textView_vip, textView_email, textView_mobile, textView_notlog;
    LinearLayout ll_mobile;
    View view_phone;
    ProgressDialog progressDialog;
    ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        apiManager = Common.getAPI();
        methods = new Methods(this);
        methods.forceRTLIfSupported(getWindow());
        methods.setStatusColor(getWindow());

        toolbar = findViewById(R.id.toolbar_pro);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);

//        textView_name = findViewById(R.id.tv_prof_fname);
        textView_email = findViewById(R.id.tv_prof_email);
        textView_vip = findViewById(R.id.tv_vip);
        textView_mobile = findViewById(R.id.tv_prof_mobile);
        textView_notlog = findViewById(R.id.textView_notlog);

        ll_mobile = findViewById(R.id.ll_prof_phone);

        view_phone = findViewById(R.id.view_prof_phone);


        if (Constant.itemUser != null && !Constant.itemUser.getId().equals("")) {
            loadUserProfile();
        } else {
            setEmpty(true, getString(R.string.not_log));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadUserProfile() {
        setVariables(Constant.itemUser);
    }

    public void setVariables(ItemUser user) {
        textView_mobile.setText(user.getPhone());
        textView_email.setText(user.getEmail());
        textView_vip.setText(user.isVip() ? "VIP(".concat(user.getVipExpireDate()).concat(")") : "Không");

        if (!user.getPhone().trim().isEmpty()) {
            ll_mobile.setVisibility(View.VISIBLE);
            view_phone.setVisibility(View.VISIBLE);
        }

        textView_notlog.setVisibility(View.GONE);
    }

    public void setEmpty(Boolean flag, String message) {
        if (flag) {
            textView_notlog.setText(message);
            textView_notlog.setVisibility(View.VISIBLE);
        } else {
            textView_notlog.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        if (Constant.isUpdate) {
            Constant.isUpdate = false;
            setVariables(Constant.itemUser);
        }
        super.onResume();
    }
}