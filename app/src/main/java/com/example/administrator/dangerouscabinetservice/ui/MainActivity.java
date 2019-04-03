package com.example.administrator.dangerouscabinetservice.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.dangerouscabinetservice.MyApp;
import com.example.administrator.dangerouscabinetservice.R;
import com.example.administrator.dangerouscabinetservice.db.BaseChemical;
import com.example.administrator.dangerouscabinetservice.db.DBManager;
import com.example.administrator.dangerouscabinetservice.db.UserRoot;
import com.example.administrator.dangerouscabinetservice.ui.activity.GoodsActivity;
import com.example.administrator.dangerouscabinetservice.ui.activity.GoodsManagerActivity;
import com.example.administrator.dangerouscabinetservice.utils.ActivityUtil;
import com.example.administrator.dangerouscabinetservice.widget.time.TimeView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Author: create by ZhongMing
 * Time: 2019/3/15 0015 15:30
 * Description:
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.time_view)
    TimeView timeView;
    @BindView(R.id.search)
    Button search;
    @BindView(R.id.btn_use)
    Button btnUse;
    @BindView(R.id.check1)
    CheckBox check1;
    @BindView(R.id.check2)
    CheckBox check2;
    @BindView(R.id.check3)
    CheckBox check3;
    List<BaseChemical> chemicalList;
    private List<UserRoot> userList;
    private List<UserRoot> tempUserList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {
        long time = System.currentTimeMillis();
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        int mHour = mCalendar.get(Calendar.HOUR);
        int mMinuts = mCalendar.get(Calendar.MINUTE);
        int mSeconds = mCalendar.get(Calendar.SECOND);
        timeView.setTime(mHour, mMinuts, mSeconds);
        timeView.start();
    }

    @Override
    protected void initData() {
        DBManager.getInstance().deleteTable(10);//删除化学表
        DBManager.getInstance().deleteTable(20);//删除用户表
        chemicalList = new ArrayList<>();
        userList = new ArrayList<>();
        tempUserList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            BaseChemical model = new BaseChemical();
            model.setRFID("JFHE" + (i + 1));
            model.setChemicalID(String.valueOf(new Random().nextInt(100)));
            model.setChemicalName("测试" + i);
            model.setCabinetID("柜子" + i);
            model.setTotal(String.valueOf((i + 1) * 12));
            chemicalList.add(model);
        }
        DBManager.getInstance().insertChemicals(chemicalList);
        List<BaseChemical> list = DBManager.getInstance().queryChemicals();
        System.out.println(list.size());
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
        userRoot2.setUserName("奇迹");
        userRoot2.setRoot(1);
        userList.add(userRoot2);
        DBManager.getInstance().insertUser(userList);
        System.out.println("用户表内：" + DBManager.getInstance().queryUser().size());

    }

    @Override
    protected void initEvent() {
        check1.setText(userList.get(0).getUserName());
        check2.setText(userList.get(1).getUserName());
        check3.setText(userList.get(2).getUserName());

        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    tempUserList.add(userList.get(0));
                } else {
                    tempUserList.remove(userList.get(0));
                }
            }
        });
        check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    tempUserList.add(userList.get(1));
                } else {
                    tempUserList.remove(userList.get(1));
                }
            }
        });
        check3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    tempUserList.add(userList.get(2));
                } else {
                    tempUserList.remove(userList.get(2));
                }
            }
        });
//        // 判断权限
//        if (!hasPermission(Manifest.permission.READ_PHONE_STATE)) {
//            requestPermission(ConstantUtil.PERMISSIONS_REQUEST_READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE);
//        }
    }


    // 处理网络状态结果
    @Override
    public void onNetChange(boolean netWorkState) {
        super.onNetChange(netWorkState);
        Log.i(TAG, netWorkState ? "当前网络可用" : "当前无网络");
        Toast.makeText(this, netWorkState ? "当前网络可用" : "当前无网络", Toast.LENGTH_SHORT).show();
    }

    // 设置返回按钮的监听事件
    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 监听返回键，点击两次退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 5000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivityUtil.getInstance().exitSystem();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.search, R.id.btn_use})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search:
                startActivity(GoodsActivity.class);
                break;
            case R.id.btn_use:
                int canStart[] = {-1, -1, -1};
                if (tempUserList.size() > 1) {
                    for (int i = 0; i < tempUserList.size(); i++) {
                        if (DBManager.getInstance().isRoot(tempUserList.get(i).getUserID())) {
                            canStart[i] = 1;
                        } else {
                            canStart[i] = 0;
                            tempUserList.remove(i);
                        }
                    }
                    if (canStart[0] == 1 && canStart[1] == 1) {
                        MyApp.setUserList(tempUserList);
                        startActivity(GoodsManagerActivity.class);
                        System.out.println("tempUserList size:" + tempUserList.size());
                    } else
                        Toast.makeText(this, "您没有权限", Toast.LENGTH_SHORT).show();
                    break;
                }
        }
    }

}
