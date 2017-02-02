package com.example.test.WebParser;

import com.example.test.Data.SourceData;
import com.example.test.Data.WebUrl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Retrofit;

/**
 * Created by king on 2017/1/17.
 */

public class DetailParser implements ObservableOnSubscribe<String> {

    private String href;

    public void addHref(String value) {
        href = value;
    }

    @Override
    public void subscribe(ObservableEmitter<String> e) throws Exception {
        SourceData sourceData = new SourceData();
        sourceData.upDateMessage();
        DetailImp detailImp = new Retrofit.Builder().baseUrl(WebUrl.getHomePage()).build().create(DetailImp.class);
        InputStream inputStream = detailImp.getDetails(href).execute().body().byteStream();
        Element body = Jsoup.parse(inputStream, "utf-8", "").body();
        String attr = body.getElementById("video_jacket_img").select("[src$=.jpg]").attr("src");
        Element video_info = body.getElementById("video_info");
        Elements header = video_info.getElementsByTag("tr").select("td[class=header]");
        Elements category = video_info.getElementsByTag("tr").select("td[class=text]").select("a[rel=category tag]");
        Elements star = video_info.getElementById("video_cast").tagName("td").select("span[class=star]");
        for (Element ele :
                header) {
            sourceData.addLeftMessage(ele.text());
            switch (ele.text()) {
                case "识别码:":
                    sourceData.addRightMessage(video_info.getElementById("video_id").select("td[class=text]").text());
                    break;
                case "发行日期:":
                    sourceData.addRightMessage(video_info.getElementById("video_date").select("td[class=text]").text());
                    break;
                case "长度:":
                    sourceData.addRightMessage(video_info.getElementById("video_length").select("span[class=text]").text());
                    break;
                case "导演:":
                    sourceData.addRightMessage(video_info.getElementById("video_director").select("td[class=text]").text());
                    break;
                case "制作商:":
                    sourceData.addRightMessage(video_info.getElementById("video_maker").tagName("span").select("a[rel=tag]").text());
                    break;
                case "发行商:":
                    sourceData.addRightMessage(video_info.getElementById("video_label").tagName("span").select("a[rel=tag]").text());
                    break;
                case "使用者评价:":
                    sourceData.addRightMessage(video_info.getElementById("video_review").tagName("td").select("span[class=score]").text());
                    break;
                case "类别:":
                    int d = 0;
                    StringBuilder cate = new StringBuilder();
                    for (Element ca :
                            category) {
                        String caText = ca.text();
                        if (d == category.size() - 1) {
                            cate.append("[" + caText + "]");
                        } else {
                            cate.append("[" + caText + "]" + "\n");
                        }
                        d++;
                    }
                    sourceData.addRightMessage(cate.toString());
                    break;
                case "演员:":
                    int i = 0;
                    StringBuilder actor = new StringBuilder();
                    for (Element ac :
                            star) {
                        String text = ac.text();
                        while (i < star.size() - 1) {
                            actor.append("[" + text + "]" + "\n");
                        }
//                        if (i == star.size() - 1) {
//                            actor.append("[" + text + "]");
//                        } else {
//                            actor.append("[" + text + "]" + "\n");
//                        }
                        i++;
                        actor.append("[" + text + "]");
                    }
                    sourceData.addRightMessage(actor.toString());
                    break;
            }
        }
        e.onNext(attr);
    }
}
