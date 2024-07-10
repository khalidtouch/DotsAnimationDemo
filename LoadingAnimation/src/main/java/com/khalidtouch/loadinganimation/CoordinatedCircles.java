package com.khalidtouch.loadinganimation;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.khalidtouch.loadinganimation.utils.Constants;

public abstract class CoordinatedCircles extends LinearLayout {
    private int DOTS_SIZE = Constants.DEFAULT_DOTS_SIZE;
    protected int DOTS_COUNT = Constants.DEFAULT_CIRCLE_DOTS_COUNT;
    private int COLOR = Constants.DEFAULT_COLOR;


    public CoordinatedCircles(@NonNull Context context) {
        super(context);
    }

    public CoordinatedCircles(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CoordinatedCircles(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int calculatedDiameter = (int) (2 * (DOTS_SIZE * 2.5) + 2 * (DOTS_SIZE * 2.5));
        setMeasuredDimension(calculatedDiameter, calculatedDiameter);
    }

    protected void init(
       Context context,
       @Nullable AttributeSet attrs,
       int dotsSize,
       int dotsCount,
       int color
    ) {
        DOTS_SIZE = dotsSize;
        DOTS_COUNT = dotsCount;
        COLOR = color;
        configureView();
        removeAllViews();
        populateCircles();
    }

    private void populateCircles() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < DOTS_COUNT; i++) {
            Circle circle = new Circle(getContext(), DOTS_SIZE, COLOR, true);
            LinearLayout layout = new LinearLayout(getContext());
            params.setMargins(DOTS_SIZE / 2, DOTS_SIZE / 2, DOTS_SIZE / 2, DOTS_SIZE / 2);
            layout.setGravity(Gravity.CENTER);
            layout.addView(circle);
            addView(layout, params);
        }
    }

    private void configureView() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        setBackgroundColor(Color.TRANSPARENT);
    }
}
