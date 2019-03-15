package com.example.administrator.dangerouscabinetservice.adpter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.dangerouscabinetservice.R;
import com.example.administrator.dangerouscabinetservice.item.GoodsItem;

import java.util.List;

/**
 * Author: create by ZhongMing
 * Time: 2019/3/14 0014 10:52
 * Description:
 */
public class GoodsAdapter extends BaseQuickAdapter<GoodsItem, BaseViewHolder> {
    private Context context;

    public GoodsAdapter(Context context, int layoutResId, @Nullable List<GoodsItem> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsItem item) {
        helper.setText(R.id.goods_name, item.getName());
        Glide.with(context).load(item.getMip()).into((ImageView) helper.getView(R.id.goods_image));
    }
}
