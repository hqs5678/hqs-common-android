package com.hqs.common.app;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by apple on 2016/11/17.
 * 全局的回调, 可以部分替换startActivityForResult, 广播, 同步, 界面之间传值等
 * 
 */

public class AppCallbackProcessor {

    private static HashMap<String, WeakReference<AppCallback>> callbacks = null;

    public static void setCallback(AppCallback callback) {
        setCallback(callback, "appDefaultCallback");
    }

    public static void setCallback(AppCallback callback, String callbackId) {
        HashMap<String, AppCallback> callbacks = new HashMap<>();
        callbacks.put(callbackId, callback);
        setCallbacks(callbacks);
    }
    
    public static void setCallbacks(Map<String, AppCallback> callbacks){
        if (callbacks == null) {
            return;
        }

        Set<String> keys = callbacks.keySet();
        if (keys.size() == 0){
            return;
        }

        if (AppCallbackProcessor.callbacks == null){
            AppCallbackProcessor.callbacks = new HashMap<>();
        }

        for (String key: keys){
            WeakReference<AppCallback> callbackWeakReference = new WeakReference<>(callbacks.get(key));
            AppCallbackProcessor.callbacks.put(key, callbackWeakReference);
        }


    }

    public static void call(Map<String, Object> params){
        call(params, "appDefaultCallback");
    }

    public static void call(Map<String, Object> params, String callbackId) {
        if (callbacks != null){
            if (callbacks.containsKey(callbackId)){
                WeakReference<AppCallback> callbackWeakReference = callbacks.get(callbackId);
                if (callbackWeakReference != null) {
                    AppCallback callback = callbackWeakReference.get();
                    if (callback != null) {
                        callback.call(params);
                        callbacks.remove(callbackId);
                    }
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
    }
}

