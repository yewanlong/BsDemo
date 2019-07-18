package com.beisheng.mybslibary.volley;

import com.android.volley.VolleyLog;
import com.beisheng.mybslibary.BSDocTalkApplication;
import com.beisheng.mybslibary.BuildConfig;
import com.beisheng.mybslibary.utils.MD5Utils;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

public class BSHttpUtils {

    public static void OkHttpPost(String url, RequestParams params, final HttpRequestListener<String> listener, final int what) {
        String[] arr = new String[params.getFormParams().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = params.getFormParams().get(i).getKey() + "=" + params.getFormParams().get(i).getValue();
        }
        String timestamp = MD5Utils.getFoodString(arr)[0];
        String sign = MD5Utils.getFoodString(arr)[1];
        params.addFormDataPart("timestamp", timestamp);
        params.addFormDataPart("sign", sign);
        if (BuildConfig.DEBUG && BSDocTalkApplication.getInstance().isDebugMode()) {
            VolleyLog.d("%s", url);
            VolleyLog.d("%s", params.toString());
        }
        HttpRequest.post(url, params, new BaseHttpRequestCallback<String>() {
            @Override
            protected void onSuccess(String response) {
                if (BuildConfig.DEBUG && BSDocTalkApplication.getInstance().isDebugMode()) {
                    VolleyLog.d("%s", response);
                }
                listener.onSuccess(what, response);
            }

            //请求失败（服务返回非法JSON、服务器异常、网络异常）
            @Override
            public void onFailure(int errorCode, String msg) {
                listener.onError(what, errorCode, msg);
            }
        });
    }

    public static void OkHttpGet(String url, RequestParams params, final HttpRequestListener<String> listener, final int what) {
        String[] arr = new String[params.getFormParams().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = params.getFormParams().get(i).getKey() + "=" + params.getFormParams().get(i).getValue();
        }
        String timestamp = MD5Utils.getFoodString(arr)[0];
        String sign = MD5Utils.getFoodString(arr)[1];
        params.addFormDataPart("timestamp", timestamp);
        params.addFormDataPart("sign", sign);
        if (BuildConfig.DEBUG && BSDocTalkApplication.getInstance().isDebugMode()) {
            VolleyLog.d("%s", url);
            VolleyLog.d("%s", params.toString());
        }
        HttpRequest.get(url, params, new BaseHttpRequestCallback<String>() {

            @Override
            protected void onSuccess(String response) {
                if (BuildConfig.DEBUG && BSDocTalkApplication.getInstance().isDebugMode()) {
                    VolleyLog.d("%s", response);
                }
                listener.onSuccess(what, response);
            }

            //请求失败（服务返回非法JSON、服务器异常、网络异常）
            @Override
            public void onFailure(int errorCode, String msg) {
                listener.onError(what, errorCode, msg);
            }
        });
    }

}
