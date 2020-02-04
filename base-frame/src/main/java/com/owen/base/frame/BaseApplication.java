package com.owen.base.frame;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

public class BaseApplication extends MultiDexApplication {
    private static BaseApplication sBaseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sBaseApplication = this;
    }

    public static <T extends Application> T get() {
        return (T)sBaseApplication;
    }
}
