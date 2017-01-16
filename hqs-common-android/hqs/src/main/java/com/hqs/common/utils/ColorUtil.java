package com.hqs.common.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by apple on 16/9/1.
 */

public class ColorUtil {

    public static int randomColor(){
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public static int randomDarkColor(){
        Random random = new Random();
        return Color.argb(255, random.nextInt(100) + 5, random.nextInt(100) + 5, random.nextInt(100) + 5);
    }

    public static int randomLightColor(){
        Random random = new Random();
        return Color.argb(255, random.nextInt(156) + 100, random.nextInt(156) + 100, random.nextInt(156) + 100);
    }
}
