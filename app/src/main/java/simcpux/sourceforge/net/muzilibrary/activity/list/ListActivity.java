package simcpux.sourceforge.net.muzilibrary.activity.list;

import android.content.Intent;
import android.view.View;

import simcpux.sourceforge.net.muzilibrary.R;
import simcpux.sourceforge.net.muzilibrary.activity.BaseActivity;

public class ListActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected int getContentView() {
        return R.layout.activity_list;
    }

    @Override
    protected void initView() {
        titleView().setText("列表页");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }

    @Override
    protected boolean isApplyEventBus() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                startActivity(new Intent(this, ListActivity1.class));
                break;
            case R.id.button2:
                startActivity(new Intent(this, ListActivity2.class));
                break;
        }
    }
}
