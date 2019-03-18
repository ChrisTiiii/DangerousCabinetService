package com.example.administrator.dangerouscabinetservice.item;

/**
 * Author: create by ZhongMing
 * Time: 2019/3/18 0018 09:27
 * Description:用户使用goods 详情实体类
 */
public class GoodsUseDetailItem {
    private String goodsId;
    private String goodsName;
    private String goodsWeight;
    private String goodsTime;

    public GoodsUseDetailItem(String goodsId, String goodsName, String goodsWeight, String goodsTime) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsWeight = goodsWeight;
        this.goodsTime = goodsTime;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(String goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public String getGoodsTime() {
        return goodsTime;
    }

    public void setGoodsTime(String goodsTime) {
        this.goodsTime = goodsTime;
    }
}
