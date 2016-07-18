package com.loopcupcakes.gassy.decorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by evin on 7/17/16.
 */
public class SimpleDecorator extends RecyclerView.ItemDecoration {
    private final int mSpace;

    public SimpleDecorator(int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;

        if (parent.getChildAdapterPosition(view) < 1)
            outRect.top = mSpace;
    }
}