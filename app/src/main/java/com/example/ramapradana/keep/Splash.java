package com.example.ramapradana.keep;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static java.lang.Thread.sleep;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Intent intent = new Intent(this, LoginActivity.class);
        final Intent mainIntent = new Intent(this, MainActivity.class);
        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

                finally {
                    SharedPreferences sharedPreferences = getSharedPreferences("credential", Context.MODE_PRIVATE);
                    if (!sharedPreferences.contains("access_token")){
                        startActivity(intent);
                        finish();
                    }else{
                        startActivity(mainIntent);
                        finish();
                    }
                }
            }
        };

        thread.start();


    }

}
