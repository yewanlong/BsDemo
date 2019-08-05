package com.beisheng.mybslibary.imgsel;

import android.Manifest;
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
import com.beisheng.mybslibary.imgsel.bean.ImageSelEvent;
import com.beisheng.mybslibary.imgsel.cameralibrary.JCameraView;
import com.beisheng.mybslibary.imgsel.cameralibrary.listener.JCameraListener;
import com.beisheng.mybslibary.imgsel.cameralibrary.util.FileUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lkn on 2018/5/4.
 */

public class CameraActivity extends BSBaseSwipeBackActivity implements View.OnClickListener {
    private JCameraView jCameraView;
    private ImageView cameraImageView;
    public static final int REQUEST_CAMERA = 5;
    private int type = 0;

    @Override
    protected int getContentView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return R.layout.activity_camera;
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 0);
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
                String path = FileUtil.saveBitmap("JCamera", bitmap);
                if (type == 0) {
                    Intent mIntent = new Intent();
                    mIntent.putExtra("IMG_PATH", path);
                    // 设置结果，并进行传送
                    CameraActivity.this.setResult(Activity.RESULT_OK, mIntent);
                } else {
                    List<String> imageList = new ArrayList<>();
                    imageList.add(path);
                    EventBus.getDefault().post(new ImageSelEvent(imageList));
                }
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
