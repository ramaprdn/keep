package com.example.ramapradana.keep;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramapradana.keep.data.remote.model.PostApiResponse;
import com.example.ramapradana.keep.data.remote.service.KeepApiClient;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private Call<PostApiResponse> call;
    TextInputEditText tvName;
    TextInputEditText tvUsername;
    TextInputEditText tvEmail;
    TextInputEditText tvPassword;

    public static void start(Context context){
        Intent intent = new Intent(context, SignUpActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView tvLogin;
        MaterialButton btnSignUp;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tvName = this.findViewById(R.id.tv_fullname);
        tvUsername = this.findViewById(R.id.tv_username);
        tvEmail = this.findViewById(R.id.tv_email);
        tvPassword = this.findViewById(R.id.tv_password);

        tvLogin = this.findViewById(R.id.btn_intent_login);
        tvLogin.setOnClickListener(v -> finish());

        btnSignUp = this.findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener((v) -> {


            String name = tvName.getText().toString();
            String username = tvUsername.getText().toString();
            String email = tvEmail.getText().toString();
            String password = tvPassword.getText().toString();

            boolean anyError = false;

            if (name.equals("")){
                tvName.setError("Full name is required!");
                anyError = true;
            }

            if (username.equals("")){
                tvUsername.setError("Username is required!");
                anyError = true;
            }

            if (email.equals("")){
                tvEmail.setError("Email is required!");
                anyError = true;
            }

            if (password.equals("")){
                tvPassword.setError("Password is required!");
                anyError = true;
            }

            if (anyError){
                return;
            }

            ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
            progressDialog.setMessage("Signing Up..");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            call = KeepApiClient.getKeepApiService()
                    .postRegistration(name, username, email, password);
            call.enqueue(new Callback<PostApiResponse>() {
                @Override
                public void onResponse(Call<PostApiResponse> call, Response<PostApiResponse> response) {
                    progressDialog.hide();
                    Toast.makeText(SignUpActivity.this, response.body().getMsg(), Toast.LENGTH_LONG).show();
                    if (response.body().isStatus() == true){
                        LoginActivity.start(SignUpActivity.this);
                    }
                }

                @Override
                public void onFailure(Call<PostApiResponse> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_LONG).show();
                    progressDialog.hide();
                }
            });
        });

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Product Sans Regular.ttf", true);

    }
}
