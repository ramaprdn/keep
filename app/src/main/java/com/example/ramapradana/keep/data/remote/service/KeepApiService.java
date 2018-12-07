package com.example.ramapradana.keep.data.remote.service;

import com.example.ramapradana.keep.data.remote.model.CreateEventResponse;
import com.example.ramapradana.keep.data.remote.model.CreateNoteResponse;
import com.example.ramapradana.keep.data.remote.model.EventFileResponse;
import com.example.ramapradana.keep.data.remote.model.EventsResponse;
import com.example.ramapradana.keep.data.remote.model.FriendsResponse;
import com.example.ramapradana.keep.data.remote.model.LoginApiResponse;
import com.example.ramapradana.keep.data.remote.model.PostApiResponse;
import com.example.ramapradana.keep.data.remote.model.SearchUserResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KeepApiService {
    @GET("register")
    Call<PostApiResponse> postRegistration(@Query("name") String name, @Query("username") String username, @Query("email") String email, @Query("password") String password);

    @GET("login")
    Call<LoginApiResponse> postLogin(
            @Query("username") String username,
            @Query("password") String password,
            @Query("fcm") String fcm);

    @FormUrlEncoded
    @POST("event/create")
    Call<CreateEventResponse> postCreateNewEvent(@Field("name") String name, @Field("access_token") String token);

    @GET("event")
    Call<EventsResponse> getEvent(@Query("access_token") String token);

    @FormUrlEncoded
    @POST("event/update")
    Call<PostApiResponse> postUpdateEvent(@Field("id") int id, @Field("name") String updatedName, @Field("access_token") String accessToken);

    @FormUrlEncoded
    @POST("event/new")
    Call<CreateNoteResponse> postCreateNote(
            @Field("access_token") String token,
            @Field("title") String title,
            @Field("event_id") int eventId,
            @Field("content") String content
            );

    @GET("event/{id}")
    Call<EventFileResponse> getEventFile(@Path("id") int eventId, @Query("access_token") String accessToken);

    @FormUrlEncoded
    @POST("event/note/{id}")
    Call<PostApiResponse> postUpdateNote(
            @Path("id") int eventFileId,
            @Field("title") String title,
            @Field("content") String content,
            @Field("access_token") String token);

    @FormUrlEncoded
    @POST("event/file/{id}/delete")
    Call<PostApiResponse> deleteFileOrNote(@Path("id") int fileId, @Field("access_token") String token);

    @FormUrlEncoded
    @POST("event/delete")
    Call<PostApiResponse> deleteEvent(@Field("event_id") int eventId, @Field("access_token") String token);

    @Multipart
    @POST("event/new")
    Call<PostApiResponse> uploadEventFile(
            @Query("access_token") String token,
            @Query("event_id") int eventId,
            @Part MultipartBody.Part file
            );

    @GET("user/search/{username}")
    Call<SearchUserResponse> searchUser(@Path("username") String username, @Query("access_token") String token);

    @FormUrlEncoded
    @POST("user/add/{friendId}")
    Call<PostApiResponse> addFriend(@Path("friendId") int friendId, @Field("access_token") String token);

    @GET("friend")
    Call<FriendsResponse> getFriend(@Query("access_token") String token);

    @FormUrlEncoded
    @POST("event/invite/{eventId}")
    Call<PostApiResponse> inviteFriend(@Path("eventId") int eventId, @Field("users[]") List<Integer> users, @Field("access_token") String token);

}
