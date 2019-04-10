package com.example.administrator.dangerouscabinetservice.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dangerouscabinetservice.MyApp;
import com.example.administrator.dangerouscabinetservice.R;
import com.example.administrator.dangerouscabinetservice.ui.BaseActivity;
import com.example.administrator.dangerouscabinetservice.ui.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: create by ZhongMing
 * Time: 2019/4/4 0004 10:02
 * Description:
 */
public class ChooseActivity extends BaseActivity {
    @BindView(R.id.user1)
    TextView user1;
    @BindView(R.id.user2)
    TextView user2;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.btn_no)
    Button btnNo;
    @BindView(R.id.btn_chanel)
    Button btnChanel;

    @Override
    protected int initLayout() {
        return R.layout.choose_layout;
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        user1.setText(MyApp.getUserList().get(0).getUserName());
        user2.setText(MyApp.getUserList().get(1).getUserName());
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.btn_sure, R.id.btn_no, R.id.btn_chanel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sure:
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                Toast.makeText(this, "开门成功", Toast.LENGTH_SHORT).show();
                startActivity(GoodsManagerActivity.class, bundle);
                break;
            case R.id.btn_no:
                startActivity(WeightActivity.class);
                break;
            case R.id.btn_chanel:
                startActivity(MainActivity.class);
                MyApp.setEmptyTempUserList();
                break;
        }
    }
}
