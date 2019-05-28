package com.example.mylibrary;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKutils {
    OkHttpClient okHttpClient;
    private OKutils() {
        okHttpClient=new OkHttpClient
                .Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .build();
    }
    private static OKutils oKutils=new OKutils();
    public static  OKutils newInstance(){
        return oKutils;
    }

    public void doGet(String url, final MyCallBack myCallBack){
        //请求对象
        final Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        //发起连接
        Call call = okHttpClient.newCall(request);
        //得到结果
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                 myCallBack.error(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                myCallBack.success(response.body().string());
            }
        });
    }




    public void doPost(String url, final MyCallBack myCallBack,String[] keyname,String[] value){
        //请求体对象
        FormBody.Builder builder = new FormBody.Builder();
        for(int i=0;i<keyname.length;i++){
            builder.add(keyname[i],value[i]);
        }
        FormBody formBody = builder.build();

        //请求对象
        final Request request = new Request.Builder()
                .post(formBody)//放置请求体
                .url(url)
                .build();
        //发起连接
        Call call = okHttpClient.newCall(request);
        //得到结果
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                myCallBack.error(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                myCallBack.success(response.body().string());
            }
        });
    }







    interface MyCallBack{
        public void success(String str);
        public void error(String mesage);
    }



}
