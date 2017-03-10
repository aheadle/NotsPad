package com.knjin.notspad.application;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Jing on 16/7/23.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
