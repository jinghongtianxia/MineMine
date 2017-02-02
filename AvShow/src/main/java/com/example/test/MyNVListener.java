package com.example.test;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.example.test.Adapter.ContentAdapter;
import com.example.test.Adapter.ContentOnScrollListener;
import com.example.test.Data.SourceData;
import com.example.test.WebParser.IndexWebParser;

/**
 * Created by king on 2017/1/2.
 */

public class MyNVListener implements NavigationView.OnNavigationItemSelectedListener {

    private final IndexWebParser indexWebParser;


    MyNVListener() {
        indexWebParser = new IndexWebParser();

    }

    /**
     * 侧边栏点击事件
     *
     * @param item 侧边栏项目名字
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        String itemName = item.getTitle().toString();
        ContentOnScrollListener.notifyNvItem(itemName);
        switchNvItem(itemName);
        return false;
    }

    /**
     * 侧边栏点击事件判断
     *
     * @param itemName 需要进行判断的项目名称
     */
    private void switchNvItem(String itemName) {
        switch (itemName) {
            case "首页":
                notifyItemType(itemName);
                SourceData sourceData = new SourceData();
                sourceData.updateSourceData();
//                Observable.create(new HomePageParser()).
//                        subscribeOn(Schedulers.io()).
//                        observeOn(AndroidSchedulers.mainThread()).
//                        unsubscribeOn(Schedulers.io()).
//                        subscribe(new MainActivity());
                break;
            case "新话题":
                notifyItemType(itemName);
                transaction(itemName);
                break;
            case "新发行":
                notifyItemType(itemName);
                transaction(itemName);
                break;
            case "新加入":
                notifyItemType(itemName);
                transaction(itemName);
                break;
            case "最想要":
                notifyItemType(itemName);
                transaction(itemName);
                break;
            case "高评价":
                notifyItemType(itemName);
                transaction(itemName);
                break;
        }
    }


    /**
     * 侧边栏点击事件事务处理
     * @param itemName 需要处理的项目名称
     */
    private void transaction(String itemName) {
//        Observable.create(new IndexWebParser(itemName)).
//                subscribeOn(Schedulers.io()).
//                observeOn(AndroidSchedulers.mainThread()).
//                unsubscribeOn(Schedulers.io()).
//                subscribe(new MainActivity());
    }

    /**
     * 侧边栏点击项目名称传递
     * @param type 需要传递的名称
     */
    private void notifyItemType(String type) {
        ContentAdapter.itemType(type);
    }
}
