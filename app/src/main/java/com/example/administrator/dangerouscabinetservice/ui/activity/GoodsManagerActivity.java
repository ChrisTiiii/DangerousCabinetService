package com.example.administrator.dangerouscabinetservice.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.dangerouscabinetservice.MyApp;
import com.example.administrator.dangerouscabinetservice.R;
import com.example.administrator.dangerouscabinetservice.adpter.BackAdapter;
import com.example.administrator.dangerouscabinetservice.adpter.PickupAdapter;
import com.example.administrator.dangerouscabinetservice.db.UserRoot;
import com.example.administrator.dangerouscabinetservice.item.GoodsUseDetailItem;
import com.example.administrator.dangerouscabinetservice.ui.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: create by ZhongMing
 * Time: 2019/3/18 0018 09:20
 * Description: 物品取出 归还 管理确认界面
 */
public class GoodsManagerActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler_up)
    RecyclerView recyclerUp;
    @BindView(R.id.recycler_down)
    RecyclerView recyclerDown;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.btn_pandian)
    Button btnPandian;
    @BindView(R.id.page_num_up)
    TextView pageNumUp;
    @BindView(R.id.page_num_down)
    TextView pageNumDown;
    private PickupAdapter pcikAdapter;
    private BackAdapter backAdapter;
    private List<GoodsUseDetailItem> list;
    String TAG = "Manager";
    static int upPosition = -1;
    static int downPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.goods_manage;
    }

    protected void initData() {
        String username = "";
        for (UserRoot userRoot : MyApp.getUserList()) {
            username += userRoot.getUserName() + ",";
        }
        tvTitle.setText(username + "您归还的盘点明细如下：");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GoodsUseDetailItem goods = new GoodsUseDetailItem("00" + i, "这是第" + i + "个化学品", String.valueOf(i * 16), df.format(new Date()));
            list.add(goods);
        }
    }

    protected void initEvent() {
        recyclerUp.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                upPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                Log.i(TAG, "onScrolled: -----------" + upPosition);
                pageNumUp.setText(upPosition + 1 + "/ " + list.size());
            }
        });
        recyclerDown.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                downPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                Log.i(TAG, "onScrolled: -----------" + downPosition);
                pageNumDown.setText(downPosition + 1 + "/ " + list.size());
            }
        });
    }

    @Override
    protected void initView() {
        pcikAdapter = new PickupAdapter(R.layout.pickup_item, list);
        backAdapter = new BackAdapter(R.layout.back_goods_item, list);
        LinearLayoutManager linearLayoutManagerUp = new LinearLayoutManager(this);
        linearLayoutManagerUp.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerUp.setLayoutManager(linearLayoutManagerUp);
        LinearLayoutManager linearLayoutManagerDown = new LinearLayoutManager(this);
        linearLayoutManagerDown.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerDown.setLayoutManager(linearLayoutManagerDown);
        pcikAdapter.openLoadAnimation();
        backAdapter.openLoadAnimation();
        recyclerUp.setAdapter(pcikAdapter);
        recyclerDown.setAdapter(backAdapter);
        PagerSnapHelper snapHelperUp = new PagerSnapHelper();
        snapHelperUp.attachToRecyclerView(recyclerUp);//只滑动一个item
        PagerSnapHelper snapHelperDown = new PagerSnapHelper();
        snapHelperDown.attachToRecyclerView(recyclerDown);
    }

    @OnClick({R.id.btn_sure, R.id.btn_pandian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sure:
                break;
            case R.id.btn_pandian:
                break;
        }
    }
}
