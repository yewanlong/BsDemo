package com.beisheng.mybslibary.imgsel.bean;

import android.graphics.Bitmap;

/**
 * Created by loujianghui on 2017/12/29.
 */

public class LoadedImage {
    Bitmap mBitmap;

    public LoadedImage(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }
}