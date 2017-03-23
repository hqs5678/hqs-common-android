package com.hqs.common.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by apple on 16/9/27.
 */

public class Log {

    public static boolean debug = true;
    public static String tag = "---log---";
    private static boolean saveToFile = false;
    public static String logFilePath = null;
    private static ArrayList<String> tmpLogs;
    private static String logFileDir;
    public static int bufferSize = 80;

    static {
        if (SDCardUtils.isSDCardEnable()) {
            logFileDir = SDCardUtils.getSDCardPath() + "com.hqs.common.log" + File.separator;
            File file = new File(logFileDir);
            if (file.exists() == false) {
                file.mkdir();
            }
        }
    }

    // log
    public static void print(String logString) {
        if (logString == null) {
            logString = "null";
        }
        if (debug) {
            android.util.Log.e(tag, logString);
        }

        save(logString);
    }

    public static void print(int log) {
        if (debug){
            android.util.Log.e(tag, log + "");
        }
        save(log + "");
    }

    public static void print(float log) {
        if (debug){
            android.util.Log.e(tag, log + "");
        }
        save(log + "");
    }

    public static void print(double log) {
        if (debug){
            android.util.Log.e(tag, log + "");
        }
        save(log + "");
    }

    public static void print(Object log) {
        String l;
        if (log == null) {
            l = "null";
        } else {
            l = log.toString();
        }
        if (debug){
            android.util.Log.e(tag, l);
        }
        save(l);
    }

    private static void save(final String logString) {
        if (saveToFile) {
            String log = tag + logString + "\n";
            tmpLogs.add(log);
            if (tmpLogs.size() > bufferSize) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                    Log.save();
                    }
                }).start();
            }
        }
    }

    public static void save(){
        if (saveToFile == false){
            return;
        }
        OutputStream os = null;
        BufferedOutputStream bos = null;
        try {

            logFilePath = getLogFilePath();
            File file = new File(logFilePath);

            if (tmpLogs.size() == 0){
                tmpLogs.add("There is no log to save!!!");
            }

            os = new FileOutputStream(file);
            bos = new BufferedOutputStream(os);

            for (String s : tmpLogs) {
                bos.write(s.getBytes(Charset.forName("UTF-8")));
            }
            bos.flush();
            tmpLogs.clear();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void setSaveToFile(boolean saveToFile) {
        if (SDCardUtils.isSDCardEnable()) {
            Log.saveToFile = saveToFile;
            if (Log.saveToFile) {
                Log.tmpLogs = new ArrayList<>();
            }
        }
    }

    private static String getLogFilePath() {
        DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fileName = df.format(new Date());
        File file = new File(logFileDir);
        if (file.exists() == false){
            file.mkdirs();
        }
        return logFileDir + fileName + ".log";
    }

    public static void setLogFileDir(String logFileDir) {
        Log.logFileDir = logFileDir;
    }

    public static String getLogFileDir() {
        return logFileDir;
    }
}
