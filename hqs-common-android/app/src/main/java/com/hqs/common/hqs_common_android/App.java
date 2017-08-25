package com.hqs.common.hqs_common_android;

import android.app.Application;

import com.hqs.common.utils.Log;
import com.squareup.leakcanary.LeakCanary;
import com.wenming.library.LogReport;
import com.wenming.library.save.imp.CrashWriter;
import com.wenming.library.util.FileUtil;

import java.io.File;

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



        LogReport.getInstance()
                .setCacheSize(30 * 1024 * 1024)//支持设置缓存大小，超出后清空
                .setLogDir(getApplicationContext(), "sdcard/" + this.getString(this.getApplicationInfo().labelRes) + "/")//定义路径为：sdcard/[app name]/
                .setWifiOnly(true)//设置只在Wifi状态下上传，设置为false为Wifi和移动网络都上传
                .setLogSaver(new CrashWriter(getApplicationContext()))//支持自定义保存崩溃信息的样式
                //.setEncryption(new AESEncode()) //支持日志到AES加密或者DES加密，默认不开启
                .init(getApplicationContext());

        try {
            File file = new File("sdcard/" + this.getString(this.getApplicationInfo().labelRes) + "/");
            printFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void printFile(File file){
        if (file.exists()){
            if (file.isDirectory()){
                File[] files = file.listFiles();
                if (files.length > 0){
                    for(File f: files){
                        if (f.isDirectory()){
                            printFile(f);
                        }
                        else{
                            Log.print(FileUtil.getText(f));
                            f.delete();
                        }
                    }
                }
            }
            file.delete();
        }
    }
}
