package com.beisheng.mybslibary.utils.pay;


/**
 * Created by Zhouztashin on 2016/3/1.
 * 创建支付宝订单
 */
public class AlipayOrderCreator extends OrdersCreator {

    public AlipayOrderCreator(String pay) {
        super(pay);
    }

    @Override
    public String createOrder() {
        return pay;
    }


}
