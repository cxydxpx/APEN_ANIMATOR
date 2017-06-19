package com.example.apen.oneanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 Y_MS
 * Created by ${APEN} on 2017/6/19.
 * GitHub：https://github.com/cxydxpx
 */

public class AnimationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tv;
    private List<String> datas;
    private RelativeLayout mll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        mll = (RelativeLayout) findViewById(R.id.ll);
        tv = (TextView) findViewById(R.id.tv_car);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        initdata();

        IAdapter adapter = new IAdapter(this,datas);

        recyclerView.setAdapter(adapter);

        adapter.setCallBackListener(new IAdapter.CallBackListener() {
            @Override
            public void callBackImg(TextView goodsImg) {
                addShopCarAnimation(goodsImg);
            }
        });
    }

    private void initdata() {

        datas = new ArrayList<>();

        datas.add("动1");
        datas.add("动2");
        datas.add("动3");
        datas.add("动4");

    }

    private void addShopCarAnimation(TextView location) {

        // 创造出执行动画的主题goodsImg（这个图片就是执行动画的图片,从开始位置出发,经过一个抛物线（贝塞尔曲线）,移动到购物车里）
        final TextView goods = new TextView(this);
        goods.setText("移动起来吧");

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        mll.addView(goods, params);

        // 得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        mll.getLocationInWindow(parentLocation);

        // 得到商品图片的坐标（用于计算动画开始的坐标）
        int startLoc[] = new int[2];
        location.getLocationInWindow(startLoc);

        // 得到购物车图片的坐标(用于计算动画结束后的坐标)
        int endLoc[] = new int[2];
        tv.getLocationInWindow(endLoc);

        // 开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLoc[0] - parentLocation[0] + location.getWidth() / 2;
        float startY = startLoc[1] - parentLocation[1] + location.getHeight() / 2;

        // 商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
        float toX = endLoc[0] - parentLocation[0] + tv.getWidth() / 5;
        float toY = endLoc[1] - parentLocation[1];

        // 开始绘制贝塞尔曲线
        Path path = new Path();
        // 移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        // 使用二阶贝塞尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((startX + toX) / 2, startY, toX, toY);
        // mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，如果是true，path会形成一个闭环
        mPathMeasure = new PathMeasure(path, false);

        // 属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(500);

        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                // mCurrentPosition此时就是中间距离点的坐标值
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
            }
        });

        // 开始执行动画
        valueAnimator.start();

        // 动画结束后的处理
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 购物车商品数量加1
//                    goodsCount++;
//                    isShowCartGoodsCount();
//                    mShoppingCartCountTv.setText(String.valueOf(goodsCount));
//                     把执行动画的商品图片从父布局中移除
//                    mShoppingCartRly.removeView(goods);
            }
        });
    }

    // 路径测量
    private PathMeasure mPathMeasure;
    // 贝塞尔曲线中间过程点坐标
    private float[] mCurrentPosition = new float[2];

    private class adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            VH vh;
            if (convertView == null) {
                convertView = View.inflate(AnimationActivity.this, R.layout.adapter_shopping_cart_item, null);
                vh = new VH();
                vh.tv = (TextView) convertView.findViewById(R.id.tv_shopping_cart_item);
                convertView.setTag(vh);
            } else {
                vh = (VH) convertView.getTag();
            }
            final TextView tv = vh.tv;

            vh.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addShopCarAnimation(tv);
                }
            });

            return convertView;
        }
    }

    private class VH {
        private TextView tv;
    }

}
