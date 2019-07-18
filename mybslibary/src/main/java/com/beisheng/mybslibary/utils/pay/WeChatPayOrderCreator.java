package com.beisheng.mybslibary.utils.pay;


import com.tencent.mm.opensdk.modelpay.PayReq;

/**
 * Created by Zhouztashin on 2016/3/1.
 * 创建微信订单
 */
public class WeChatPayOrderCreator extends OrdersCreator {

    public WeChatPayOrderCreator(String appId, String partnerId, String prepayId, String nonceStr,
                                 String timeStamp, String packageValue, String sign) {
        super(appId, partnerId, prepayId, nonceStr,
                timeStamp, packageValue, sign);
    }


    @Override
    public Object createOrder() {
        PayReq req = new PayReq();
        req.appId = appid;
        req.partnerId = partnerid;
        req.prepayId = prepayid;
        req.nonceStr = noncestr;
        req.timeStamp = timestamp;
        req.packageValue = pkg;
        req.sign = sign;
        return req;
    }

}
