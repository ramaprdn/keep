package com.example.ramapradana.keep.data.remote.service;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KeepApiClient {
    private static KeepApiService INSTANCE;
    private static final String BASE_URL = "http://127.0.0.1:8000/api/";

    public static KeepApiService getKeepApiService(){
        if (INSTANCE == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .build();

            INSTANCE = retrofit.create(KeepApiService.class);
        }

        return INSTANCE;
    }
}
