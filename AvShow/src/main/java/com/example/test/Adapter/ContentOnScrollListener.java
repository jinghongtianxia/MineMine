package com.example.test.Adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.test.Data.SourceData;
import com.example.test.WebParser.ProcessWeb;
import com.example.test.WebParser.ProcessWebDataImp;
import com.example.test.Data.WebUrl;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by king on 2017/1/17.
 */

public class ContentOnScrollListener extends RecyclerView.OnScrollListener {

    private static String item;
    private final ProcessWebDataImp processWebDataImp;
    private boolean haveLoad;
    private int i = 1;

    public ContentOnScrollListener() {
        super();
        processWebDataImp = new ProcessWeb();
    }

    public static void notifyNvItem(String value) {
        item = value;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
        LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        if (lastVisibleItemPosition == SourceData.getSourceData().getTitleSource().size() && !haveLoad) {
            System.out.println("enter");
            haveLoad = true;
            i++;
            Observable.create(new ObservableOnSubscribe<SourceData>() {
                @Override
                public void subscribe(ObservableEmitter<SourceData> e) throws Exception {
                    chooseScrollItem(i);
                    SourceData sourceData = SourceData.getSourceData();
                    e.onNext(sourceData);
                }
            }).subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new Observer<SourceData>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(SourceData value) {
                            recyclerView.getAdapter().notifyDataSetChanged();
                            haveLoad = false;
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void chooseScrollItem(int page) {
        switch (item) {
            case "新话题":
                processWebDataImp.process(WebUrl.getNewTopic(), page);
                break;
            case "新发行":
                processWebDataImp.process(WebUrl.getNewRelease(), page);
                break;
            case "新加入":
                processWebDataImp.process(WebUrl.getNewMovies(), page);
                break;
            case "最想要":
                processWebDataImp.process(WebUrl.getMostWanted(), page);
                break;
            case "高评价":
                processWebDataImp.process(WebUrl.getMostPraise(), page);
                break;
        }
    }
}
