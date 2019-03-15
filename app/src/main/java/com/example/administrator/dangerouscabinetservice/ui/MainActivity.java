package com.example.administrator.dangerouscabinetservice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.administrator.dangerouscabinetservice.R;
import com.example.administrator.dangerouscabinetservice.ui.activity.GoodsActivity;
import com.example.administrator.dangerouscabinetservice.widget.time.TimeView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: create by ZhongMing
 * Time: 2019/3/15 0015 15:30
 * Description:
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.time_view)
    TimeView timeView;
    @BindView(R.id.search)
    Button search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        long time = System.currentTimeMillis();
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        int mHour = mCalendar.get(Calendar.HOUR);
        int mMinuts = mCalendar.get(Calendar.MINUTE);
        int mSeconds = mCalendar.get(Calendar.SECOND);
        timeView.setTime(mHour, mMinuts, mSeconds);
        timeView.start();
    }

    @OnClick(R.id.search)
    public void onViewClicked() {
        Intent intent = new Intent(MainActivity.this, GoodsActivity.class);
        startActivity(intent);
    }
}