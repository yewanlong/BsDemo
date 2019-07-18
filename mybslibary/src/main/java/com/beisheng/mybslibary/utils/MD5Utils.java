package com.beisheng.mybslibary.utils;

import android.util.Log;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collections;


/**
 * Created by Administrator on 2018/3/2.
 */

public class MD5Utils {
    public static final String MD5_TITLE = "4WCqP3LR6Vre^AyN";

    /**
     * 升序排序
     * @param str
     * @return
     */
    public static  String[] getString(String[] str){
        String tm= System.currentTimeMillis()+"";
        String temptime="timestamp="+tm.substring(0,10);
        String[]ststemp=new String[str.length+1];
        for(int i=0;i<ststemp.length;i++){
            if(i==ststemp.length-1){
                ststemp[i]=temptime;
            }else{
                ststemp[i]=str[i];
            }
        }
        Arrays.sort(ststemp);
        String sorstr="";

        for(int i=0;i<ststemp.length;i++){
            sorstr=sorstr+ststemp[i]+"&";
        }
        String sm=sorstr+MD5_TITLE;
        Log.v("tags","加密前："+sm);
        String mdstr=MD5Utils.encrypt(sm);
        Log.v("tags","加密后："+mdstr);
        String[]ps=new String[2];
        ps[1]=mdstr;
        ps[0]=tm.substring(0,10);
        return ps;
    }

    /**
     * 美食外卖加密算法  倒序排序
     * @param str
     * @return
     */
    public static  String[] getFoodString(String[] str){
        String tm= System.currentTimeMillis()+"";
        String temptime="timestamp="+tm.substring(0,10);
        String[]ststemp=new String[str.length+1];
        for(int i=0;i<ststemp.length;i++){
            if(i==ststemp.length-1){
                ststemp[i]=temptime;
            }else{
                ststemp[i]=str[i];
            }
        }
        Arrays.sort(ststemp, Collections.reverseOrder());
        String sorstr="";
        for(int i=0;i<ststemp.length;i++)
        {
            sorstr=sorstr+ststemp[i]+"&";
        }
        String sm=sorstr+MD5_TITLE;
        Log.v("tags","加密前："+sm);
        String mdstr=MD5Utils.encrypt(sm);
//        Log.v("tags","加密后："+mdstr);
        String[]ps=new String[2];
        ps[1]=mdstr;
        ps[0]=tm.substring(0,10);
        return ps;
    }
    public static String getFoodString2(String[] str) {
        String tm = System.currentTimeMillis() + "";
        String[] ststemp = new String[str.length];
        for (int i = 0; i < ststemp.length; i++) {
            ststemp[i] = str[i];
        }
        Arrays.sort(ststemp, Collections.reverseOrder());
        String sorstr = "";
        for (int i = 0; i < ststemp.length; i++) {
            sorstr = sorstr + ststemp[i] + "&";
        }
        String sm = sorstr +MD5_TITLE;
        Log.v("tags", "加密前：" + sm);
        String mdstr = MD5Utils.encrypt(sm);
//        Log.v("tags","加密后："+mdstr);
        return mdstr;
    }

    public final static String encrypt(String plaintext) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] btInput = plaintext.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

}
