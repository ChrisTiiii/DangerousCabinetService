package com.example.administrator.dangerouscabinetservice;

import android.app.Application;

import com.example.administrator.dangerouscabinetservice.db.UserRoot;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.List;


/**
 * Author: create by ZhongMing
 * Time: 2019/3/15 0015 09:23
 * Description:
 */
public class MyApp extends Application {
    public volatile static MyApp myApp;
    public static List<UserRoot> userList;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());
        //设置日志显示
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);
    }

    public static synchronized MyApp getInstance() {
        if (myApp == null) {
            synchronized (MyApp.class) {
                myApp = new MyApp();
            }
        }
        return myApp;
    }


    public static List<UserRoot> getUserList() {
        return userList;
    }

    public static void setEmptyUserList() {
        setUserList(null);
    }

    public static void setUserList(List<UserRoot> userList) {
        MyApp.userList = userList;
    }
}
