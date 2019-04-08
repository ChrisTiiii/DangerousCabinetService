package com.example.administrator.dangerouscabinetservice.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Author: create by ZhongMing
 * Time: 2019/4/3 0003 09:55
 * Description:化学品柜台账记录表 只有批量插入操作与 删除表内数据操作
 */
@Table(database = MyDatabase.class)
public class RecordTable extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    private int id;
    @Column
    private String RFID;//RFID唯一编码
    @Column
    private String chemicalID;//化学品ID
    @Column
    private String offRFID;//取出
    @Column
    private String inRFID;//存入
    @Column
    private String weight;//当前重量 g
    @Column
    private String first_userId;//用户1
    @Column
    private String second_userId;//用户1
    @Column
    private String time;//操作时间
    @Column
    private String cabinetID;//柜体ID
    @Column
    private int status;//状态 -1 空 1在柜内 2被取出

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRFID() {
        return RFID;
    }

    public void setRFID(String RFID) {
        this.RFID = RFID;
    }

    public String getChemicalID() {
        return chemicalID;
    }

    public void setChemicalID(String chemicalID) {
        this.chemicalID = chemicalID;
    }

    public String getOffRFID() {
        return offRFID;
    }

    public void setOffRFID(String offRFID) {
        this.offRFID = offRFID;
    }

    public String getInRFID() {
        return inRFID;
    }

    public void setInRFID(String inRFID) {
        this.inRFID = inRFID;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFirst_userId() {
        return first_userId;
    }

    public void setFirst_userId(String first_userId) {
        this.first_userId = first_userId;
    }

    public String getSecond_userId() {
        return second_userId;
    }

    public void setSecond_userId(String second_userId) {
        this.second_userId = second_userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCabinetID() {
        return cabinetID;
    }

    public void setCabinetID(String cabinetID) {
        this.cabinetID = cabinetID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
