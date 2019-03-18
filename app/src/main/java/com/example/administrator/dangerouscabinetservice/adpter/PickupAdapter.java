package com.example.administrator.dangerouscabinetservice.adpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.dangerouscabinetservice.R;
import com.example.administrator.dangerouscabinetservice.item.GoodsUseDetailItem;

import java.util.List;

/**
 * Author: create by ZhongMing
 * Time: 2019/3/18 0018 09:26
 * Description:
 */
public class PickupAdapter extends BaseQuickAdapter<GoodsUseDetailItem, BaseViewHolder> {

    public PickupAdapter(int layoutResId, @Nullable List<GoodsUseDetailItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsUseDetailItem item) {
        helper.setText(R.id.pickup_id, item.getGoodsId());
        helper.setText(R.id.pickup_name, item.getGoodsName());
        helper.setText(R.id.pickup_weight, item.getGoodsWeight());
        helper.setText(R.id.pickup_time, item.getGoodsTime());
    }
}
