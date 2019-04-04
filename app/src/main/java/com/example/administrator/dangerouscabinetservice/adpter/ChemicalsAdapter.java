package com.example.administrator.dangerouscabinetservice.adpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.dangerouscabinetservice.R;
import com.example.administrator.dangerouscabinetservice.db.NowChemical;

import java.util.List;

/**
 * Author: create by ZhongMing
 * Time: 2019/3/14 0014 10:52
 * Description:
 */
public class ChemicalsAdapter extends BaseQuickAdapter<NowChemical, BaseViewHolder> {
    public ChemicalsAdapter(int layoutResId, @Nullable List<NowChemical> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NowChemical item) {
        helper.setText(R.id.chemical_name, item.getChemicalName());
        helper.setText(R.id.chemical_id, item.getChemicalID());
        helper.setText(R.id.chemical_weight, item.getWeight() + "克/g");
        helper.setText(R.id.tv_rfid, item.getRFID());
    }

}
