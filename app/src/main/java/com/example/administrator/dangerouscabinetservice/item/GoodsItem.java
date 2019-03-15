package com.example.administrator.dangerouscabinetservice.item;

/**
 * Author: create by ZhongMing
 * Time: 2019/3/14 0014 09:55
 * Description:危化品实体类
 */
public class GoodsItem {
    private String name;
    private int mip;

    public GoodsItem(String name, int mip) {
        this.name = name;
        this.mip = mip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMip() {
        return mip;
    }

    public void setMip(int mip) {
        this.mip = mip;
    }
}
