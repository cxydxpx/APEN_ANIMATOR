package com.example.apen.oneanimation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.iv);
    }

    public void alpha(View v) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "alpha", 1f, 0f, 1f);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }


    public void rotation(View v) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "rotation", 0f, 360f);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }

    public void translation(View v) {
        float curTranslation = v.getTranslationX();
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        float trans = manager.getDefaultDisplay().getWidth();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "translationX", curTranslation, -trans, curTranslation);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }

    public void scale(View v) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "scaleY", 1f, 2f, 1f);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }

    public void all(View v) {
//        代码方式
        float curTranslation = v.getTranslationX();
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        float trans = manager.getDefaultDisplay().getWidth();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "rotation", 0f, 360f);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(iv, "alpha", 1f, 0f, 1f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(iv, "scaleY", 1f, 2f, 1f);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(iv, "translationX", curTranslation, -trans, curTranslation);

        AnimatorSet set = new AnimatorSet();
        set.play(objectAnimator1).with(objectAnimator2).with(objectAnimator).after(objectAnimator3);
        set.setDuration(3000);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);

            }
        });
    }

    public void iv(View v) {
//        XML方式
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.translation);
        animator.setTarget(v);
        animator.start();
    }
}
