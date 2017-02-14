package com.example.test;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.test.Adapter.ContentAdapter;
import com.example.test.Adapter.ContentItemDecoration;
import com.example.test.Adapter.ContentOnScrollListener;
import com.example.test.Data.SourceData;
import com.example.test.Data.WebUrl;
import com.example.test.WebParser.HomeIndex;
import com.example.test.WebParser.Postfix;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * width=1088
 */
public class MainActivity extends AppCompatActivity implements Observer<String> {


    private ContentAdapter contentAdapter;
    private RecyclerView contentRecyclerView;
    private Toolbar mainToolBar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAdapter();
//        LeakCanary.install(getApplication()).watch(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        System.out.println("onDestory");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String key = "test";
        Bundle bundle = new Bundle(ClassLoader.getSystemClassLoader());
        outState.putBundle(key, bundle);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void initView() {
        contentRecyclerView = (RecyclerView) findViewById(R.id.content_recyclerview);
        contentRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, OrientationHelper.VERTICAL, false));
        contentRecyclerView.addItemDecoration(new ContentItemDecoration());
        contentRecyclerView.addOnScrollListener(new ContentOnScrollListener());
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout_main);
        navigationItemSelected();
        mainToolBar = (Toolbar) findViewById(R.id.main_toolbar);
        setToolbarTitle("首页");
    }

    public void initAdapter() {
        contentAdapter = new ContentAdapter();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                initHomePage();
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                contentRecyclerView.setAdapter(contentAdapter);
            }
        });
    }

    public void initHomePage() {
        ContentAdapter.itemType("首页");
        homePageProcess();
    }


    /**
     * 抽屉点击事件
     */
    public void navigationItemSelected() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String itemName = item.getTitle().toString();
                drawerLayout.closeDrawers();
                ContentOnScrollListener.notifyNvItem(itemName);
                switchItem(itemName);
                return false;
            }
        });
    }



//    {"新话题","新发行","新加入","最想要","高评价","排行榜","名鑑"}

    /**
     * 抽屉点击事件判断
     *
     * @param itemName 点击事件名称
     */
    public void switchItem(final String itemName) {
        switch (itemName) {
            case "首页":
                ContentAdapter.itemType(itemName);
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        homePageProcess();
                        e.onNext(itemName);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(MainActivity.this);
                break;
            case "新话题":
                ContentAdapter.itemType(itemName);
                indexPageProcess(itemName, WebUrl.getNewTopic());
                break;
            case "新发行":
                ContentAdapter.itemType(itemName);
                indexPageProcess(itemName, WebUrl.getNewRelease());
                break;
            case "新加入":
                ContentAdapter.itemType(itemName);
                indexPageProcess(itemName, WebUrl.getNewMovies());
                break;
            case "最想要":
                ContentAdapter.itemType(itemName);
                indexPageProcess(itemName, WebUrl.getMostWanted());
                break;
            case "高评价":
                ContentAdapter.itemType(itemName);
                indexPageProcess(itemName, WebUrl.getMostPraise());
                break;
            case "排行榜":
                ContentAdapter.itemType(itemName);
                indexPageProcess(itemName, WebUrl.getActorRank());
                break;
            case "名鑑":
                ContentAdapter.itemType(itemName);
                indexPageProcess(itemName, WebUrl.getActorList());
                break;
        }
    }


    /**
     * 首页处理
     */
    public void homePageProcess() {
        final SourceData homePage = new SourceData();
        homePage.updateSourceData();
        final HomeIndex homeIndex = new Retrofit.Builder().baseUrl(WebUrl.getHomePage()).build().create(HomeIndex.class);
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
    }


    /**
     * 其他页面处理
     *
     * @param itemName 其他页面的名称，用于switch语句判断。
     */
    public void indexPageProcess(final String itemName, final String url) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                SourceData sourceData = new SourceData();
                sourceData.updateSourceData();
                try {
                    Postfix postfix = new Retrofit.Builder().baseUrl(WebUrl.getHomePage()).build().create(Postfix.class);
                    InputStream inputStream = postfix.getIndexContent(url, 0, 1).execute().body().byteStream();
                    Element body = Jsoup.parse(inputStream, "utf-8", "").body();
                    Elements select = body.select("div[class=video]");
                    for (Element ele :
                            select) {
                        sourceData.addBitmap(ele.select("img[src$=.jpg]").attr("src"));
                        sourceData.addTitle(ele.select("div[class=title]").text());
                        sourceData.addUrl(ele.select("a").attr("href").substring(5));
                        sourceData.addId(ele.select("div[class=id]").text());
                    }
                } catch (IOException error) {
                    error.printStackTrace();
                }
                e.onNext(itemName);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(MainActivity.this);

//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                SourceData sourceData = new SourceData();
//                sourceData.updateSourceData();
//                Postfix postfix = new Retrofit.Builder().baseUrl(WebUrl.getHomePage()).build().create(Postfix.class);
//                InputStream inputStream = postfix.getIndexContent(url, 0, 1).execute().body().byteStream();
//                Element body = Jsoup.parse(inputStream, "utf-8", "").body();
//
//                //排名序号
//                Elements h3 = body.getElementsByTag("h3");
//                for (Element ele :
//                        h3) {
//                    sourceData.addId(ele.text());
//                }
//
//                //详情页面
//                Elements searchitem = body.getElementsByClass("searchitem");
//                for (Element ele :
//                        searchitem) {
//                    sourceData.addUrl(ele.getElementsByTag("a").attr("href"));
//                }
//
//                //名字及头像
//                Elements select = body.select("img[src$=.jpg]");
//                for (Element ele :
//                        select) {
//                    sourceData.addTitle(ele.attr("title"));
//                    sourceData.addBitmap(ele.attr("src"));
//                }
//                e.onNext(itemName);
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(MainActivity.this);
    }

    /**
     * 设置标题
     *
     * @param title 要设置的标题名称
     */
    public void setToolbarTitle(String title) {
        Paint paint = new Paint();
        float titleLength = paint.measureText(title);
        mainToolBar.setTitle(title);
        mainToolBar.setTitleMargin(((int) ((1088 / 2) - (titleLength / 2) - 30)), 70, 50, 1152);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(String s) {
        contentAdapter.notifyDataSetChanged();
        setToolbarTitle(s);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

}
