package com.hqs.common.app;

import java.util.HashMap;

/**
 * Created by apple on 2016/11/17.
 * 全局的回调, 可以部分替换startActivityForResult, 广播, 同步, 界面之间传值等
 * 
 */

public class AppCallbackProcessor {

    private static HashMap<String, AppCallback> callbacks = null;

    public static void setCallback(AppCallback callback) {
        setCallback(callback, "appDefaultCallback");
    }

    public static void setCallback(AppCallback callback, String callbackId) {
        HashMap<String, AppCallback> callbacks = new HashMap<>();
        callbacks.put(callbackId, callback);
        setCallbacks(callbacks);
    }
    
    public static void setCallbacks(HashMap<String, AppCallback> callbacks){
        if (callbacks == null) {
            return;
        }

        if (AppCallbackProcessor.callbacks == null){
            AppCallbackProcessor.callbacks = callbacks;
        }
        else{
            AppCallbackProcessor.callbacks.putAll(callbacks);
        }

    }

    public static void call(HashMap<String, Object> params){
        call(params, "appDefaultCallback");
    }

    public static void call(HashMap<String, Object> params, String callbackId) {
        if (callbacks != null){
            if (callbacks.containsKey(callbackId)){
                AppCallback callback = callbacks.get(callbackId);
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
        void call(HashMap<String, Object> params);
    }
}

