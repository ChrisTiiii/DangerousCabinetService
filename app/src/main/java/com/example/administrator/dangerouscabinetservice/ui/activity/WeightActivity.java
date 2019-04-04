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
 * Time: 2019/4/4 0004 10:19
 * Description:
 */
public class WeightActivity extends BaseActivity {
    @BindView(R.id.tv_now_weight)
    TextView tvNowWeight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_last_weight)
    TextView tvLastWeight;
    @BindView(R.id.btn_open_door)
    Button btnOpenDoor;
    @BindView(R.id.back)
    Button back;

    @Override
    protected int initLayout() {
        return R.layout.weight_layout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.btn_open_door, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open_door:
                Toast.makeText(this, "开门成功", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putInt("type", 2);
                startActivity(GoodsManagerActivity.class,bundle);
                break;
            case R.id.back:
                startActivity(MainActivity.class);
                MyApp.setEmptyUserList();
                break;
        }
    }
}
