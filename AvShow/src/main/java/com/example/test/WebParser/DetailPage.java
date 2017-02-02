package com.example.test.WebParser;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.test.Adapter.DetailRvItemDecoration;
import com.example.test.Data.SourceData;
import com.example.test.R;

import java.util.ArrayList;

/**
 * Created by king on 2017/1/25.
 */

public class DetailPage extends AppCompatActivity {

    private String href;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_page);
        Intent intent = getIntent();
        href = intent.getStringExtra("href");
        initView();
    }

    private void initView() {
        ImageView detailsImage = (ImageView) findViewById(R.id.details_image);
        RecyclerView detailsRecyclerview = (RecyclerView) findViewById(R.id.details_recyclerview);
        Glide.with(DetailPage.this).load(href).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(detailsImage);
        detailsRecyclerview.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL,false));
        detailsRecyclerview.addItemDecoration(new DetailRvItemDecoration());
        detailsRecyclerview.setAdapter(new DetailAdapter());
    }

    class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private SourceData sourceData = new SourceData();
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            ArrayList<String> leftMessage = sourceData.getLeftMessage();
//            for (int i = 0; i < leftMessage.size(); i++) {
//                System.out.println(leftMessage.get(i));
//            }
            System.out.println("onCreateViewHolder");
            return new DetailsViewHolder(View.inflate(parent.getContext(),R.layout.detail_rv_item,null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((DetailsViewHolder) holder).itemLeft.setText(sourceData.getLeftMessage().get(position));
            ((DetailsViewHolder) holder).itemRight.setText(sourceData.getRightMessage().get(position));
        }

        @Override
        public int getItemCount() {
            return sourceData.getLeftMessage().size();
        }

        class DetailsViewHolder extends RecyclerView.ViewHolder{


            private final TextView itemLeft;
            private final TextView itemRight;

            public DetailsViewHolder(View itemView) {
                super(itemView);
                itemLeft = (TextView) itemView.findViewById(R.id.item_left);
                itemRight = (TextView) itemView.findViewById(R.id.item_right);
            }
        }

    }

}
