package com.example.photoshare.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.example.photoshare.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;

import com.example.photoshare.common.Contact;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * **********step1**********
 * 网络安全配置
 * 在资源文件新建一个xml目录，在该目录下新建文件network_security_config.xml，配置如下
 * <?xml version="1.0" encoding="utf-8"?>
 * <network-security-config >
 *     <base-config cleartextTrafficPermitted="true" />
 * </network-security-config >
 *
 * **********step2**********
 * 在AndroidManifest.xml的<application></application>标签中引入上一步所添加的网络配置相关资源文件
 * <application android:networkSecurityConfig="@xml/network_security_config"></application>
 *
 * **********step3**********
 * 在AndroidManifest.xml中添加如下配置添加网络请求权限
 * <uses-permission android:name="android.permission.INTERNET" />
 *
 * **********step4**********
 * 添加以下依赖到build.gradle，用户可自主在[https://mvnrepository.com]仓库中选择合适的版本
 *   // 网络请求框架 okhttp3
 *   implementation 'com.squareup.okhttp3:okhttp:3.10.0'
 *   //用来解析json串
 *   // https://mvnrepository.com/artifact/com.google.code.gson/gson
 *   implementation 'com.google.code.gson:gson:2.9.1'
 *
 * **********step5**********
 * 用户对所请求的数据进行自主操作
 */
public class MainActivity extends AppCompatActivity {

    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 这里写用户对应的布局
        setContentView(R.layout.activity_main);
        post();
    }

    private void post(){
        new Thread(() -> {

            // url路径
            String url = "http://47.107.52.7:88/member/photo/user/register";

            // 请求头
            Headers headers = new Headers.Builder()
                    .add("Accept", "application/json, text/plain, */*")
                    .add("appId", Contact.appId)
                    .add("appSecret", Contact.appSecret)
                    .add("Content-Type", "application/json")
                    .build();

            // 请求体
            // PS.用户也可以选择自定义一个实体类，然后使用类似fastjson的工具获取json串
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("password", "qwe123456");
            bodyMap.put("username", "qwe123456");
            // 将Map转换为字符串类型加入请求体中
            String body = gson.toJson(bodyMap);

            MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

            //请求组合创建
            Request request = new Request.Builder()
                    .url(url)
                    // 将请求头加至请求中
                    .headers(headers)
                    .post(RequestBody.create(MEDIA_TYPE_JSON, body))
                    .build();
            try {
                OkHttpClient client = new OkHttpClient();
                //发起请求，传入callback进行回调
                client.newCall(request).enqueue(callback);
            }catch (NetworkOnMainThreadException ex){
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * 回调
     */
    private final Callback callback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, IOException e) {
            //TODO 请求失败处理
            e.printStackTrace();
        }
        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            //TODO 请求成功处理
            Type jsonType = new TypeToken<ResponseBody<Object>>(){}.getType();
            // 获取响应体的json串
            String body = response.body().string();
            Log.d("info", body);
            // 解析json串到自己封装的状态
            ResponseBody<Object> dataResponseBody = gson.fromJson(body,jsonType);
            Log.d("info", dataResponseBody.toString());
        }
    };

    /**
     * http响应体的封装协议
     * @param <T> 泛型
     */
    public static class ResponseBody <T> {

        /**
         * 业务响应码
         */
        private int code;
        /**
         * 响应提示信息
         */
        private String msg;
        /**
         * 响应数据
         */
        private T data;

        public ResponseBody(){}

        public int getCode() {
            return code;
        }
        public String getMsg() {
            return msg;
        }
        public T getData() {
            return data;
        }

        @NonNull
        @Override
        public String toString() {
            return "ResponseBody{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", data=" + data +
                    '}';
        }
    }
}