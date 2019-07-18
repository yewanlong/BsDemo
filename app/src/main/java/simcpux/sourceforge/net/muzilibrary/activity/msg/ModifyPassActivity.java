package simcpux.sourceforge.net.muzilibrary.activity.msg;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beisheng.mybslibary.utils.BSVToast;
import com.beisheng.mybslibary.volley.BSHttpUtils;
import com.beisheng.mybslibary.volley.HttpRequestListener;
import com.beisheng.mybslibary.widget.MessageButtonCode;

import org.greenrobot.eventbus.EventBus;

import cn.finalteam.okhttpfinal.RequestParams;
import simcpux.sourceforge.net.Constant;
import simcpux.sourceforge.net.muzilibrary.R;
import simcpux.sourceforge.net.muzilibrary.activity.BaseActivity;
import simcpux.sourceforge.net.muzilibrary.model.ModelBase;
import simcpux.sourceforge.net.muzilibrary.model.MsgPassEvent;
import simcpux.sourceforge.net.muzilibrary.utlis.CommonUtils;

public class ModifyPassActivity extends BaseActivity {
    public static int TYPE2 = 2;//2是忘记密码
    public static int TYPE3 = 3;//3是修改密码
    private EditText et_register_code, et_user_pwd, et_user_pwd2,
            et_old_pwd, et_mobile;
    private TextView tv_get_sms_code;
    private MessageButtonCode buttonCode;
    private String uid, token, mobile;
    private int type = 2;//2是忘记密码，3是登录密码

    public static void startModifyPassActivity(Context context, int type,
                                               String uid, String token, String mobile) {
        context.startActivity(new Intent(context, ModifyPassActivity.class)
                .putExtra("type", type)
                .putExtra("uid", uid)
                .putExtra("token", token)
                .putExtra("mobile", mobile));
    }

    public static void startForgetPassActivity(Context context, int type) {
        context.startActivity(new Intent(context, ModifyPassActivity.class)
                .putExtra("type", type));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_setting_pass;
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 2);
        uid = getIntent().getStringExtra("uid");
        token = getIntent().getStringExtra("token");
        mobile = getIntent().getStringExtra("mobile");
        et_register_code = $(R.id.et_register_code);
        et_user_pwd = $(R.id.et_user_pwd);
        tv_get_sms_code = $(R.id.tv_get_sms_code);
        et_user_pwd2 = $(R.id.et_user_pwd2);
        et_old_pwd = $(R.id.et_old_pwd);
        et_mobile = $(R.id.et_mobile);
        if (type == TYPE2) {
            findViewById(R.id.layout_mobile).setVisibility(View.VISIBLE);
            findViewById(R.id.layout_old_pwd).setVisibility(View.GONE);
            titleView().setText("忘记密码");
        } else {
            findViewById(R.id.layout_mobile).setVisibility(View.GONE);
            findViewById(R.id.layout_old_pwd).setVisibility(View.VISIBLE);
            titleView().setText("修改密码");
        }
    }

    @Override
    protected void initData() {
        buttonCode = new MessageButtonCode(this, new MessageButtonCode.CountDownTimerListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_get_sms_code.setEnabled(false);
                tv_get_sms_code.setText("已发送(" + millisUntilFinished / 1000 + ")");
                tv_get_sms_code.setBackgroundResource(R.drawable.bs_splash_grey_5dp);
            }

            @Override
            public void onFinish() {
                tv_get_sms_code.setEnabled(true);
                tv_get_sms_code.setText("重新获取验证码");
                tv_get_sms_code.setBackgroundResource(R.drawable.bs_splash_accent_5dp);
            }
        });
    }


    @Override
    protected void initListener() {
        tv_get_sms_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSmsCode();
            }
        });
        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String password = et_user_pwd.getText().toString().trim();
        String smsCode = et_register_code.getText().toString().trim();
        if (!CommonUtils.checkPassword(password)) {
            return;
        }
        if (!CommonUtils.checkSmsCode(smsCode)) {
            return;
        }
        if (!password.equals(et_user_pwd2.getText().toString().trim())) {
            BSVToast.showLong("两次密码输入不同");
            return;
        }
        if (isNetWorkConnected(this)) {
            showProgressDialog("正在提交...");
            RequestParams params = new RequestParams(this);//请求参数
            if (type == TYPE3) {
                if (et_old_pwd.getText().toString().trim().length() == 0) {
                    BSVToast.showLong("请输入旧密码");
                    return;
                }
                params.addFormDataPart("old", et_old_pwd.getText().toString().trim());
                params.addFormDataPart("uid", uid);
                params.addFormDataPart("token", token);
            }
            params.addFormDataPart("code", smsCode);
            params.addFormDataPart("password", password);
            params.addFormDataPart("again", password);
            params.addFormDataPart("mobile", mobile);
            BSHttpUtils.OkHttpGet(Constant.CHANGE_PWD, params, listener, 1002);
        } else {
            BSVToast.showShort(R.string.net_work_msg);
        }
    }

    private void sendSmsCode() {
        if (isNetWorkConnected(this)) {
            RequestParams params = new RequestParams(this);//请求参数
            if (type == TYPE2) {
                params.addFormDataPart("mobile", et_mobile.getText().toString().trim());
            } else {
                params.addFormDataPart("mobile", mobile);
            }
            params.addFormDataPart("type", type);
            BSHttpUtils.OkHttpGet(Constant.SEND_MSG, params, listener, 1001);
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
                    try {
                        ModelBase modelBase = JSON.parseObject(response, ModelBase.class);
                        if (Constant.HTTP_CODE200.equals(modelBase.getCode()) ||
                                Constant.HTTP_CODE201.equals(modelBase.getCode())) {
                            buttonCode.start(ModifyPassActivity.this);
                        } else {
                            buttonCode.cancel();
                            BSVToast.showLong(modelBase.getDesc());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 1002:
                    try {
                        ModelBase modelBase = JSON.parseObject(response, ModelBase.class);
                        BSVToast.showLong(modelBase.getDesc());
                        if (Constant.HTTP_CODE200.equals(modelBase.getCode()) ||
                                Constant.HTTP_CODE201.equals(modelBase.getCode())) {
                            EventBus.getDefault().post(new MsgPassEvent(response));
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }

        @Override
        protected void onError(int what, int code, String message) {
            dismissProgressDialog();
        }
    };


    @Override
    protected boolean isApplyEventBus() {
        return false;
    }
}
