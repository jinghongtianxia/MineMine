package com.example.test.WebParser;

import android.os.SystemClock;
import android.widget.Toast;

import com.example.test.Data.SourceData;
import com.example.test.Data.WebUrl;
import com.example.test.MainActivity;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by king on 2016/12/29.
 */

/**
 * 侧边栏网页事件解析
 */
public class IndexWebParser implements ObservableOnSubscribe<SourceData>{

    private final String tip;
    private SourceData datas;



    public IndexWebParser(String string) {
        tip = string;
    }

    public IndexWebParser() {
        tip = WebUrl.getHomePage();
    }

    @Override
    public void subscribe(ObservableEmitter<SourceData> e) throws Exception {
        switchItem(tip);
    }

    private void switchItem(String tip){
        switch (tip) {
            case "新话题":
                NvItemGetNewTopic();
                break;
            case "新发行":
                NvItemGetNewRelease();
                break;
            case "新加入":
                NvItemGetNewMovies();
                break;
            case "最想要":
                NvItemGetMostWanted();
                break;
            case "高评价":
                NvItemGetMostPraise();
                break;
        }
    }

    private void NvItemGetNewTopic() {
        notifyUpdate(WebUrl.getNewTopic());
    }

    private void NvItemGetNewRelease() {
        notifyUpdate(WebUrl.getNewRelease());
    }

    private void NvItemGetNewMovies() {
        notifyUpdate(WebUrl.getNewMovies());
    }

    private void NvItemGetMostWanted() {
        notifyUpdate(WebUrl.getMostWanted());
    }

    private void NvItemGetMostPraise() {
        notifyUpdate(WebUrl.getMostPraise());
    }

    private void notifyUpdate(String url) {
//        datas = SourceData.getSourceData();
//        datas.updateSourceData();
        SourceData sourceData = new SourceData();
        sourceData.updateSourceData();
        ProcessWebDataImp processWebDataImp = new ProcessWeb();
        processWebDataImp.process(url,0);
    }

//    @Override
//    public void process(String url, int i) {
//        try {
//            Postfix postfix = new Retrofit.Builder().baseUrl(MAIN_PAGE).build().create(Postfix.class);
//            InputStream inputStream = postfix.getIndexContent(url, 0, i).execute().body().byteStream();
//            Element body = Jsoup.parse(inputStream, "utf-8", "").body();
//            Elements select = body.select("div[class=video]");
//            for (Element ele :
//                    select) {
//                datas.addBitmap(ele.select("img[src$=.jpg]").attr("src"));
//                datas.addTitle(ele.select("div[class=title]").text());
//                datas.addUrl(ele.select("a").attr("href").substring(5));
//                datas.addId(ele.select("div[class=id]").text());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
