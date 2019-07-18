package simcpux.sourceforge.net;

import com.beisheng.mybslibary.BSDocTalkApplication;

import simcpux.sourceforge.net.muzilibrary.R;


public class InitApplication extends BSDocTalkApplication {
    private static InitApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initUmeng(Constant.UMKey1, Constant.WxKey1, Constant.WxKey2,
                Constant.QQKey1, Constant.QQKey2);
    }

    public static InitApplication getInstance() {
        return instance;
    }

    /**
     * 顶部栏颜色
     *
     * @return
     */

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    public String getUserId() {
        return "24";
    }

    public String getUserToken() {
        return "McjxQpO0O0Oh";
    }

    public String getUserMoblie() {
        return "18502792974";
    }
}