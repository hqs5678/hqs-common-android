package com.hqs.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 获得屏幕相关的辅助类
 * 
 * @author zhy
 * 
 */
public class ActivityUtil {

	/**
	 * 在 setContentView 之前设置
	 */
	public static void setActivityFullScreen(Activity activity){
		//设置无标题
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置全屏
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**
	 * 隐藏action bar
	 */
	public static void hideActionBar(AppCompatActivity activity){

		try {
			activity.getActionBar().hide();
		} catch (Exception e) {
			ActionBar actionBar = activity.getSupportActionBar();
			actionBar.hide();
		}
	}

}
