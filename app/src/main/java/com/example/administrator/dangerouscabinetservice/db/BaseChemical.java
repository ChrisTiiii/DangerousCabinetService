package com.example.administrator.dangerouscabinetservice.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Author: create by ZhongMing
 * Time: 2019/4/3 0003 09:49
 * Description:基本化学表 只有批量插入操作
 */
@Table(database = MyDatabase.class)
public class BaseChemical extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    private int id;
    @Column
    private String RFID;//RFID唯一编码
    @Column
    private String chemicalID;//化学品ID
    @Column
    private String chemicalName;//化学品名称
    @Column
    private String weight;//整体重量
    @Column
    private String cabinetID;//柜体ID
    @Column
    private String cheDescription;//化学品描述

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChemicalID() {
        return chemicalID;
    }

    public void setChemicalID(String chemicalID) {
        this.chemicalID = chemicalID;
    }

    public String getChemicalName() {
        return chemicalName;
    }

    public void setChemicalName(String chemicalName) {
        this.chemicalName = chemicalName;
    }

    public String getRFID() {
        return RFID;
    }

    public void setRFID(String RFID) {
        this.RFID = RFID;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCabinetID() {
        return cabinetID;
    }

    public void setCabinetID(String cabinetID) {
        this.cabinetID = cabinetID;
    }

    public String getCheDescription() {
        return cheDescription;
    }

    public void setCheDescription(String cheDescription) {
        this.cheDescription = cheDescription;
    }
}
