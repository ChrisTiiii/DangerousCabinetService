package com.example.administrator.dangerouscabinetservice.utils;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Author: create by ZhongMing
 * Time: 2019/3/15 0015 09:33
 * Description: 封装了用于下载数据流
 */
public class WriteUtil {
    private String TAG = "DOWNLOAD :";
    InputStream inputStream = null;
    OutputStream outputStream = null;
    long fileSize;
    String type;
    public volatile static WriteUtil instance;

    public static synchronized WriteUtil getInstance() {
        if (instance == null) {
            synchronized (WriteUtil.class) {
                instance = new WriteUtil();
            }
        }
        return instance;
    }

    public boolean writeResponseBodyToDisk(ResponseBody body) {
        //创建文件夹 MyDownLoad，在存储卡下
        createDir("/sdcard/sanleng/");
        fileSize = body.contentLength();
        type = body.contentType().toString();
        inputStream = body.byteStream();
        String content = type.substring(0, type.indexOf("/"));
        Log.d(TAG, body.contentType().toString());
        switch (content) {
            case "image":
                createDir("/sdcard/sanleng/img/");
                return download("sanleng/img/sanleng.png");
            case "video":
                createDir("/sdcard/sanleng/video/");
                return download("sanleng/video/sanleng.mp4");
            default:
                return false;
        }

    }

    /**
     * 用于下载文件
     *
     * @param fileName
     * @return
     */
    private boolean download(String fileName) {
        try {
            // todo change the file location/name according to your needs
            File file = new File("/sdcard/" + fileName);
            try {
                byte[] fileReader = new byte[4096];
                long fileSizeDownloaded = 0;
                outputStream = new FileOutputStream(file);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 创建文件夹
     *
     * @param path 文件夹路径
     */
    public void createDir(String path) {
        //创建文件夹 MyDownLoad，在存储卡下
        String dirName2 = path;
        File file2 = new File(dirName2);
        //不存在创建
        if (!file2.exists()) {
            file2.mkdir();
        }

    }
}
