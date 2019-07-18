package com.beisheng.mybslibary.utils.pay;

/**
 * Created by Zhouztashin on 2016/3/1.
 * 生成支付订单
 */
public abstract class OrdersCreator {
    public String pay;
    public String appid;
    public String partnerid;
    public String prepayid;
    public String noncestr;
    public String timestamp;
    public String pkg;
    public String sign;

    public OrdersCreator(String payOrder) {
        this.pay = payOrder;
    }

    public OrdersCreator(String appId, String partnerId, String prepayId, String nonceStr,
                         String timeStamp, String packageValue, String sign) {
        this.appid = appId;
        this.partnerid = partnerId;
        this.prepayid = prepayId;
        this.noncestr = nonceStr;
        this.timestamp = timeStamp;
        this.pkg = packageValue;
        this.sign = sign;
    }

    public abstract Object createOrder();

}

