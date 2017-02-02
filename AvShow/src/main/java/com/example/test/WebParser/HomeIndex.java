package com.example.test.WebParser;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by king on 2017/1/17.
 */

public interface HomeIndex {
    @POST("cn/{postfix}")
    Call<ResponseBody> getIndexContent(@Path("postfix") String postfix);
}
