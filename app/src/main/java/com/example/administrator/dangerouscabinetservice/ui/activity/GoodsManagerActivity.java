package com.example.administrator.dangerouscabinetservice.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.dangerouscabinetservice.MyApp;
import com.example.administrator.dangerouscabinetservice.R;
import com.example.administrator.dangerouscabinetservice.adpter.BackAdapter;
import com.example.administrator.dangerouscabinetservice.adpter.PickupAdapter;
import com.example.administrator.dangerouscabinetservice.db.DBManager;
import com.example.administrator.dangerouscabinetservice.db.NowChemical;
import com.example.administrator.dangerouscabinetservice.db.UserRoot;
import com.example.administrator.dangerouscabinetservice.item.GoodsUseDetailItem;
import com.example.administrator.dangerouscabinetservice.ui.BaseActivity;
import com.example.administrator.dangerouscabinetservice.ui.MainActivity;

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
    @BindView(R.id.choose_up)
    LinearLayout chooseUp;
    @BindView(R.id.chosoe_down)
    LinearLayout chosoeDown;
    private PickupAdapter pcikAdapter;
    private BackAdapter backAdapter;
    private List<GoodsUseDetailItem> list;
    private List<GoodsUseDetailItem> emptyList;
    private List<NowChemical> chemicalList;//取出使用假数据
    private List<NowChemical> nowList;//页面展示
    static int upPosition = -1;
    static int downPosition = -1;
    private int type = -1;//1为取出 2为归还
    private String username = "";
    private LinearLayoutManager linearLayoutManagerUp;
    private LinearLayoutManager linearLayoutManagerDown;
    private PagerSnapHelper snapHelperUp;
    private PagerSnapHelper snapHelperDown;

    @Override
    protected int initLayout() {
        return R.layout.goods_manage_layout;
    }

    protected void initData() {
        list = new ArrayList<>();
        emptyList = new ArrayList<>();
        chemicalList = new ArrayList<>();
        nowList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("type");
        for (UserRoot userRoot : MyApp.getUserList()) {
            username += userRoot.getUserName() + ",";
        }
        makeData();

    }

    private void makeData() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        emptyList.add(new GoodsUseDetailItem());
        switch (type) {
            case 1:
                chemicalList = DBManager.getInstance().queryNowChemicals();
                for (int i = 0; i < 3; i++) {
                    nowList.add(chemicalList.get((int) (0 + Math.random() * (chemicalList.size() - 1) - 0 + 1)));
                }
                for (NowChemical nowChemical : nowList) {
                    list.add(new GoodsUseDetailItem(nowChemical.getRFID(), nowChemical.getChemicalName(), nowChemical.getWeight(), df.format(new Date())));
                }
                break;
            case 2:
                for (String rfid : MyApp.getOffList()) {
                    nowList.add(DBManager.getInstance().queryNowChemicalsByRFID(rfid));
                }
                for (NowChemical nowChemical : nowList) {
                    list.add(new GoodsUseDetailItem(nowChemical.getRFID(), nowChemical.getChemicalName(), nowChemical.getWeight(), df.format(new Date())));
                }
                break;
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
        switch (type) {
            case 1:
                chooseUp.setVisibility(View.VISIBLE);
                chosoeDown.setVisibility(View.GONE);
                tvTitle.setText(username + "您取出的盘点明细如下：");
                pageNumUp.setText(1 + "/ " + list.size());
                pcikAdapter = new PickupAdapter(R.layout.pickup_item, list);
                linearLayoutManagerUp = new LinearLayoutManager(this);
                linearLayoutManagerUp.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerUp.setLayoutManager(linearLayoutManagerUp);
                pcikAdapter.openLoadAnimation();
                recyclerUp.setAdapter(pcikAdapter);
                snapHelperUp = new PagerSnapHelper();
                snapHelperUp.attachToRecyclerView(recyclerUp);//只滑动一个item

                backAdapter = new BackAdapter(R.layout.back_goods_item, emptyList);
                linearLayoutManagerDown = new LinearLayoutManager(this);
                linearLayoutManagerDown.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerDown.setLayoutManager(linearLayoutManagerDown);
                backAdapter.openLoadAnimation();
                recyclerDown.setAdapter(backAdapter);
                snapHelperDown = new PagerSnapHelper();
                snapHelperDown.attachToRecyclerView(recyclerDown);
                break;
            case 2:
                chooseUp.setVisibility(View.GONE);
                chosoeDown.setVisibility(View.VISIBLE);
                tvTitle.setText(username + "您归还的盘点明细如下：");
                pageNumDown.setText(1 + "/ " + list.size());
                backAdapter = new BackAdapter(R.layout.back_goods_item, list);
                linearLayoutManagerDown = new LinearLayoutManager(this);
                linearLayoutManagerDown.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerDown.setLayoutManager(linearLayoutManagerDown);
                backAdapter.openLoadAnimation();
                recyclerDown.setAdapter(backAdapter);
                snapHelperDown = new PagerSnapHelper();
                snapHelperDown.attachToRecyclerView(recyclerDown);

                pcikAdapter = new PickupAdapter(R.layout.pickup_item, emptyList);
                linearLayoutManagerUp = new LinearLayoutManager(this);
                linearLayoutManagerUp.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerUp.setLayoutManager(linearLayoutManagerUp);
                pcikAdapter.openLoadAnimation();
                recyclerUp.setAdapter(pcikAdapter);
                snapHelperUp = new PagerSnapHelper();
                snapHelperUp.attachToRecyclerView(recyclerUp);//只滑动一个item
                break;
        }

    }

    @OnClick({R.id.btn_sure, R.id.btn_pandian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sure:
                switch (type) {
                    case 1:
                        List<String> rfid = new ArrayList<>();
                        for (NowChemical now : nowList) {
                            DBManager.getInstance().updateOffChemical(now.getRFID(), 2);
                            rfid.add(now.getRFID());
                        }
                        MyApp.setOffList(rfid);
                        break;
                    case 2:
                        for (NowChemical now : nowList)
                            DBManager.getInstance().updateInChemical(now.getRFID(), 1, "1000000");
                        break;
                }

                startActivity(MainActivity.class);
                MyApp.setEmptyTempUserList();
                break;
            case R.id.btn_pandian:
                break;
        }
    }

}
