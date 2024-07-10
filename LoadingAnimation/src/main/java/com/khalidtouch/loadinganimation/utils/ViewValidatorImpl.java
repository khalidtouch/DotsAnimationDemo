package com.khalidtouch.loadinganimation.utils;

public class ViewValidatorImpl implements ViewValidator{
    @Override
    public boolean isCountValid(int value) {
        return value > 0;
    }

    @Override
    public boolean isDurationValid(int value) {
        return value > 0;
    }
}
