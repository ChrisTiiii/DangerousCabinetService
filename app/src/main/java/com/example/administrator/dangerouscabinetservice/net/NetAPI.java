package com.example.administrator.dangerouscabinetservice.net;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Author: create by ZhongMing
 * Time: 2019/3/15 0015 09:22
 * Description:
 */
public interface NetAPI {
    @GET("/RootFile/Platform/20190313/1552463436695.png")
    Call<ResponseBody> downloadFileWithFixedUrl();

    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);
}
