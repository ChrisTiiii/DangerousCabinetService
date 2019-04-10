package com.example.administrator.dangerouscabinetservice;

import android.app.Application;
import android.util.Log;

import com.example.administrator.dangerouscabinetservice.db.BaseChemical;
import com.example.administrator.dangerouscabinetservice.db.DBManager;
import com.example.administrator.dangerouscabinetservice.db.NowChemical;
import com.example.administrator.dangerouscabinetservice.db.UserRoot;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Author: create by ZhongMing
 * Time: 2019/3/15 0015 09:23
 * Description:
 */
public class MyApp extends Application {
    public volatile static MyApp myApp;
    public static List<UserRoot> tempUserList;

    private static List<UserRoot> userList;//用户权限
    private List<BaseChemical> chemicalList;//基本化学库
    private List<NowChemical> nowChemicalList;//当前化学柜内数据


    private static List<String> OffList;//已取出的rfid

    @Override

    public void onCreate() {
        super.onCreate();
        //初始化DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());
        //设置日志显示
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);

        DBManager.getInstance().deleteTable(10);//删除化学表
        DBManager.getInstance().deleteTable(20);//删除用户表
        DBManager.getInstance().deleteTable(30);//删除柜内数据表
        DBManager.getInstance().deleteTable(40);//删除台账数据表
        initData();
    }

    private void initData() {
        nowChemicalList = new ArrayList<>();
        chemicalList = new ArrayList<>();
        userList = new ArrayList<>();
        makeData();
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

    public static List<UserRoot> getTempUserList() {
        return tempUserList;
    }

    public static void setEmptyTempUserList() {
        setTempUserList(null);
    }

    public static void setTempUserList(List<UserRoot> tempUserList) {
        MyApp.tempUserList = tempUserList;
    }

    public static List<String> getOffList() {
        return OffList;
    }

    public static void setOffList(List<String> offList) {
        OffList = offList;
    }

    private void makeData() {
        //基本化学表插入假数据
        for (int i = 0; i < 50; i++) {
            BaseChemical model = new BaseChemical();
            model.setRFID("JFHE" + (i + 1));
            model.setChemicalID(String.valueOf(new Random().nextInt(100)));
            model.setChemicalName("测试" + i + "化学品");
            model.setCabinetID("柜子" + i);
            model.setWeight(String.valueOf((i + 1) * 12));
            model.setCheDescription("这是" + i + 1 + "个没有危险性的化学品");
            chemicalList.add(model);
        }
        DBManager.getInstance().insertChemicals(chemicalList);
        Log.i("TAG", "基本化学表：" + DBManager.getInstance().queryChemicals().size());
        //复制整张基本化学表数据
        for (BaseChemical model : chemicalList) {
            NowChemical nowChemical = new NowChemical();
            nowChemical.setChemicalID(model.getChemicalID());
            nowChemical.setChemicalName(model.getChemicalName());
            nowChemical.setCabinetID(model.getCabinetID());
            nowChemical.setWeight(model.getWeight());
            nowChemical.setRFID(model.getRFID());
            nowChemical.setStatus(1);
            nowChemical.setCheDescription(model.getCheDescription());
            nowChemicalList.add(nowChemical);
        }
        DBManager.getInstance().insertNowChemicals(nowChemicalList);
        Log.i("TAG", "当前柜内数据：" + String.valueOf(DBManager.getInstance().queryNowChemicals().size()));

        //添加权限表中数据
        UserRoot userRoot = new UserRoot();
        userRoot.setUserID("001");
        userRoot.setUserName("钟铭");
        userRoot.setRoot(1);
        userList.add(userRoot);
        UserRoot userRoot1 = new UserRoot();
        userRoot1.setUserID("002");
        userRoot1.setUserName("王五");
        userRoot1.setRoot(0);
        userList.add(userRoot1);
        UserRoot userRoot2 = new UserRoot();
        userRoot2.setUserID("003");
        userRoot2.setUserName("张三");
        userRoot2.setRoot(1);
        userList.add(userRoot2);
        DBManager.getInstance().insertUser(userList);
        Log.i("TAG", "权限表中数据：" + DBManager.getInstance().queryUser().size());
    }

}
