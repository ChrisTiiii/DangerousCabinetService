package com.example.administrator.dangerouscabinetservice.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: create by ZhongMing
 * Time: 2019/3/15 0015 13:50
 * Description:
 */
public class RootUtils {
    private Context context;
    public volatile static RootUtils instance;

    public synchronized static RootUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (RootUtils.class) {
                instance = new RootUtils(context);
            }
        }
        return instance;
    }

    public RootUtils(Context context) {
        this.context = context;
    }



}
