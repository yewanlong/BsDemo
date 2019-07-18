package com.beisheng.mybslibary.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class MessageButtonCode {
    private static final String BS_QUANQIU_TALK = "BS_QUANQIU_TALK";
    private static final String BS_LONG_TIME = "BS_LONG_TIME";

    private long longTime = 60000; //验证码时间
    private CountDownTimer countDownTimer;
    private CountDownTimerListener listeners;

    public MessageButtonCode(Context context, CountDownTimerListener listener) {
        this.listeners = listener;
        long time = Long.valueOf(getTalk(context, BS_LONG_TIME, longTime).toString());
        long time2 = System.currentTimeMillis();
        if (time > time2) {
            longTime = time - time2;
        }
        countDownTimer = new CountDownTimer(longTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                listeners.onTick(millisUntilFinished);

            }

            @Override
            public void onFinish() {
                listeners.onFinish();
            }
        };

        if (time > time2) {
            countDownTimer.start();
        }
    }

    public void start(Context context) {
        long data = System.currentTimeMillis() + longTime;
        putTalk(context, BS_LONG_TIME, data);
        countDownTimer.start();
    }

    public void cancel() {
        countDownTimer.onFinish();
    }

    public static void putTalk(Context context, String key, Object object) {
        if (key == null) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(BS_QUANQIU_TALK,
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof Set) {
            editor.putStringSet(key, (Set<String>) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    public static Object getTalk(Context context, String key, Object defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(BS_QUANQIU_TALK,
                context.MODE_PRIVATE);
        if (defValue instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defValue);
        } else if (defValue instanceof Set) {
            return sharedPreferences.getStringSet(key, (Set<String>) defValue);
        } else {
            return sharedPreferences.getString(key, defValue.toString());
        }
    }

    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }

    public interface CountDownTimerListener {
        /**
         * 计时回调
         * @param millisUntilFinished
         */
        void onTick(long millisUntilFinished);
        /**
         * 成功回调
         */
        void onFinish();
    }
}
