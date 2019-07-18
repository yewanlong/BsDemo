package com.beisheng.mybslibary.imgsel;

import android.graphics.Color;
import android.os.Environment;


import com.beisheng.mybslibary.imgsel.utils.FileUtils;

import java.io.Serializable;


/**
 * @author yuyh.
 * @date 2016/8/5.
 */
public class ImgSelConfig implements Serializable {

    /**
     * 是否需要裁剪
     */
    public boolean needCrop;

    /**
     * 是否多选
     */
    public boolean multiSelect;

    /**
     * 最多选择图片数
     */
    public int maxNum = 9;

    /**
     * 第一个item是否显示相机
     */
    public boolean needCamera = true;

    /**
     * 标题
     */
    public String title;


    /**
     * 背景色
     */
    public int bgColor;

    /**
     * 文字颜色
     */
    public int textColor;
    /**
     * 返回图标
     */
    public int butImage;

    /**
     * 拍照存储路径
     */
    public String filePath;

    /**
     * 自定义图片加载器
     */
    public SelImageLoader loader;
    /**
     * 拍照存储路径
     */
    public String butText;
    /**
     * 裁剪输出大小
     */
    public int aspectX = 1;
    public int aspectY = 1;
    public int outputX = 500;
    public int outputY = 500;

    /**
     * 选择打开相册Activity
     *
     * @param builder
     */

    public ImgSelConfig(Builder builder) {
        this.needCrop = builder.needCrop;
        this.multiSelect = builder.multiSelect;
        this.maxNum = builder.maxNum;
        this.needCamera = builder.needCamera;
        this.title = builder.title;
        this.bgColor = builder.bgColor;
        this.textColor = builder.textColor;
        this.filePath = builder.filePath;
        this.loader = builder.loader;
        this.aspectX = builder.aspectX;
        this.aspectY = builder.aspectY;
        this.outputX = builder.outputX;
        this.outputY = builder.outputY;
        this.butText = builder.butText;
        this.butImage = builder.butImage;
    }

    public static class Builder implements Serializable {

        private boolean needCrop = false;
        private boolean multiSelect = true;
        private int maxNum = 9;
        private boolean needCamera = true;
        private String title = "图片";
        private int textColor;
        private int bgColor;
        private String filePath;
        private SelImageLoader loader;

        private int aspectX = 1;
        private int aspectY = 1;
        private int outputX = 400;
        private int outputY = 400;
        private String butText = "确认";
        private int butImage;

        public Builder(SelImageLoader loader) {
            this.loader = loader;

            if (FileUtils.isSdCardAvailable())
                filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Camera";
            else
                filePath = Environment.getRootDirectory().getAbsolutePath() + "/Camera";

            FileUtils.createDir(filePath);
        }

        public Builder needCrop(boolean needCrop) {
            this.needCrop = needCrop;
            return this;
        }

        public Builder multiSelect(boolean multiSelect) {
            this.multiSelect = multiSelect;
            return this;
        }

        public Builder butText(String butText) {
            this.butText = butText;
            return this;
        }

        public Builder maxNum(int maxNum) {
            this.maxNum = maxNum;
            return this;
        }

        public Builder butImage(int butImage) {
            this.butImage = butImage;
            return this;
        }

        public Builder needCamera(boolean needCamera) {
            this.needCamera = needCamera;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder textColor(int titleColor) {
            this.textColor = titleColor;
            return this;
        }

        public Builder bgColor(int titleBgColor) {
            this.bgColor = titleBgColor;
            return this;
        }


        private Builder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public Builder cropSize(int aspectX, int aspectY, int outputX, int outputY) {
            this.aspectX = aspectX;
            this.aspectY = aspectY;
            this.outputX = outputX;
            this.outputY = outputY;
            return this;
        }

        public ImgSelConfig build() {
            return new ImgSelConfig(this);
        }
    }
}
