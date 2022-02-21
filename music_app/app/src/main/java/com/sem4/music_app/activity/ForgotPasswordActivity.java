package com.sem4.music_app.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sem4.music_app.R;
import com.sem4.music_app.network.ApiManager;
import com.sem4.music_app.network.Common;
import com.sem4.music_app.response.ApiResponse;
import com.sem4.music_app.utils.Methods;
import com.sem4.music_app.utils.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends BaseActivity {

    Methods methods;
    Button btSend;
    EditText etEmail;
    SharedPref sharedPref;
    ProgressDialog progressDialog;
    ApiManager apiManager;

    @Override
    protected void initView() {
        methods = new Methods(this);
        methods.forceRTLIfSupported(getWindow());
        methods.setStatusColor(getWindow());

        apiManager = Common.getAPI();

        progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
        progressDialog.setMessage(getString(R.string.loading));

        btSend = findViewById(R.id.btSend);
        etEmail = findViewById(R.id.etEmail);

        sharedPref = new SharedPref(this);
        btSend.setBackground(methods.getRoundDrawable(Color.WHITE));
        btSend.setTypeface(btSend.getTypeface(), Typeface.BOLD);

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (methods.isNetworkAvailable()) {
                    if (!etEmail.getText().toString().trim().isEmpty()) {
                        loadForgotPass();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void loadForgotPass() {
        progressDialog.show();
        apiManager.forgotPassword(etEmail.getText().toString())
                .enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        progressDialog.dismiss();
                        if (response.body().isSuccess()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Hãy kiếm tra email để nhận link reset mật khẩu", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(ForgotPasswordActivity.this, getString(R.string.err_server), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}