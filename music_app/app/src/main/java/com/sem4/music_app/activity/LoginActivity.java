package com.sem4.music_app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sem4.music_app.R;
import com.sem4.music_app.item.ItemUser;
import com.sem4.music_app.network.ApiManager;
import com.sem4.music_app.network.Common;
import com.sem4.music_app.utils.Constant;
import com.sem4.music_app.utils.Methods;
import com.sem4.music_app.utils.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    SharedPref sharedPref;
    Methods methods;
    EditText etUsername, etPassword;
    Button btLogin, btSkip;
    CheckBox cbRememberMe;
    TextView tvRegister, tvForgotPassword;
    ProgressDialog progressDialog;
    ApiManager apiManager;

    @Override
    protected void initView() {
        apiManager = Common.getAPI();

        sharedPref = new SharedPref(this);
        methods = new Methods(this);
        methods.forceRTLIfSupported(getWindow());
        methods.setStatusColor(getWindow());

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        btSkip = findViewById(R.id.btSkip);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        btLogin.setBackground(methods.getRoundDrawable(Color.WHITE));
        btSkip.setBackground(methods.getRoundDrawable(Color.WHITE));
        btSkip.setTextColor(getResources().getColor(R.color.colorPrimary));

        tvForgotPassword.setTypeface(tvForgotPassword.getTypeface(), Typeface.BOLD);
        tvRegister.setTypeface(tvRegister.getTypeface(), Typeface.BOLD);
        btLogin.setTypeface(btLogin.getTypeface(), Typeface.BOLD);
        btSkip.setTypeface(btSkip.getTypeface(), Typeface.BOLD);

        if (sharedPref.getIsRemember()) {
            etUsername.setText(sharedPref.getUsername());
            etPassword.setText(sharedPref.getPassword());
            cbRememberMe.setChecked(true);
        }

        btSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void attemptLogin() {
        etUsername.setError(null);
        etPassword.setError(null);

        // Store values at the time of the login attempt.
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            etPassword.setError(getString(R.string.error_password_sort));
            focusView = etPassword;
            cancel = true;
        }
        if (etPassword.getText().toString().endsWith(" ")) {
            etPassword.setError(getString(R.string.pass_end_space));
            focusView = etPassword;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            etUsername.setError(getString(R.string.cannot_empty));
            focusView = etUsername;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            etUsername.setError(getString(R.string.error_invalid_username));
            focusView = etUsername;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            loadLogin();
        }
    }

    private void loadLogin() {
        if (methods.isNetworkAvailable()) {
            progressDialog.show();
            apiManager.userLogin(etUsername.getText().toString(), etPassword.getText().toString())
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            progressDialog.dismiss();
                            if (TextUtils.isEmpty(response.body())) {
                                Toast.makeText(LoginActivity.this, getString(R.string.wrong_username_or_password), Toast.LENGTH_SHORT).show();
                            } else {
                                Constant.itemUser = new ItemUser(etUsername.getText().toString());
                                if (cbRememberMe.isChecked()) {
                                    sharedPref.setLoginDetails(Constant.itemUser, cbRememberMe.isChecked(), etPassword.getText().toString());
                                } else {
                                    sharedPref.setRemeber(false);
                                }
                                sharedPref.setIsAutoLogin(true);
                                Constant.isLogged = true;
//                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, getString(R.string.err_server), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(LoginActivity.this, getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isUsernameValid(String username) {
        return username.length() > 0 && !username.contains(" ");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 0;
    }

    private void openMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}