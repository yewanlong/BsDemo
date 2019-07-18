package simcpux.sourceforge.net.muzilibrary.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beisheng.mybslibary.activity.bs.BSBaseSwipeBackActivity;
import com.beisheng.mybslibary.volley.MyHttpCycleContext;
import com.beisheng.mybslibary.widget.LoadingView;

import simcpux.sourceforge.net.muzilibrary.R;

public abstract class BaseActivity extends BSBaseSwipeBackActivity implements MyHttpCycleContext {
    private NoDataListener noDataListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public TextView titleView() {
        return (TextView) findViewById(R.id.tv_title);
    }

    public LoadingView loadingView() {
        return (LoadingView) findViewById(R.id.loadingView);
    }

    public void layoutNoDataGone() {
        findViewById(R.id.layout_no_data).setVisibility(View.GONE);
    }

    public void layoutNoDataVis(NoDataListener listener) {
        this.noDataListener = listener;
        findViewById(R.id.layout_no_data).setVisibility(View.VISIBLE);
    }

    public boolean checkPermission(String[] permissions, int REQUEST_FOR_PERMISSIONS) {
        if (lacksPermissions(permissions)) {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    REQUEST_FOR_PERMISSIONS);
            return true;
        }
        return false;
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }    // 判断是否缺少权限

    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(getApplicationContext(), permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void noData(View view) {
        noDataListener.noDataOnclick();
        loadingView().setVisibility(View.VISIBLE);
    }


    public interface NoDataListener {
        void noDataOnclick();
    }

}
