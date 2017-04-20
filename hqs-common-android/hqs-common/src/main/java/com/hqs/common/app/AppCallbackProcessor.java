package com.hqs.common.app;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 2016/11/17.
 * 全局的回调, 可以部分替换startActivityForResult, 广播, 同步, 界面之间传值等
 * 
 */

public class AppCallbackProcessor {

    private static Map<String, WeakReference<AppCallback>> callbacks = null;
    public static String defaultCallbackId = "qs_appDefaultCallback";

    public static void addCallback(AppCallback callback) {
        addCallback(callback, defaultCallbackId);
    }

    public static void addCallback(AppCallback callback, String callbackId) {

        if (callbacks == null) {
            callbacks = new HashMap<>();
        }

        callbacks.put(callbackId, new WeakReference<>(callback));
    }

    public static void call(Map<String, Object> params){
        call(params, defaultCallbackId);
    }

    public static void call(Map<String, Object> params, String callbackId) {
        if (callbacks != null){
            if (callbacks.containsKey(callbackId)){
                AppCallback callback = callbacks.get(callbackId).get();
                if (callback != null) {
                    callback.call(params);
                    callbacks.remove(callbackId);
                }
            }
        }
    }


    public static void removeAllCallbacks(){
        callbacks = null;
    }

    public static void removeCallback(String callbackId){
        if (callbacks != null){
            if (callbacks.containsKey(callbackId)){
                callbacks.remove(callbackId);
            }
        }
    }

    public interface AppCallback {
        void call(Map<String, Object> params);
        void call(Map<String, Object> params, String callbackId);
    }
}

