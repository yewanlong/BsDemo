package simcpux.sourceforge.net.muzilibrary.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.beisheng.mybslibary.utils.pay.MyWeChatPay;
import com.beisheng.mybslibary.utils.pay.Pay;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import simcpux.sourceforge.net.Constant;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        api = WXAPIFactory.createWXAPI(this, Constant.WxKey1);
        api.handleIntent(getIntent(), this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                MyWeChatPay.WeChatResult(Pay.PAY_SUCCESS);
                finish();
            } else {
                MyWeChatPay.WeChatResult(Pay.PAY_FAIL);
                finish();
            }
        }
    }
}