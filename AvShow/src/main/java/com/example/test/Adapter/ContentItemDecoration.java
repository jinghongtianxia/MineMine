package com.example.test.Adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by king on 2016/12/24.
 */

public class ContentItemDecoration extends RecyclerView.ItemDecoration {

    private final Paint paint;

    public ContentItemDecoration() {
//        super();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View childAt = parent.getChildAt(i);
//            c.drawRect(0, childAt.getBottom(), childAt.getWidth(), childAt.getBottom() + 3, paint);
//        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildLayoutPosition(view)==0){
            outRect.set(0,0,0,0);
        }else {
            outRect.set(0,30,0,0);
        }
    }
}
