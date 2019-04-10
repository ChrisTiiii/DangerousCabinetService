package com.example.administrator.dangerouscabinetservice.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.dangerouscabinetservice.R;
import com.example.administrator.dangerouscabinetservice.adpter.ChemicalsAdapter;
import com.example.administrator.dangerouscabinetservice.db.DBManager;
import com.example.administrator.dangerouscabinetservice.db.NowChemical;
import com.example.administrator.dangerouscabinetservice.ui.BaseActivity;
import com.example.administrator.dangerouscabinetservice.ui.MainActivity;
import com.example.administrator.dangerouscabinetservice.utils.DialogUtil;
import com.example.administrator.dangerouscabinetservice.widget.search.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 化学品查询界面
 */
public class GoodsActivity extends BaseActivity {

    @BindView(R.id.search_view)
    MaterialSearchView searchView;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.show_all)
    TextView showAll;
    ChemicalsAdapter adapter;
    List<String> listName;
    List<NowChemical> listChemical;
    @BindView(R.id.img_back)
    ImageView imgBack;

    @Override
    protected int initLayout() {
        return R.layout.search_main;
    }

    /**
     * 初始化数据
     */
    protected void initData() {
        listChemical = new ArrayList<>();
        listName = new ArrayList<>();
        setData();
        adapter = new ChemicalsAdapter(R.layout.goods_item, listChemical);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerview.setAdapter(adapter);
        searchView.setVoiceSearch(false); //or true,是否支持声音的
        searchView.setSubmitOnClick(true);  //设置为true后，单击ListView的条目，searchView隐藏。实现数据的搜索
        searchView.setEllipsize(true);   //搜索框的ListView中的Item条目是否是单显示
        for (NowChemical chemical : listChemical) {
            listName.add(chemical.getChemicalName());
        }
        String[] array = listName.toArray(new String[listName.size()]);
        searchView.setSuggestions(array);
    }

    @Override
    protected void initView() {

    }


    /**
     * 设置回调事件
     */
    protected void initEvent() {
        adapter.openLoadAnimation();//打开加载动画
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DialogUtil.getInstance().setNowChemical(listChemical.get(position));
                DialogUtil.getInstance().showDialog(GoodsActivity.this, 1);
            }
        });
        //数据的监听（在自定义类中已经做了些处理）
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(GoodsActivity.this, "你要搜索的是：" + query, Toast.LENGTH_SHORT).show();
                listChemical.clear();
                listChemical.addAll(DBManager.getInstance().likeNowChemicals(query));
                adapter.notifyDataSetChanged();
                return false;
            }

            //文本内容发生改变时
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }


    @OnClick({R.id.img_search, R.id.show_all, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_search:
                if (searchView.isSearchOpen()) {
                    searchView.closeSearch();//隐藏搜索框
                } else {
                    searchView.showSearch(true);//显示搜索框
                }
                break;
            case R.id.show_all:
                listChemical.clear();
                setData();
                System.out.println("list size:" + listChemical.size());
                adapter.notifyDataSetChanged();
                break;
            case R.id.img_back:
                startActivity(MainActivity.class);
                break;
        }
    }

    /**
     * 设置假数据
     */
    public void setData() {
        listChemical.addAll(DBManager.getInstance().queryNowChemicals());
    }


    /**
     * 监听回退事件
     */
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

}
