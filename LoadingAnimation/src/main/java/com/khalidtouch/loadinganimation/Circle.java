package com.khalidtouch.loadinganimation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Circle extends View {
    int mRadius = 20;
    int mStrokeWidth = 0;
    int mColor = 0;
    boolean mShouldDrawOnlyStroke = false;
    boolean mIsAntiAlias = true;
    private Paint mPaint = new Paint();


    public Circle(Context context, int radius, int color, boolean isAntiAlias) {
        super(context);
        this.mRadius = radius;
        this.mColor = color;
        this.mIsAntiAlias = isAntiAlias;
        initializeProperties();
    }

    public Circle(Context context, int radius, int color, boolean shouldDrawOnlyStroke, int strokeWidth) {
        super(context);
        this.mRadius = radius;
        this.mColor = color;
        this.mShouldDrawOnlyStroke = shouldDrawOnlyStroke;
        this.mStrokeWidth = strokeWidth;
        initializeProperties();
    }

    public Circle(Context context) {
        super(context);
        initializeProperties();
    }

    public Circle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeAttributes(attrs);
        initializeProperties();
    }

    public Circle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeAttributes(attrs);
        initializeProperties();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int diameter = (2 * mRadius) + mStrokeWidth;
        setMeasuredDimension(diameter, diameter);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        float x = getWidth() / 2f;
        float y = getHeight() / 2f;
        canvas.drawCircle(x, y, (float) mRadius, mPaint);
    }

    private void initializeProperties() {
        mPaint.setAntiAlias(mIsAntiAlias);
        if(mShouldDrawOnlyStroke) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth((float) mStrokeWidth);
        } else {
            mPaint.setStyle(Paint.Style.FILL);
        }

        mPaint.setColor(mColor);
    }

    public void initializeAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Circle, 0, 0);

        this.mRadius = typedArray.getDimensionPixelSize(R.styleable.Circle_radius, 30);
        this.mColor = typedArray.getColor(R.styleable.Circle_color, 0);
        this.mShouldDrawOnlyStroke = typedArray.getBoolean(R.styleable.Circle_shouldDrawOnlyStroke, false);

        if (mShouldDrawOnlyStroke) {
            this.mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.Circle_strokeWidth, 0);
        }

        typedArray.recycle();
    }


}
