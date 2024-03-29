package com.android.myapplication;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface apiservice
{

    @FormUrlEncoded
    @POST("students")
     Call<ResponseBody> store(@Field( "name" ) String name, @Field ( "email" ) String email, @Field ( "password" ) String password);
    @FormUrlEncoded
    @PUT("students/id")
    Call<ResponseBody> update( @Path ("id") int id, @Field( "name" ) String name, @Field ( "email" ) String email, @Field ( "password" ) String password );

    @GET("students")
    Call<List<Student>> readData();
}
