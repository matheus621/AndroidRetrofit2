package com.example.androidretrofit2.resource;


import com.example.androidretrofit2.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;



public interface UsersResource {

    @GET("users")
    Call<List<Users>> get();

//    @POST("users")
//    Call<Users> post(@Body Users user);
//
//    @PUT("users")
//    Call<Users> put(@Body Users user);
//
//    @DELETE("users")
//    Call<Void> delete(@Body Users user);
}
