package simcpux.sourceforge.net.muzilibrary.activity.msg;

import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;

import simcpux.sourceforge.net.InitApplication;
import simcpux.sourceforge.net.muzilibrary.R;
import simcpux.sourceforge.net.muzilibrary.activity.BaseActivity;
import simcpux.sourceforge.net.muzilibrary.model.MsgPassEvent;

public class MsgActivity extends BaseActivity {
    private TextView tv_content;

    @Override
    protected int getContentView() {
        return R.layout.activity_msg;
    }

    @Override
    protected void initView() {
        tv_content = $(R.id.tv_content);
    }

    @Override
    protected void initData() {
        titleView().setText("验证码");
    }

    @Override
    protected void initListener() {
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPassActivity.startForgetPassActivity(MsgActivity.this,
                        ModifyPassActivity.TYPE2);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPassActivity.startModifyPassActivity(MsgActivity.this,
                        ModifyPassActivity.TYPE3,
                        InitApplication.getInstance().getUserId(),
                        InitApplication.getInstance().getUserToken(),
                        InitApplication.getInstance().getUserMoblie());
            }
        });
    }

    @Override
    protected boolean isApplyEventBus() {
        return true;
    }

    @Subscribe
    public void onEventMainThread(MsgPassEvent event) {
        //自己去处理Json
        tv_content.setText(event.getResponse());
    }
}
