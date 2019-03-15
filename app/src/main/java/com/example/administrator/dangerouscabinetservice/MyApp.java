package com.example.administrator.dangerouscabinetservice;

import android.app.Application;


/**
 * Author: create by ZhongMing
 * Time: 2019/3/15 0015 09:23
 * Description:
 */
public class MyApp extends Application {
    public volatile static MyApp myApp;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static synchronized MyApp getInstance() {
        if (myApp == null) {
            synchronized (MyApp.class) {
                myApp = new MyApp();
            }
        }
        return myApp;
    }

}
