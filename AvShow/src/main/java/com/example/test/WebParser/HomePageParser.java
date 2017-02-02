package com.example.test.WebParser;

import com.example.test.Data.SourceData;
import com.example.test.Data.WebUrl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by king on 2016/12/29.
 */

public class HomePageParser implements ObservableOnSubscribe<SourceData> {

    @Override
    public void subscribe(ObservableEmitter<SourceData> e) throws Exception {
        final SourceData homePage = new SourceData();
        HomeIndex homeIndex = new Retrofit.Builder().baseUrl(WebUrl.getHomePage()).build().create(HomeIndex.class);
        try {
            InputStream inputStream = homeIndex.getIndexContent("").execute().body().byteStream();
            Element body = Jsoup.parse(inputStream, "utf-8", "").body();
            Elements homePageContent = body.getElementsByClass("video");
            Elements select = body.getElementsByTag("ul").tagName("li").select("a");
            for (Element ele :
                    homePageContent) {
                homePage.addBitmap(ele.select("img[src$=.jpg]").attr("src"));
                homePage.addTitle(ele.select("[class=title post_title]").text());
                homePage.addUrl(ele.select("a").attr("href").substring(5));
                homePage.addId(ele.select("[class=id]").text());
            }
            for (Element ele :
                    select) {
                homePage.addDrawerItem(ele.text());
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
        e.onComplete();
    }
}
