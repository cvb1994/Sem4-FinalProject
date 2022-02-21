package com.sem4.music_app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class RegisterActivity extends BaseActivity {

    Methods methods;
    EditText etFirstName, etLastName, etUsername, etEmail,
            etPassword, etConfirmPassword, etPhone;
    TextView tvLoginNow;
    Button btRegister;
    ProgressDialog progressDialog;
    SharedPref sharedPref;
    ApiManager apiManager;

    @Override
    protected void initView() {
        sharedPref = new SharedPref(this);
        methods = new Methods(this);
        methods.setStatusColor(getWindow());
        methods.forceRTLIfSupported(getWindow());
        apiManager = Common.getAPI();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.registering));
        progressDialog.setCancelable(false);

        tvLoginNow = findViewById(R.id.tvLoginNow);
        btRegister = findViewById(R.id.btRegister);
        etEmail = findViewById(R.id.etEmail);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etPhone = findViewById(R.id.etPhone);

        btRegister.setBackground(methods.getRoundDrawable(Color.WHITE));

        tvLoginNow.setTypeface(tvLoginNow.getTypeface(), Typeface.BOLD);
        btRegister.setTypeface(btRegister.getTypeface(), Typeface.BOLD);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    loadRegister();
                }
            }
        });

        tvLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Boolean validate() {
        if (etLastName.getText().toString().trim().isEmpty()) {
            etLastName.setError(getResources().getString(R.string.enter_name));
            etLastName.requestFocus();
            return false;
        } else if (etUsername.getText().toString().trim().isEmpty()) {
            etUsername.setError(getResources().getString(R.string.error_invalid_username));
            etUsername.requestFocus();
            return false;
        } else if (etEmail.getText().toString().trim().isEmpty()) {
            etEmail.setError(getResources().getString(R.string.enter_email));
            etEmail.requestFocus();
            return false;
        } else if (!isEmailValid(etEmail.getText().toString())) {
            etEmail.setError(getString(R.string.error_invalid_email));
            etEmail.requestFocus();
            return false;
        } else if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError(getResources().getString(R.string.enter_password));
            etPassword.requestFocus();
            return false;
        } else if (etPassword.getText().toString().endsWith(" ")) {
            etPassword.setError(getResources().getString(R.string.pass_end_space));
            etPassword.requestFocus();
            return false;
        } else if (etConfirmPassword.getText().toString().isEmpty()) {
            etConfirmPassword.setError(getResources().getString(R.string.enter_cpassword));
            etConfirmPassword.requestFocus();
            return false;
        } else if (!etConfirmPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            etConfirmPassword.setError(getResources().getString(R.string.pass_nomatch));
            etConfirmPassword.requestFocus();
            return false;
        } else if (etPhone.getText().toString().trim().isEmpty()) {
            etPhone.setError(getResources().getString(R.string.enter_phone));
            etPhone.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && !email.contains(" ");
    }

    private void loadRegister() {
        if (methods.isNetworkAvailable()) {
            progressDialog.show();
            apiManager.register(etFirstName.getText().toString(),
                    etLastName.getText().toString(),
                    etUsername.getText().toString(),
                    etPassword.getText().toString(),
                    etEmail.getText().toString(),
                    etPhone.getText().toString())
                    .enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            progressDialog.dismiss();
                            if (response.body().isSuccess()) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, getString(R.string.err_server), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
        }
    }
}