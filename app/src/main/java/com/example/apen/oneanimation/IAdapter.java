package com.example.apen.oneanimation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * 作者 Y_MS
 * Created by ${APEN} on 2017/6/19.
 * GitHub：https://github.com/cxydxpx
 */

public class IAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final Context mContext;
    private List<String> mDatas;

    public IAdapter(Context context, List<String> datas) {
        this.mDatas = datas;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext,R.layout.adapter_shopping_cart_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.tv_shopping_cart_item.setText(mDatas.get(position));

        holder.tv_shopping_cart_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBackListener != null){
                    mCallBackListener.callBackImg(holder.tv_shopping_cart_item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    private CallBackListener mCallBackListener;

    public void setCallBackListener(CallBackListener mCallBackListener){
        this.mCallBackListener = mCallBackListener;
    }

    public interface CallBackListener{
        void callBackImg(TextView goodsImg);
    }
}
