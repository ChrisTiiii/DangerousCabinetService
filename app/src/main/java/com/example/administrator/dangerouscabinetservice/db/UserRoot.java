package com.example.administrator.dangerouscabinetservice.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Author: create by ZhongMing
 * Time: 2019/4/3 0003 13:59
 * Description:用户权限表 此表用于条件查询 用户是否有权限开门
 */
@Table(database = MyDatabase.class)
public class UserRoot extends BaseModel implements Serializable {
    @Column
    @PrimaryKey(autoincrement = true)
    private int id;
    @Column
    private String userID;//用户ID
    @Column
    private String userName;//用户名
    @Column
    private int root;//用户权限 -1出错 0未开启 1开启

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }
}
