package com.beisheng.mybslibary.utils;

import android.widget.Toast;

import com.beisheng.mybslibary.BSDocTalkApplication;

/**
 * 全局Toast
 */

public class BSVToast {
    private static Toast toast;

    private BSVToast() {

    }

    /**
     * 短时间显示toast,在旧的toast未消失之前，新的不会产生
     */
    public static void showShort(CharSequence message) {
        if (toast == null) {
            toast = Toast.makeText(BSDocTalkApplication.applicationContext, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    /**
     * 短时间显示toast,在旧的toast未消失之前，新的不会产生
     */
    public static void showShort(int message) {
        if (toast == null) {
            toast = Toast.makeText(BSDocTalkApplication.applicationContext, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(BSDocTalkApplication.applicationContext.getResources().getString(message));
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    /**
     * 长时间显示toast,在旧的toast未消失之前，新的不会产生
     */
    public static void showLong(int message) {
        if (toast == null) {
            toast = Toast.makeText(BSDocTalkApplication.applicationContext, message, Toast.LENGTH_LONG);
        } else {
            toast.setText(BSDocTalkApplication.applicationContext.getResources().getString(message));
            toast.setDuration(Toast.LENGTH_LONG);
        }
        toast.show();
    }

    /**
     * 长时间显示toast,在旧的toast未消失之前，新的不会产生
     */
    public static void showLong(CharSequence message) {
        if (toast == null) {
            toast = Toast.makeText(BSDocTalkApplication.applicationContext, message, Toast.LENGTH_LONG);
        } else {
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        toast.show();
    }
}
