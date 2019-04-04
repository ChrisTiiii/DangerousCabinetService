package com.example.administrator.dangerouscabinetservice.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.dangerouscabinetservice.R;
import com.example.administrator.dangerouscabinetservice.db.NowChemical;

import cn.carbs.android.library.MDDialog;

/**
 * Author: create by ZhongMing
 * Time: 2019/3/19 0019 14:14
 * Description:
 */
public class DialogUtil {
    public static volatile DialogUtil instance;
    private TextView tvName, tvID, tvNum, tvDes, tvRfid;
    private NowChemical nowChemical;

    public static synchronized DialogUtil getInstance() {
        if (instance == null) {
            synchronized (DialogUtil.class) {
                instance = new DialogUtil();
            }
        }
        return instance;
    }

    public NowChemical getNowChemical() {
        return nowChemical;
    }

    public void setNowChemical(NowChemical nowChemical) {
        this.nowChemical = nowChemical;
    }

    public void showDialog(final Context context, int type) {
        switch (type) {
            case 1://自定义
                new MDDialog.Builder(context)
                        .setContentView(R.layout.goods_dialog)
                        .setContentViewOperator(new MDDialog.ContentViewOperator() {
                            @Override
                            public void operate(View contentView) {//这里的contentView就是上面代码中传入的自定义的View或者layout资源inflate出来的view
                                tvName = contentView.findViewById(R.id.tv_name);
                                tvID = contentView.findViewById(R.id.tv_bh);
                                tvNum = contentView.findViewById(R.id.tv_num);
                                tvDes = contentView.findViewById(R.id.tv_desciption);
                                tvRfid = contentView.findViewById(R.id.tv_rfid);
                                if (getNowChemical() != null) {
                                    tvRfid.setText(nowChemical.getRFID());
                                    tvName.setText(nowChemical.getChemicalName());
                                    tvID.setText(nowChemical.getChemicalID());
                                    tvNum.setText(nowChemical.getWeight());
                                    tvDes.setText(nowChemical.getCheDescription());
                                }

                            }
                        })
                        .setPrimaryTextColor(R.color.yellow)
                        .setTitle("柜内详情")
                        .setShowPositiveButton(true)
                        .setShowNegativeButton(false)
                        .setPositiveButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        })
                        .setWidthMaxDp(600)
                        .create()
                        .show();
                break;

            case 2:

                break;
        }

    }


}
