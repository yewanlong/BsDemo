package com.beisheng.mybslibary.imgsel;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beisheng.mybslibary.R;
import com.beisheng.mybslibary.activity.bs.BSBaseSwipeBackActivity;
import com.beisheng.mybslibary.imgsel.bean.ImageSelEvent;
import com.beisheng.mybslibary.imgsel.common.Callback;
import com.beisheng.mybslibary.imgsel.common.Constant;
import com.beisheng.mybslibary.imgsel.utils.FileUtils;
import com.beisheng.mybslibary.utils.BSVToast;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;


public class ImgSelActivity extends BSBaseSwipeBackActivity implements View.OnClickListener, Callback {

    public static final String INTENT_RESULT = "result";
    private static final int IMAGE_CROP_CODE = 1;
    private ImgSelConfig config;

    private TextView tvTitle;
    private Button btnConfirm;
    private String cropImagePath;
    private ImageView ivBack;
    private ArrayList<String> result = new ArrayList<>();
    private RelativeLayout rlTitleBar;

    public static void startImageActivity(Activity activity, ImgSelConfig config) {
        Intent intent = new Intent(activity, ImgSelActivity.class);
        Constant.config = config;
        activity.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_img_sel;
    }

    @Override
    protected void initView() {
        setSwipeBackEnable(false);
        tvTitle = findViewById(R.id.tvTitle);
        btnConfirm = findViewById(R.id.btnConfirm);
        ivBack = findViewById(R.id.ivBack);
        rlTitleBar = findViewById(R.id.rlTitleBar);
    }

    @Override
    protected void initData() {
        config = Constant.config;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fmImageList, ImgSelFragment.instance(), null)
                .commit();
        if (!FileUtils.isSdCardAvailable()) {
            BSVToast.showShort("SD卡不可用");
        }

        if (config != null) {
            rlTitleBar.setBackgroundColor(config.bgColor);
            tvTitle.setTextColor(config.textColor);
            tvTitle.setText(config.title);
            btnConfirm.setTextColor(config.textColor);
            ivBack.setImageResource(config.butImage);
        }
    }

    @Override
    protected void initListener() {
        btnConfirm.setOnClickListener(this);
        ivBack.setOnClickListener(this);

    }

    @Override
    protected boolean isApplyEventBus() {
        return false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnConfirm) {
            if (Constant.imageList != null && !Constant.imageList.isEmpty()) {
                exit();
            }
        } else if (id == R.id.ivBack) {
            Constant.imageList.clear();
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Constant.imageList.clear();
        finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSingleImageSelected(String path) {
        if (config.needCrop) {
            crop(path);
        } else {
            Constant.imageList.add(path);
            exit();
        }
    }

    @Override
    public void onImageSelected(String path) {
        btnConfirm.setText("确定(" + Constant.imageList.size() + "/" + config.maxNum + ")");
    }

    @Override
    public void onImageUnselected(String path) {
        btnConfirm.setText("确定(" + Constant.imageList.size() + "/" + config.maxNum + ")");
    }

    @Override
    public void onCameraShot(File imageFile) {
        if (imageFile != null) {
            if (config.needCrop) {
                crop(imageFile.getAbsolutePath());
            } else {
                Constant.imageList.add(imageFile.getAbsolutePath());
                exit();
            }
        }
    }

    private void crop(String imagePath) {
        File file = new File(FileUtils.createRootPath(this) + "/" + System.currentTimeMillis() + ".jpg");
        cropImagePath = file.getAbsolutePath();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(FileUtils.getImageContentUri(this, new File(imagePath)), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", config.aspectX);
        intent.putExtra("aspectY", config.aspectY);
        intent.putExtra("outputX", config.outputX);
        intent.putExtra("outputY", config.outputY);
        intent.putExtra("scale", true);//去除黑边
        intent.putExtra("scaleUpIfNeeded", true);//去除黑边
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, IMAGE_CROP_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CROP_CODE && resultCode == RESULT_OK) {
            Constant.imageList.add(cropImagePath);
            exit();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void exit() {
        EventBus.getDefault().post(new ImageSelEvent(Constant.imageList));
        Constant.imageList.clear();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
