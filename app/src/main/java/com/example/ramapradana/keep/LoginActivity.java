package com.example.ramapradana.keep;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramapradana.keep.data.remote.model.LoginApiResponse;
import com.example.ramapradana.keep.data.remote.service.KeepApiClient;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView tvSignUp;
    MaterialButton btnLogin;
    TextInputEditText tvUsername;
    TextInputEditText tvPassword;
    private Call<LoginApiResponse> call;


    public static void start(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("credential", Context.MODE_PRIVATE);

        Animation animation = AnimationUtils.makeInAnimation(this, true);
        animation.start();
        if (sharedPreferences.contains("access_token")){
            Toast.makeText(LoginActivity.this, "Hello, You are still login!", Toast.LENGTH_LONG).show();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvUsername = this.findViewById(R.id.tv_username);
        tvPassword = this.findViewById(R.id.tv_password);

        btnLogin = this.findViewById(R.id.btn_login);
        Intent intent = new Intent(this, MainActivity.class);
        btnLogin.setOnClickListener((v) -> {
            if (!sharedPreferences.contains("access_token")){
                String username = tvUsername.getText().toString();
                String password = tvPassword.getText().toString();

                boolean anyError = false;
                if (username.equals("")){
                    tvUsername.setError("Username is required.");
                    anyError = true;
                }

                if (password.equals("")){
                    tvPassword.setError("Password is required");
                    anyError = true;
                }

                if (anyError){
                    return;
                }

                ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Logging in.. Please wait!");
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);


                call = KeepApiClient.getKeepApiService()
                        .postLogin(username, password);
                call.enqueue(new Callback<LoginApiResponse>() {
                    @Override
                    public void onResponse(Call<LoginApiResponse> call, Response<LoginApiResponse> response) {
                        progressDialog.hide();
                        if (!response.body().isStatus()){
                            tvUsername.setError(response.body().getMsg());
                        }else{
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name", response.body().getUser_name());
                            editor.putString("username", response.body().getUser_name());
                            editor.putString("email", response.body().getUser_email());
                            editor.putString("access_token", response.body().getUser_access_token());
                            editor.apply();

                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginApiResponse> call, Throwable t) {
                        progressDialog.hide();
                        Toast.makeText(LoginActivity.this, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                startActivity(intent);
                finish();
            }

        });

        tvSignUp = this.findViewById(R.id.btn_intent_signup);
        tvSignUp.setOnClickListener(v -> SignUpActivity.start(LoginActivity.this));

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Product Sans Regular.ttf", true);
    }

}
