package com.example.ramapradana.keep.data.remote.service;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KeepApiClient {
    private static KeepApiService INSTANCE;
    private static final String BASE_URL = "http://172.17.100.2:8000/api/";

    public static KeepApiService getKeepApiService(){
        if (INSTANCE == null){
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(logging);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .client(okHttpClientBuilder.build())
                    .build();

            INSTANCE = retrofit.create(KeepApiService.class);
        }

        return INSTANCE;
    }
}
