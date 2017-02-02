package com.example.test.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.test.Data.SourceData;
import com.example.test.MainActivity;
import com.example.test.MyNVListener;
import com.example.test.R;
import com.example.test.WebParser.DetailPage;
import com.example.test.WebParser.DetailParser;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by king on 2016/12/13.
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {

    private static final int TYPE_FOOT = 1;
    private static final int TYPE_NORMAL = 0;
    private static final int NOTHING = 3;
    private static String ITEM_TYPE;
    private Context context;
    private SourceData value = new SourceData();

    public static void itemType(String type) {
        ITEM_TYPE = type;
    }


    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        if (ITEM_TYPE.equals("首页")) {
            return new ContentViewHolder(View.inflate(parent.getContext(), R.layout.content_cardview, null), TYPE_NORMAL);
        } else if (!ITEM_TYPE.equals("首页")) {
            if (viewType == TYPE_NORMAL) {
                return new ContentViewHolder(View.inflate(parent.getContext(), R.layout.content_cardview, null), TYPE_NORMAL);

            } else if (viewType == TYPE_FOOT) {
                return new ContentViewHolder(View.inflate(parent.getContext(), R.layout.footview_layout, null), TYPE_FOOT);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final ContentViewHolder holder, final int position) {
        if (ITEM_TYPE.equals("首页")) {
            Glide.with(context).load(value.getBitmapSource().get(position)).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.cardIv);
            holder.cardTitle.setText(value.getTitleSource().get(position));
            holder.cardId.setText(value.getIdSource().get(position));
        } else if (!ITEM_TYPE.equals("首页")) {
            if (position == value.getTitleSource().size()) {
                holder.footView.setProgress(ProgressBar.IMPORTANT_FOR_ACCESSIBILITY_AUTO);
            } else if (position <= value.getTitleSource().size()) {
                Glide.with(context).load(value.getBitmapSource().get(position)).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.cardIv);
                holder.cardTitle.setText(value.getTitleSource().get(position));
                holder.cardId.setText(value.getIdSource().get(position));
            }
        }
    }

    @Override
    public int getItemCount() {
        if (ITEM_TYPE.equals("首页")) {
            return value.getTitleSource().size();
        } else if (!ITEM_TYPE.equals("首页")) {
            return value.getTitleSource().size() + 1;
        }
        return NOTHING;
    }

    @Override
    public int getItemViewType(int position) {
        if (ITEM_TYPE.equals("首页")) {
            return TYPE_NORMAL;
        } else if (!ITEM_TYPE.equals("首页")) {
            if (position == getItemCount() - 1) {
                return TYPE_FOOT;
            } else if (position < getItemCount() - 1) {
                return TYPE_NORMAL;
            }
        }
        return NOTHING;
    }

    class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView cardIv;
        private TextView cardTitle;
        private TextView cardId;
        private ProgressBar footView;

        ContentViewHolder(View itemView, int type) {
            super(itemView);
            if (ITEM_TYPE.equals("首页")) {
                if (type == TYPE_FOOT) {
                    footView = (ProgressBar) itemView.findViewById(R.id.footview);
                } else {
                    cardId = (TextView) itemView.findViewById(R.id.card_id);
                    cardTitle = (TextView) itemView.findViewById(R.id.card_title);
                    cardIv = (ImageView) itemView.findViewById(R.id.card_iv);
                }
                cardIv.setOnClickListener(this);
            } else if (!ITEM_TYPE.equals("首页")) {
                if (type == TYPE_FOOT) {
                    footView = (ProgressBar) itemView.findViewById(R.id.footview);
                } else if (type == TYPE_NORMAL) {
                    cardId = (TextView) itemView.findViewById(R.id.card_id);
                    cardTitle = (TextView) itemView.findViewById(R.id.card_title);
                    cardIv = (ImageView) itemView.findViewById(R.id.card_iv);
                    cardIv.setOnClickListener(this);
                }
            }
        }

        @Override
        public void onClick(View v) {
            int layoutPosition = ContentViewHolder.this.getLayoutPosition();
            final String href = value.getUrlSource().get(layoutPosition);
            DetailParser detailParser = new DetailParser();
            detailParser.addHref(href);
//            System.out.println("detailParser.addHref(href);");
            Observable.create(detailParser).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String value) {
                    Intent intent = new Intent(context, DetailPage.class);
                    intent.putExtra("href", value);
                    context.startActivity(intent);
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
}
