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
import com.example.administrator.dangerouscabinetservice.db.DBManager;
import com.example.administrator.dangerouscabinetservice.db.UserRoot;
import com.example.administrator.dangerouscabinetservice.ui.activity.ChooseActivity;
import com.example.administrator.dangerouscabinetservice.ui.activity.GoodsActivity;
import com.example.administrator.dangerouscabinetservice.ui.activity.GoodsManagerActivity;
import com.example.administrator.dangerouscabinetservice.utils.ActivityUtil;
import com.example.administrator.dangerouscabinetservice.widget.thermometer.HumidityView;
import com.example.administrator.dangerouscabinetservice.widget.thermometer.ThermometerView;

import java.util.ArrayList;
import java.util.List;

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

    private List<UserRoot> tempUserList;//验证临时用户权限


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

        tempUserList = new ArrayList<>();

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
//        Log.i("tempControl", "current value: " + value);
        return value;
    }

    private float getRandomHumidy() {
        float value = (int) (0 + Math.random() * (100 - 0 + 1));
//        Log.i("HumidyControl", "current value: " + value);
        return value;
    }



    @Override
    protected void initEvent() {
        check1.setText(MyApp.getUserList().get(0).getUserName());
        check2.setText(MyApp.getUserList().get(1).getUserName());
        check3.setText(MyApp.getUserList().get(2).getUserName());

        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    tempUserList.add(MyApp.getUserList().get(0));
                } else {
                    tempUserList.remove(MyApp.getUserList().get(0));
                }
            }
        });
        check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    tempUserList.add(MyApp.getUserList().get(1));
                } else {
                    tempUserList.remove(MyApp.getUserList().get(1));
                }
            }
        });
        check3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    tempUserList.add(MyApp.getUserList().get(2));
                } else {
                    tempUserList.remove(MyApp.getUserList().get(2));
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

    @OnClick({R.id.search, R.id.btn_shuaka})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search:
                startActivity(GoodsActivity.class);
                break;
            case R.id.btn_shuaka:
                setUser(1);
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
                MyApp.setTempUserList(tempUserList);
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
