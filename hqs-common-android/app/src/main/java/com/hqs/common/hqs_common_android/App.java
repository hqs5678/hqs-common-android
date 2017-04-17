package com.hqs.common.hqs_common_android;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by super on 2017/3/27.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        // 调试Android 内存泄露  在发布时移除!!!!
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        else{
            LeakCanary.install(this);
        }

    }
}