package com.beisheng.mybslibary;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.beisheng.mybslibary.utils.BSImageLoaderUtil;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.Map;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;


public abstract class BSDocTalkApplication extends MultiDexApplication {
    public static Context applicationContext;
    private static BSDocTalkApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;
        BSImageLoaderUtil.initImageLoaderConfig(applicationContext);
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
//        builder.setSSLSocketFactory()
        OkHttpFinal.getInstance().init(builder.build());

    }

    public void initUmeng(String umengKey, String wxKey, String wxKey2, String qqK1, String qqK2) {
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, umengKey
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        PlatformConfig.setWeixin(wxKey, wxKey2);
        PlatformConfig.setQQZone(qqK1, qqK2);
    }

    /**
     * 顶部栏颜色
     *
     * @return
     */
    public abstract int getStatusBarColor();

    public static BSDocTalkApplication getInstance() {
        return instance;
    }


    public boolean isDebugMode() {
        ApplicationInfo info = getApplicationInfo();
        return (0 != ((info.flags) & ApplicationInfo.FLAG_DEBUGGABLE));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public SharedPreferences getPublicPreference() {
        return getSharedPreferences(BSConstant.BS_QUANQIU_TALK, Context.MODE_PRIVATE);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }
}