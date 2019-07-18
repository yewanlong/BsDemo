package com.beisheng.mybslibary.utils.pay;

import android.app.Activity;

/**
 * Created by Administrator on 2016/3/4.
 */
public class MyWeChatPay extends WeChatPay {
    private static PayCallBack sPayCallBack;
    public static void setCallBack(PayCallBack payCallBack){
        sPayCallBack = payCallBack;
    }
    public static void removeCallBack(){
        sPayCallBack = null;
    }

    public MyWeChatPay(Activity activity) {
        super(activity);
    }

    @Override
    public void result(int payResult) {
        if(sPayCallBack!=null){
            sPayCallBack.result(payResult);
        }
    }
    public static void WeChatResult(int payResult){
        if(sPayCallBack!=null){
            sPayCallBack.result(payResult);
        }
    }
}
