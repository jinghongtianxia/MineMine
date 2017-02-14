package com.example.test.WebParser;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.example.test.Adapter.DetailRvItemDecoration;
import com.example.test.Data.SourceData;
import com.example.test.R;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by king on 2017/1/25.
 */

public class DetailPage extends AppCompatActivity {

    private String href;
    private ImageView detailsImage;
    private ProgressBar progressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_page);
        final Intent intent = getIntent();
        href = intent.getStringExtra("href");
        DetailParser detailParser = new DetailParser();
        detailParser.addHref(href);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        Observable.create(detailParser).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("System.out.println(s); + " + s);
                initView(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
        System.gc();
    }

    private void initView(String bitmapUrl) {
        RecyclerView detailsRecyclerview = (RecyclerView) findViewById(R.id.details_recyclerview);
        detailsImage = (ImageView) findViewById(R.id.details_image);
        detailsRecyclerview.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        detailsRecyclerview.addItemDecoration(new DetailRvItemDecoration());
        detailsRecyclerview.setAdapter(new DetailAdapter());
        showViewContent(detailsImage, bitmapUrl);
    }

    private void showViewContent(ImageView imageView, String bitmapUrl) {
        Glide.with(DetailPage.this).load(bitmapUrl).placeholder(R.drawable.ic_autorenew_black_24dp).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        progressBar.setVisibility(View.INVISIBLE);
    }


    class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private SourceData sourceData = new SourceData();

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DetailsViewHolder(View.inflate(DetailPage.this, R.layout.detail_rv_item, null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ((DetailsViewHolder) holder).itemLeft.setText(sourceData.getLeftMessage().get(position));
            ((DetailsViewHolder) holder).itemRight.setText(sourceData.getRightMessage().get(position));
            ((DetailsViewHolder) holder).itemRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(sourceData.getRightMessage().get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return sourceData.getLeftMessage().size();
        }

        class DetailsViewHolder extends RecyclerView.ViewHolder {


            private final TextView itemLeft;
            private final TextView itemRight;

            DetailsViewHolder(View itemView) {
                super(itemView);
                itemLeft = (TextView) itemView.findViewById(R.id.item_left);
                itemRight = (TextView) itemView.findViewById(R.id.item_right);
            }
        }

    }

}
