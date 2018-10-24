package com.example.ramapradana.keep;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import me.anwarshahriar.calligrapher.Calligrapher;

public class SignUpActivity extends AppCompatActivity {

    public static void start(Context context){
        Intent intent = new Intent(context, SignUpActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView tvLogin;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();
        tvLogin = this.findViewById(R.id.btn_intent_login);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Product Sans Regular.ttf", true);

    }
}
