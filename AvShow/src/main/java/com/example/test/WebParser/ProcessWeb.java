package com.example.test.WebParser;

import android.os.SystemClock;

import com.example.test.Data.SourceData;
import com.example.test.Data.WebUrl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by king on 2017/1/17.
 */

public class ProcessWeb implements ProcessWebDataImp {

    private SourceData datas;

    @Override
    public void process(String url, int i) {
        datas = SourceData.getSourceData();
        try {
            Postfix postfix = new Retrofit.Builder().baseUrl(WebUrl.getHomePage()).build().create(Postfix.class);
            InputStream inputStream = postfix.getIndexContent(url, 0, i).execute().body().byteStream();
            Element body = Jsoup.parse(inputStream, "utf-8", "").body();
            Elements select = body.select("div[class=video]");
            for (Element ele :
                    select) {
                datas.addBitmap(ele.select("img[src$=.jpg]").attr("src"));
                datas.addTitle(ele.select("div[class=title]").text());
                datas.addUrl(ele.select("a").attr("href").substring(5));
                datas.addId(ele.select("div[class=id]").text());
            }
            SystemClock.sleep(1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
