package com.beisheng.mybslibary.activity.bs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.beisheng.mybslibary.BSDocTalkApplication;
import com.beisheng.mybslibary.R;
import com.beisheng.mybslibary.volley.MyHttpCycleContext;
import com.beisheng.mybslibary.widget.ShapeLoadingDialog;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Lkn on 2016/7/15.
 */
public abstract class BaseFragment extends Fragment implements MyHttpCycleContext {
    public static BSDocTalkApplication app = BSDocTalkApplication.getInstance();
    public ShapeLoadingDialog pd;
    public View view;
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isApplyEventBus()) EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getContentView(), container, false);
            initView();
            initData();
            initListener();
        }
        return view;
    }

    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract boolean isApplyEventBus();

    @Override
    public void onDestroy() {
        if (isApplyEventBus()) EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) view.getParent()).removeView(view);
    }

    //映射view的快捷方法
    public <T> T $(int viewId) {
        return (T) view.findViewById(viewId);
    }

    public void setProgressDialog(String msg) {
        if (pd == null)
            pd = new ShapeLoadingDialog.Builder(getActivity())
                    .loadText(msg)
                    .build();

        pd.show();
    }

    public void dismissProgressDialog() {
        if (getActivity() == null) {
            return;
        }
        if (!getActivity().isFinishing() && pd != null)
            pd.dismiss();
    }

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
