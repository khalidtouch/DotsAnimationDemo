package com.khalidtouch.loadinganimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.khalidtouch.loadinganimation.utils.Constants;
import com.khalidtouch.loadinganimation.utils.ViewValidator;
import com.khalidtouch.loadinganimation.utils.ViewValidatorImpl;

public class LoadingAnimation extends CoordinatedCircles {
    private int DOTS_SIZE = Constants.DEFAULT_DOTS_SIZE;
    private int DOTS_COUNT = Constants.DEFAULT_DOTS_COUNT;
    private int DURATION = Constants.DEFAULT_DURATION;
    private int COLOR = Constants.DEFAULT_COLOR;

    private ObjectAnimator[] animators;
    boolean hasCircleBeenAnimated = false;

    private ViewValidator validator;

    public LoadingAnimation(@NonNull Context context) {
        super(context);
        init(getContext(), null, Constants.DEFAULT_DOTS_SIZE, Constants.DEFAULT_DOTS_COUNT, Constants.DEFAULT_COLOR);
    }

    public LoadingAnimation(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(getContext(), null, Constants.DEFAULT_DOTS_SIZE, Constants.DEFAULT_DOTS_COUNT, Constants.DEFAULT_COLOR);
    }

    public LoadingAnimation(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(getContext(), null, Constants.DEFAULT_DOTS_SIZE, Constants.DEFAULT_DOTS_COUNT, Constants.DEFAULT_COLOR);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!hasCircleBeenAnimated) {
            hasCircleBeenAnimated = true;
            animateView(true);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animators == null) return;
        for (int i = 0; i < DOTS_COUNT; i++) {
            if (animators[i].isRunning()) {
                animators[i].removeAllListeners();
                animators[i].end();
                animators[i].cancel();
            }
        }
    }

    @Override
    protected void init(Context context, @Nullable AttributeSet attrs, int dotsSize, int dotsCount, int color) {
        validator = new ViewValidatorImpl();

        if(attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingAnimation);
            setColor(typedArray.getColor(R.styleable.LoadingAnimation_dots_color, Constants.DEFAULT_COLOR));
            setDuration(typedArray.getInt(R.styleable.LoadingAnimation_dots_duration, Constants.DEFAULT_DURATION));
            setDotsCount(typedArray.getInt(R.styleable.LoadingAnimation_dots_count, Constants.DEFAULT_DOTS_COUNT));
            setSize(typedArray.getDimensionPixelSize(R.styleable.LoadingAnimation_dots_size, Constants.DEFAULT_DOTS_SIZE));
            typedArray.recycle();
        }
        super.init(context, attrs, DOTS_SIZE, DOTS_COUNT, COLOR);
    }

    public void setDotsCount(int value) {
        if (validator.isCountValid(value)) {
            this.DOTS_COUNT = value;
            init(getContext(), null, Constants.DEFAULT_DOTS_SIZE, Constants.DEFAULT_DOTS_COUNT, Constants.DEFAULT_COLOR);
        }
    }

    public void setDuration(int value) {
        if (validator.isDurationValid(value)) {
            this.DURATION = value;
            init(getContext(), null, Constants.DEFAULT_DOTS_SIZE, Constants.DEFAULT_DOTS_COUNT, Constants.DEFAULT_COLOR);
        }
    }

    public void setColor(int value) {
        this.COLOR = value;
        init(getContext(), null, Constants.DEFAULT_DOTS_SIZE, Constants.DEFAULT_DOTS_COUNT, Constants.DEFAULT_COLOR);
    }

    public void setSize(int value) {
        this.DOTS_SIZE = value;
        init(getContext(), null, Constants.DEFAULT_DOTS_SIZE, Constants.DEFAULT_DOTS_COUNT, Constants.DEFAULT_COLOR);
    }

    private void animateView(final boolean show) {
        animators = new ObjectAnimator[DOTS_COUNT];
        for (int i = 0; i < DOTS_COUNT; i++) {
            PropertyValuesHolder A = PropertyValuesHolder.ofFloat("alpha", 0.2f);
            PropertyValuesHolder B = PropertyValuesHolder.ofFloat("alpha", 1.0f);
            PropertyValuesHolder alpha = show ? A : B;
            animators[i] = ObjectAnimator.ofPropertyValuesHolder(getChildAt(i), alpha);
            animators[i].setRepeatCount(0);
            animators[i].setRepeatMode(ValueAnimator.REVERSE);
            animators[i].setDuration(DURATION);
            animators[i].setStartDelay((long) DURATION * i);
            animators[i].start();
        }
        animators[DOTS_COUNT - 1].addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animateView(!show);
            }
        });
    }
}
