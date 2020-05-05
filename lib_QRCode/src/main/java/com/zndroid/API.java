package com.zndroid;

import android.util.Log;

import com.zndroid.zbar.BuildConfig;

public abstract class API<T> {
    abstract T getDefault();
    abstract String getTag();

    protected void showLog(String msg) {
        if (BuildConfig.DEBUG)
            Log.i(getTag(), msg);
    }

    protected void showError(String msg) {
        throw new UnsupportedOperationException(msg);
    }
}
