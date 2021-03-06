package com.shishanqing.shakedemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener{

    private TextView mtv0;
    private TextView mtv1;
    private TextView mtv2;
    private TextView mtv3;

    private boolean mNeedShake = false;
    private boolean mStartShake = false;

    private static final int ICON_WIDTH = 80;
    private static final int ICON_HEIGHT = 94;
    private static final float DEGREE_0 = 1.8f;
    private static final float DEGREE_1 = -2.0f;
    private static final float DEGREE_2 = 2.0f;
    private static final float DEGREE_3 = -1.5f;
    private static final float DEGREE_4 = 1.5f;
    private static final int ANIMATION_DURATION = 80;

    private int mCount = 0;

    float mDensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (dm != null) {
            mDensity = dm.density;
        }

        mtv0 = (TextView) findViewById(R.id.tv0);
        mtv0.setOnClickListener(this);
        mtv1 = (TextView) findViewById(R.id.tv1);
        mtv1.setOnClickListener(this);
        mtv2 = (TextView) findViewById(R.id.tv2);
        mtv2.setOnClickListener(this);
        mtv3 = (TextView) findViewById(R.id.tv3);
        mtv3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!mStartShake) {
            mStartShake = true;
            mNeedShake = true;
            shakeAnimation(mtv0);
            shakeAnimation(mtv1);
            shakeAnimation(mtv2);
            shakeAnimation(mtv3);
        }
    }

    private void shakeAnimation(final View v) {
        float rotate = 0;
        int c = mCount++ % 5;
        if (c == 0) {
            rotate = DEGREE_0;
        } else if (c == 1) {
            rotate = DEGREE_1;
        } else if (c == 2) {
            rotate = DEGREE_2;
        } else if (c == 3) {
            rotate = DEGREE_3;
        } else {
            rotate = DEGREE_4;
        }
        final RotateAnimation mra = new RotateAnimation(rotate, -rotate, ICON_WIDTH * mDensity / 2, ICON_HEIGHT * mDensity / 2);
        final RotateAnimation mrb = new RotateAnimation(-rotate, rotate, ICON_WIDTH * mDensity / 2, ICON_HEIGHT * mDensity / 2);

        mra.setDuration(ANIMATION_DURATION);
        mrb.setDuration(ANIMATION_DURATION);

        mra.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                if (mNeedShake) {
                    mra.reset();
                    v.startAnimation(mrb);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }

        });

        mrb.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                if (mNeedShake) {
                    mrb.reset();
                    v.startAnimation(mra);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }

        });
        v.startAnimation(mra);
    }

    @Override
    public void onBackPressed() {
        if (!mNeedShake) {
            super.onBackPressed();
        } else {
            mNeedShake = false;
            mCount = 0;
            mStartShake = false;
        }
    }
}
