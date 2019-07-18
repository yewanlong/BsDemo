package simcpux.sourceforge.net.muzilibrary.utlis;

import android.text.TextUtils;

import com.beisheng.mybslibary.utils.BSVToast;

public class CommonUtils {
    /**
     * 检查手机号
     *
     * @param phone 手机号
     * @return 是否合格
     */
    public static boolean checkPhone(String phone) {

        if (TextUtils.isEmpty(phone)) {
            BSVToast.showLong("手机号不能为空");
            return false;
        } else if (phone.length() != 11) {
            BSVToast.showLong("您的手机号不合法");
            return false;
        } else {
            return true;
        }

    }


    /**
     * 检查密码
     *
     * @param password 密码
     * @return 是否合格
     */
    public static boolean checkPassword(String password) {

        if (TextUtils.isEmpty(password)) {
            BSVToast.showLong("密码不能为空");
            return false;

        } else {
            return true;
        }

    }

    /**
     * 检查短信验证码
     *
     * @param smsCode
     * @return
     */
    public static boolean checkSmsCode(String smsCode) {

        if (TextUtils.isEmpty(smsCode)) {
            BSVToast.showLong("短信验证码不能为空");
            return false;

        } else {
            return true;
        }


    }
}
