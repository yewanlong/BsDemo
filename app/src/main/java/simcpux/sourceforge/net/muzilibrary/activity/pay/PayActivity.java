package simcpux.sourceforge.net.muzilibrary.activity.pay;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.beisheng.mybslibary.utils.BSVToast;
import com.beisheng.mybslibary.utils.pay.AlipayOrderCreator;
import com.beisheng.mybslibary.utils.pay.MyAlipayPay;
import com.beisheng.mybslibary.utils.pay.MyWeChatPay;
import com.beisheng.mybslibary.utils.pay.Pay;
import com.beisheng.mybslibary.utils.pay.PayCallBack;
import com.beisheng.mybslibary.utils.pay.WeChatPayOrderCreator;
import com.beisheng.mybslibary.volley.BSHttpUtils;
import com.beisheng.mybslibary.volley.HttpRequestListener;

import cn.finalteam.okhttpfinal.RequestParams;
import simcpux.sourceforge.net.Constant;
import simcpux.sourceforge.net.InitApplication;
import simcpux.sourceforge.net.muzilibrary.R;
import simcpux.sourceforge.net.muzilibrary.activity.BaseActivity;
import simcpux.sourceforge.net.muzilibrary.model.OrderData;

public class PayActivity extends BaseActivity implements View.OnClickListener, PayCallBack {
    private CheckBox scb_zfb, scb_wx;
    private EditText tv_money;

    @Override
    protected int getContentView() {
        return R.layout.activity_pay;
    }

    @Override
    protected void initView() {
        scb_zfb = $(R.id.scb_zfb);
        scb_wx = $(R.id.scb_wx);
        tv_money = $(R.id.tv_money);
        MyAlipayPay.setCallBack(this);
        MyWeChatPay.setCallBack(this);
    }

    @Override
    protected void initData() {
        titleView().setText("支付");
    }

    @Override
    protected void initListener() {
        findViewById(R.id.ll_zfb).setOnClickListener(this);
        findViewById(R.id.ll_wx).setOnClickListener(this);
        findViewById(R.id.btnSubmit).setOnClickListener(this);
    }

    @Override
    protected boolean isApplyEventBus() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_zfb:
                scb_zfb.setChecked(true);
                scb_wx.setChecked(false);
                break;
            case R.id.ll_wx:
                scb_zfb.setChecked(false);
                scb_wx.setChecked(true);
                break;
            case R.id.btnSubmit:
                if (tv_money.getText().toString().trim().length() == 0) {
                    BSVToast.showLong("请输入充值金额");
                    return;
                }
                toPay();
                break;
        }
    }

    private void toPay() {
        if (isNetWorkConnected(this)) {
            showProgressDialog("正在加载...");
            RequestParams params = new RequestParams(this);//请求参数
            params.addFormDataPart("uid", InitApplication.getInstance().getUserId());
            params.addFormDataPart("token", InitApplication.getInstance().getUserToken());
            params.addFormDataPart("money", tv_money.getText().toString().trim());
            if (scb_zfb.isChecked()) {
                params.addFormDataPart("pay_type", 1);
            } else params.addFormDataPart("pay_type", 2);
            BSHttpUtils.OkHttpPost(Constant.HTTP_IP_API + "/mine/recharge.html", params, listener, 1001);
        } else {
            BSVToast.showShort(R.string.net_work_msg);
        }
    }

    private HttpRequestListener<String> listener = new HttpRequestListener<String>() {
        @Override
        protected void onSuccess(int what, String response) {
            dismissProgressDialog();
            switch (what) {
                case 1001:
                    OrderData shopData = JSON.parseObject(response, OrderData.class);
                    if (Constant.HTTP_CODE200.equals(shopData.getCode())) {
                        payData(shopData);
                    } else {
                        BSVToast.showLong(shopData.getDesc());
                    }
                    break;
            }

        }

        @Override
        protected void onError(int what, int code, String message) {
            dismissProgressDialog();
        }
    };

    private void payData(OrderData orderData) {
        String payOrder = orderData.getData().getPayOrder();
        if (scb_zfb.isChecked()) {
            aliPay(payOrder);
        } else {
            Pay pay = new MyWeChatPay(PayActivity.this);
            pay.pay(new WeChatPayOrderCreator(orderData.getData().getOrder().getAppid()
                    , orderData.getData().getOrder().getPartnerid(), orderData.getData().getOrder().getPrepayid()
                    , orderData.getData().getOrder().getNoncestr(), orderData.getData().getOrder().getTimestamp(), orderData.getData().getOrder().getPkg()
                    , orderData.getData().getOrder().getSign()));
        }
    }

    private void aliPay(String order) {
        Pay pay = new MyAlipayPay(this);
        pay.pay(new AlipayOrderCreator(order));
    }

    @Override
    public void result(int i) {
        //输出结果
        if (i == Pay.PAY_SUCCESS) {
            BSVToast.showLong("支付成功");
        } else {
            BSVToast.showLong("支付失败");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyAlipayPay.removeCallBack();
        MyWeChatPay.removeCallBack();
    }
}
