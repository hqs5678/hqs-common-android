package com.hqs.common.utils;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by apple on 16/9/5.
 */

public class ViewUtil {


    public static void getViewRect(final View view, final OnViewRectCallBack onViewRectCallBack){

        new Thread(new Runnable() {
            @Override
            public void run() {
                int n = 0;
                while (true){
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
                    int h = view.getHeight();
                    int w = view.getWidth();
                    RectF rectF = new RectF();
                    rectF.left = x;
                    rectF.top = y;
                    rectF.right = x + w;
                    rectF.bottom = y + h;

                    if (rectF.width() == 0 && rectF.height() == 0 && x == y && x == 0) {
                        try {
                            Thread.sleep(100);
                            n += 1;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        onViewRectCallBack.onRect(rectF);
                        return;
                    }

                    if (n == 50){
                        // 5 秒
                        onViewRectCallBack.onRect(rectF);
                        return;
                    }
                }
            }
        }).start();


    }

    public static void setRoundCornerToView(final View view, final float cornerRadius, boolean ripple, int backgroundColor) {
        setRippleDrawableRoundCorner(view, cornerRadius, ripple, lighterColor(backgroundColor), backgroundColor);
    }

    public static void setRoundCornerToView(final View view, final float cornerRadius, final int rippleColor, int backgroundColor) {
        setRippleDrawableRoundCorner(view, cornerRadius, true, rippleColor, backgroundColor);
    }

    private static void setRippleDrawableRoundCorner(final View view, final float cornerRadius, boolean ripple, final int backgroundColor) {
        setRippleDrawableRoundCorner(view, cornerRadius, ripple, lighterColor(backgroundColor), backgroundColor);
    }

    private static void setRippleDrawableRoundCorner(final View view, final float cornerRadius, boolean ripple, final int rippleColor, final int backgroundColor) {

        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.setShape(new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                paint.setColor(backgroundColor);
                canvas.drawRoundRect(new RectF(0, 0, view.getWidth(), view.getHeight()), cornerRadius, cornerRadius, paint);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ripple) {
                RippleDrawable newRippleDrawable = null;
                newRippleDrawable = new RippleDrawable(ColorStateList.valueOf(rippleColor), shapeDrawable, shapeDrawable);
                view.setBackground(newRippleDrawable);
            } else {
                view.setBackground(shapeDrawable);
            }
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(shapeDrawable);
            }
            else{
                view.setBackgroundColor(backgroundColor);
            }
        }
    }

    /**
     * 获取activity 的contentView
     * @param activity
     * @return
     */
    public static ViewGroup getContentView(Activity activity) {
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
        return viewGroup;
    }

    /**
     * 获取activity root view (最外层的view)
     * @param activity
     * @return
     */
    public static ViewGroup getRootView(Activity activity) {
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
        while (true) {
            ViewGroup vg = (ViewGroup) viewGroup.getParent();
            if (vg == null){
                break;
            }
            viewGroup = vg;
        }
        return viewGroup;
    }


    private static int lighterColor(int color) {
        int r = Color.red(color);
        int b = Color.blue(color);
        int g = Color.green(color);
        int a = Color.alpha(color);

        int offset = 20;
        a -= offset;

        int newColor = Color.argb(a, r, g, b);
        return newColor;
    }

    public interface OnViewRectCallBack{
        void onRect(RectF rectF);
    }
}
