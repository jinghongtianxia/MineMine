package com.example.test.WebParser;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by king on 2016/12/29.
 */

public interface Postfix {
    @POST("cn/{postfix}")
    Call<ResponseBody> getIndexContent(@Path("postfix") String postfix,@Query("mode") int mode,@Query("page") int page);
}
