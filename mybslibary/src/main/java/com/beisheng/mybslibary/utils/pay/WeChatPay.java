package com.beisheng.mybslibary.utils.pay;

import android.app.Activity;
import android.util.Log;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * Created by Zhozutashin on 2016/3/1.
 * 微信支付
 */
public abstract class WeChatPay implements Pay {
    private IWXAPI mApi;
    protected Activity mActivity;

    public WeChatPay(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void pay(final OrdersCreator ordersCreator) {
        if (!(ordersCreator instanceof WeChatPayOrderCreator)) {
            result(PAY_FAIL);
            return;
        }
        mApi = WXAPIFactory.createWXAPI(mActivity, ((WeChatPayOrderCreator) ordersCreator).appid);
        //版本不支持
        if (mApi.getWXAppSupportAPI() < Build.PAY_SUPPORTED_SDK_INT) {
            result(PAY_FAIL);
            return;
        }
        mApi.registerApp(((WeChatPayOrderCreator) ordersCreator).appid);
        mApi.sendReq((BaseReq) ordersCreator.createOrder());
    }
}
