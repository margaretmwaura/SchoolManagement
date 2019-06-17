package com.android.myapplication;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface apiservice
{

    @FormUrlEncoded
    @POST("/android/index.php")
    Call<ResponseBody> insertData(@Field("name") String name , @Field("email") String email , @Field("password") String password);
}
