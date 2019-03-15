package com.example.administrator.dangerouscabinetservice.net;

import android.util.Log;
import com.example.administrator.dangerouscabinetservice.utils.WriteUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Author: create by ZhongMing
 * Time: 2019/3/15 0015 09:22
 * Description:实现网络接口
 */
public class NetImp {
    private Retrofit retrofit;
    private NetAPI netAPI;
    private static final String BASE_URL = "https://slyj.slicity.com";
    private OkHttpClient client;
    private String TAG = "NET WORK:";
    private volatile static NetImp instance;

    public static synchronized NetImp getInstance() {
        if (instance == null) {
            synchronized (NetImp.class) {
                instance = new NetImp();
            }
        }
        return instance;
    }

    /**
     * 初始化retrofit netApi
     *
     * @param
     */
    public NetImp() {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        client = httpBuilder.readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS) //设置超时
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)//基础URL 建议以 / 结尾
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//RxJava 适配器
                .client(client)
                .build();
        netAPI = retrofit.create(NetAPI.class);
    }


    public void downLoad() {
        netAPI.downloadFileWithFixedUrl().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "server contacted and has file");
                    boolean writtenToDisk = WriteUtil.getInstance().writeResponseBodyToDisk(response.body());
                    Log.d(TAG, "file download was a success? " + writtenToDisk);
                } else {
                    Log.d(TAG, "server contact failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    public void downlaodVideo() {
        netAPI.downloadFileWithDynamicUrlSync("http://10.101.80.113:8080/RootFile/Platform/20181114/1542178640266.mp4").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "server contacted and has file");
                            boolean writtenToDisk = WriteUtil.getInstance().writeResponseBodyToDisk(response.body());
                            Log.d(TAG, "file download was a success? " + writtenToDisk);
                        } else {
                            Log.d(TAG, "server contact failed");
                        }
                    }
                }).start();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

}
