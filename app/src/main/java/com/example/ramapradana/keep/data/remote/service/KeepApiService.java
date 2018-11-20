package com.example.ramapradana.keep.data.remote.service;

import com.example.ramapradana.keep.data.remote.model.CreateEventResponse;
import com.example.ramapradana.keep.data.remote.model.EventsResponse;
import com.example.ramapradana.keep.data.remote.model.LoginApiResponse;
import com.example.ramapradana.keep.data.remote.model.PostApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface KeepApiService {
    @GET("register")
    Call<PostApiResponse> postRegistration(@Query("name") String name, @Query("username") String username, @Query("email") String email, @Query("password") String password);

    @GET("login")
    Call<LoginApiResponse> postLogin(@Query("username") String username, @Query("password") String password);

    @FormUrlEncoded
    @POST("event/create")
    Call<CreateEventResponse> postCreateNewEvent(@Field("name") String name, @Field("access_token") String token);

    @GET("event")
    Call<EventsResponse> getEvent(@Query("access_token") String token);

    @FormUrlEncoded
    @POST("event/update")
    Call<PostApiResponse> postUpdateEvent(@Field("id") int id, @Field("name") String updatedName, @Field("access_token") String accessToken);
}
