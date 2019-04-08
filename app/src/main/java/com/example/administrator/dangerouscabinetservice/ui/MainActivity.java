package com.example.administrator.dangerouscabinetservice.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dangerouscabinetservice.MyApp;
import com.example.administrator.dangerouscabinetservice.R;
import com.example.administrator.dangerouscabinetservice.db.BaseChemical;
import com.example.administrator.dangerouscabinetservice.db.DBManager;
import com.example.administrator.dangerouscabinetservice.db.NowChemical;
import com.example.administrator.dangerouscabinetservice.db.UserRoot;
import com.example.administrator.dangerouscabinetservice.ui.activity.ChooseActivity;
import com.example.administrator.dangerouscabinetservice.ui.activity.GoodsActivity;
import com.example.administrator.dangerouscabinetservice.ui.activity.GoodsManagerActivity;
import com.example.administrator.dangerouscabinetservice.utils.ActivityUtil;
import com.example.administrator.dangerouscabinetservice.widget.thermometer.HumidityView;
import com.example.administrator.dangerouscabinetservice.widget.thermometer.ThermometerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: create by ZhongMing
 * Time: 2019/3/15 0015 15:30
 * Description:
 */
public class MainActivity extends BaseActivity {
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
    @BindView(R.id.btn_shuaka)
    Button btnShuaka;
    @BindView(R.id.text_temp)
    TextView textTemp;
    @BindView(R.id.text_humid)
    TextView textHumid;
    @BindView(R.id.tv_temp)
    ThermometerView tvTemp;
    @BindView(R.id.tv_humidity)
    HumidityView tvHumidity;
    // 设置返回按钮的监听事件
    private long exitTime = 0;

    private Handler handler;
    private static final int TIMER = 0x12334;

    private List<UserRoot> userList;//用户权限
    private List<UserRoot> tempUserList;//验证临时用户权限
    private List<BaseChemical> chemicalList;//基本化学库
    private List<NowChemical> nowChemicalList;//当前化学柜内数据

    @Override

    protected int initLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {
        autoChange();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    //去执行定时操作逻辑
                    case TIMER:
                        float temp = getRandomTemp();
                        textTemp.setText(String.valueOf(temp) + "℃");
                        tvTemp.setValueAndStartAnim(temp);
                        float humidty = getRandomHumidy();
                        textHumid.setText(String.valueOf(humidty) + "RH%");
                        tvHumidity.setValueAndStartAnim(humidty);
                        break;
                    default:
                        break;
                }
            }
        };

    }

    @Override
    protected void initData() {
        chemicalList = new ArrayList<>();
        userList = new ArrayList<>();
        tempUserList = new ArrayList<>();
        nowChemicalList = new ArrayList<>();
        DBManager.getInstance().deleteTable(10);//删除化学表
        DBManager.getInstance().deleteTable(20);//删除用户表
        DBManager.getInstance().deleteTable(30);//删除柜内数据表
        DBManager.getInstance().deleteTable(40);//删除台账数据表
        makeData();
    }

    public void autoChange() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2000);
                        handler.sendEmptyMessage(TIMER);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private float getRandomTemp() {
        float value = (int) (0 + Math.random() * 70 - 0 + 1);
        Log.i("tempControl", "current value: " + value);
        return value;
    }

    private float getRandomHumidy() {
        float value = (int) (0 + Math.random() * (100 - 0 + 1));
        Log.i("HumidyControl", "current value: " + value);
        return value;
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
        Log.i(TAG, "基本化学表：" + DBManager.getInstance().queryChemicals().size());
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
        Log.i(TAG, "当前柜内数据：" + String.valueOf(DBManager.getInstance().queryNowChemicals().size()));

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
        Log.i(TAG, "权限表中数据：" + DBManager.getInstance().queryUser().size());
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
    }


    // 处理网络状态结果
    @Override
    public void onNetChange(boolean netWorkState) {
        super.onNetChange(netWorkState);
        Log.i(TAG, netWorkState ? "当前网络可用" : "当前无网络");
        Toast.makeText(this, netWorkState ? "当前网络可用" : "当前无网络", Toast.LENGTH_SHORT).show();
    }


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

    @OnClick({R.id.search, R.id.btn_use, R.id.btn_shuaka})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search:
                startActivity(GoodsActivity.class);
                break;
            case R.id.btn_shuaka:
                setUser(1);
                break;
            case R.id.btn_use:
                setUser(2);
                break;
        }
    }

    public void setUser(int type) {
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
                switch (type) {
                    case 1:
                        startActivity(ChooseActivity.class);
                        break;
                    case 2:
                        startActivity(GoodsManagerActivity.class);
                        break;
                }
                System.out.println("tempUserList size:" + tempUserList.size());
            } else
                Toast.makeText(this, "您没有权限", Toast.LENGTH_SHORT).show();
        }
    }

}
