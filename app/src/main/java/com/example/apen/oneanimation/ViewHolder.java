package com.example.apen.oneanimation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * 作者 Y_MS
 * Created by ${APEN} on 2017/6/19.
 * GitHub：https://github.com/cxydxpx
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_shopping_cart_item;

    public ViewHolder(View itemView) {
        super(itemView);
        tv_shopping_cart_item = (TextView) itemView.findViewById(R.id.tv_shopping_cart_item);
    }
}
