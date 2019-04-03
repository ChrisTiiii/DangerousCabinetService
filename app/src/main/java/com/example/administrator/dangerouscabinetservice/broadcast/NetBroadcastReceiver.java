package com.example.administrator.dangerouscabinetservice.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.example.administrator.dangerouscabinetservice.ui.BaseActivity;
import com.example.administrator.dangerouscabinetservice.utils.NetworkUtil;

/**
 * Author: create by ZhongMing
 * Time: 2019/4/3 0003 16:28
 * Description:检查网络状态切换 - 广播接受器
 */
public class NetBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果相等的话就说明网络状态发生了变化
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            boolean netWorkState = NetworkUtil.isNetworkConnected(context);
            // 接口回调传过去状态的类型
            if (BaseActivity.netEvent != null)
                BaseActivity.netEvent.onNetChange(netWorkState);
//            if (BaseFragment.netEvent != null)
//                BaseFragment.netEvent.onNetChange(netWorkState);
//            if (BaseFragmentActivity.netEvent != null)
//                BaseFragmentActivity.netEvent.onNetChange(netWorkState);
        }
    }

    // 网络状态变化接口
    public interface NetChangeListener {
        void onNetChange(boolean netWorkState);
    }
}
