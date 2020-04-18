package com.aotuman.studydemo;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.util.Date;

public class PathConstant {

    public static String AppPath;
    public static String CAMERAPATH;
    public static String THUMPATH;
    public static String ATTACHMENT;
    public static String LOGPATH;
    public static String SHAREPATH;
    public static String TEMPPATH;

    public static String Root = "Admin";//默认账户目录

    public static void init(Context applicationContext) {
        PathConstant.AppPath = android.os.Environment.getExternalStorageDirectory().getPath() + "/" + applicationContext.getPackageName();
        PathConstant.CAMERAPATH = PathConstant.AppPath + "/" + Root + "/Camera";
        PathConstant.SHAREPATH = PathConstant.AppPath + "/" + Root + "/Share";///storage/emulated/0/packagename/Camera
        PathConstant.THUMPATH = PathConstant.AppPath + "/" + Root + "/Thum";///storage/emulated/0/packagename/Thum
        PathConstant.ATTACHMENT = PathConstant.AppPath + "/" + Root + "/Attachment";///storage/emulated/0/packagename/Thum
        PathConstant.TEMPPATH = PathConstant.AppPath + "/" + Root + "/Temp";///storage/emulated/0/packagename/Thum
        PathConstant.LOGPATH = PathConstant.AppPath + "/" + Root + "/Log";///storage/emulated/0/packagename/Log


        new File(AppPath).mkdirs();
        new File(CAMERAPATH).mkdirs();
        new File(THUMPATH).mkdirs();
        new File(ATTACHMENT).mkdirs();
        new File(LOGPATH).mkdirs();
    }
}
