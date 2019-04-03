package com.example.administrator.dangerouscabinetservice.db;


import android.support.annotation.NonNull;
import android.util.Log;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;
import java.util.Random;

/**
 * Author: create by ZhongMing
 * Time: 2019/4/3 0003 11:12
 * Description:此类做数据库操作
 */
public class DBManager {
    public static volatile DBManager instance;
    private static final String TAG = "DBManager";

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null)
                    instance = new DBManager();
            }
        }
        return instance;
    }


    /**
     * 删除数据库表结构
     */
    public void deleteTable(int type) {
        switch (type) {
            case 10:
                //删除整张化学基础表
                Delete.table(BaseChemical.class);
                break;
            case 20:
                //删除整张用户权限表
                Delete.table(UserRoot.class);
                break;
            case 30:
                //删除整张当前柜内数据表
                Delete.table(NowChemical.class);
                break;
            case 40://删除台账全部数据表（主要删除这张表数据）
                Delete.table(RecordTable.class);
                break;
            default:
                Log.i(TAG, "代码错误,删除表失败");
        }

    }

    /**
     * 取出化学品
     *
     * @param RFID
     * @param status
     */
    public void offChemical(String RFID, int status) {
        SQLite.update(NowChemical.class)
                .set(NowChemical_Table.status.eq(status))
                .where(NowChemical_Table.RFID.is(RFID))
                .async()
                .execute();
    }

    /**
     * 归还化学品
     *
     * @param RFID
     * @param status
     * @param weight
     */
    public void inChemical(String RFID, int status, String weight) {
        SQLite.update(NowChemical.class)
                .set(NowChemical_Table.status.eq(status),
                        NowChemical_Table.remain.eq(weight))
                .where(NowChemical_Table.RFID.is(RFID))
                .async()
                .execute();
    }


    /**
     * 查询化学基础表全部数据
     *
     * @return
     */
    public List<BaseChemical> queryChemicals() {
        List<BaseChemical> list = SQLite.select().from(BaseChemical.class).queryList();
        return list;
    }

    /**
     * 查询当前柜内数据
     *
     * @return
     */
    public List<NowChemical> queryNowChemicals() {
        List<NowChemical> list = SQLite.select().from(NowChemical.class).queryList();
        return list;
    }


    /**
     * 查询台账记录表全部数据
     *
     * @return
     */
    public List<RecordTable> queryRecords() {
        List<RecordTable> list = SQLite.select().from(RecordTable.class).queryList();
        return list;
    }

    /**
     * 查询用户表全部数据
     *
     * @return
     */
    public List<UserRoot> queryUser() {
        List<UserRoot> list = SQLite.select().from(UserRoot.class).queryList();
        return list;
    }


    /**
     * 判断用户是否有权限
     *
     * @param userId
     * @return true 有权限开门
     * false 无权限
     */
    public boolean isRoot(String userId) {
        boolean bolRoot = false;
        //根据条件查询
        List<UserRoot> model = SQLite.select()
                .from(UserRoot.class)
                .where(UserRoot_Table.userID.is(userId))
                .queryList();
        for (UserRoot userRoot : model) {
            if (userRoot.getRoot() == 1)
                bolRoot = true;
        }
        return bolRoot;
    }

    public void insertUserRoot(UserRoot userRoot) {
        userRoot.insert();
    }

    /**
     * 事务批量操作新增基础化学库
     */
    public void insertChemicals(final List<BaseChemical> list) {
        FlowManager.getDatabase(MyDatabase.class).beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                for (BaseChemical model : list) {
                    model.save(databaseWrapper);
                }
            }
        }).success(new Transaction.Success() {
            @Override
            public void onSuccess(@NonNull Transaction transaction) {
                Log.i(TAG, "on success");
            }
        }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                Log.i(TAG, error.getMessage());
            }
        }).build().execute();
    }


    /**
     * 事务批量操作新增台账操作
     */
    public void insertRecord(final List<RecordTable> list) {
        FlowManager.getDatabase(MyDatabase.class).beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                for (RecordTable model : list) {
                    model.save(databaseWrapper);
                }
            }
        }).success(new Transaction.Success() {
            @Override
            public void onSuccess(@NonNull Transaction transaction) {
                Log.i(TAG, "on success");
            }
        }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                Log.i(TAG, error.getMessage());
            }
        }).build().execute();
    }

    /**
     * 事务批量操作新增用户权限表
     */
    public void insertUser(final List<UserRoot> list) {
        FlowManager.getDatabase(MyDatabase.class).beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                for (UserRoot model : list) {
                    model.save(databaseWrapper);
                }
            }
        }).success(new Transaction.Success() {
            @Override
            public void onSuccess(@NonNull Transaction transaction) {
                Log.i(TAG, "on success");
            }
        }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                Log.i(TAG, error.getMessage());
            }
        }).build().execute();
    }

}
