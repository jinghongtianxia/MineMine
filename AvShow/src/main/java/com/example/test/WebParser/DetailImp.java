package com.example.test.WebParser;

/**
 * Created by king on 2017/1/17.
 */

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DetailImp {
    @POST("cn")
    Call<ResponseBody> getDetails(@Query("v") String details);
}
