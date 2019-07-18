package com.beisheng.mybslibary.imgsel;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.beisheng.mybslibary.R;
import com.beisheng.mybslibary.activity.bs.BSBaseSwipeBackActivity;
import com.beisheng.mybslibary.imgsel.cameralibrary.JCameraView;
import com.beisheng.mybslibary.imgsel.cameralibrary.listener.JCameraListener;
import com.beisheng.mybslibary.imgsel.cameralibrary.util.FileUtil;

import java.io.File;


/**
 * Created by Lkn on 2018/5/4.
 */

public class CameraActivity extends BSBaseSwipeBackActivity implements View.OnClickListener {
    private JCameraView jCameraView;
    private ImageView cameraImageView;
    private static final int REQUEST_CAMERA = 5;

    @Override
    protected int getContentView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return R.layout.activity_camera;
    }

    @Override
    protected void initView() {
        jCameraView = (JCameraView) findViewById(R.id.jcameraview);
        cameraImageView = $(R.id.iv_camera);
    }

    @Override
    protected void initData() {
        jCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "JCamera");
        jCameraView.setFeatures(JCameraView.BUTTON_STATE_ONLY_CAPTURE);
        jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);
        jCameraView.setImageViewGone();
        jCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                //获取图片bitmap
//                Log.i("JCameraView", "bitmap = " + bitmap.getWidth());
                String path = FileUtil.saveBitmap("JCamera", bitmap);
                Intent mIntent = new Intent();
                mIntent.putExtra("IMG_PATH", path);
                // 设置结果，并进行传送
                CameraActivity.this.setResult( Activity.RESULT_OK, mIntent);
                finish();
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
            }

            @Override
            public void quit() {
                finish();
            }
        });
    }

    @Override
    protected void initListener() {
        cameraImageView.setOnClickListener(this);
    }

    @Override
    protected boolean isApplyEventBus() {
        return false;
    }


    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_camera) {
            jCameraView.setCamera();

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        jCameraView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        jCameraView.onPause();
    }
}
