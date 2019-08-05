package simcpux.sourceforge.net.muzilibrary.activity.img;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.beisheng.mybslibary.imgsel.ImgSelActivity;
import com.beisheng.mybslibary.imgsel.ImgSelConfig;
import com.beisheng.mybslibary.imgsel.SelImageLoader;
import com.beisheng.mybslibary.imgsel.bean.ImageSelEvent;
import com.beisheng.mybslibary.utils.BSImageSelectView;
import com.beisheng.mybslibary.utils.BSVToast;
import com.beisheng.mybslibary.utils.OptionsUtils;
import com.beisheng.mybslibary.volley.BSHttpUtils;
import com.beisheng.mybslibary.volley.HttpRequestListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;

import cn.finalteam.okhttpfinal.RequestParams;
import simcpux.sourceforge.net.Constant;
import simcpux.sourceforge.net.InitApplication;
import simcpux.sourceforge.net.muzilibrary.R;
import simcpux.sourceforge.net.muzilibrary.activity.BaseActivity;
import simcpux.sourceforge.net.muzilibrary.model.ModelBase;

public class ImageActivity extends BaseActivity implements View.OnClickListener, BSImageSelectView.OnImageSelectViewListener {
    private BSImageSelectView gridView;
    private int maxImage = 8;
    private int selectImage = maxImage;

    @Override
    protected int getContentView() {
        return R.layout.activity_image;
    }

    @Override
    protected void initView() {
        gridView = $(R.id.gridView);
    }

    @Override
    protected void initData() {
        titleView().setText("选择图片");
    }

    @Override
    protected void initListener() {
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        gridView.setOnImageSelectViewListener(this);
    }

    @Override
    protected boolean isApplyEventBus() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                if (!checkPermission(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, 199)) {
                    ImgSelConfig config = new ImgSelConfig.Builder(loader).multiSelect(false)//是否多选
                            .title("图片")//标题名称
                            .textColor(Color.WHITE)//字体颜色
                            .needCrop(true) //是否裁剪
                            .bgColor(getResources().getColor(R.color.colorPrimary))//背景色
                            .needCamera(true)//第一个item是否显示相机
                            .cropSize(1, 1, 500, 500)
                            .butText("确定")
                            .butImage(R.mipmap.back_word)
                            .build();
                    ImgSelActivity.startImageActivity(this, config);
                }
                break;
            case R.id.button2:
                if (!checkPermission(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, 199)) {
                    ImgSelConfig config = new ImgSelConfig.Builder(loader).multiSelect(true)//是否多选
                            .title("图片")//标题名称
                            .textColor(Color.WHITE)//字体颜色
                            .needCrop(false) //是否裁剪
                            .bgColor(getResources().getColor(R.color.colorPrimary))//背景色
                            .needCamera(true)//第一个item是否显示相机
                            .maxNum(selectImage)//做多选几张
                            .butText("确定")
                            .butImage(R.mipmap.back_word)
                            .build();
                    ImgSelActivity.startImageActivity(this, config);
                }
                break;
            case R.id.button3:
                if (gridView.getList().size() == 0) {
                    BSVToast.showLong("请选择图片");
                    return;
                }
                upLoadFileRequest();
                break;
            case R.id.button4:
                if (!checkPermission(new String[]{
                        Manifest.permission.CAMERA,
                }, 199)) {
                    ImgSelActivity.startPhoneActivity(this);
                }
                break;
        }
    }

    private SelImageLoader loader = new SelImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            ImageLoader.getInstance().displayImage("file://" + path, imageView, OptionsUtils.optionsFlase());
        }
    };

    @Subscribe
    public void onEventMainThread(ImageSelEvent event) {
        if (event.getRemove() != 0) {
            gridView.getList().clear();
        }
        gridView.setList(event.getImageList());
        selectImage = maxImage - event.getImageList().size();
    }

    private void upLoadFileRequest() {
        File f = new File(gridView.getList().get(0));
        showProgressDialog("正在上传...");
        RequestParams params = new RequestParams(this);//请求参数
        params.addFormDataPart("uid", InitApplication.getInstance().getUserId());
        params.addFormDataPart("token", InitApplication.getInstance().getUserToken());
        params.addFormDataPart("headpic", f);
//        params.addFormDataParts("headpic", files);
        BSHttpUtils.OkHttpPost(Constant.HTTP_IP_API + "/mine/update_user_info.html", params, listener, 1001);
    }

    private HttpRequestListener<String> listener = new HttpRequestListener<String>() {

        @Override
        protected void onSuccess(int what, String response) {
            dismissProgressDialog();
            switch (what) {
                case 1001: {
                    ModelBase modelBase = JSON.parseObject(response, ModelBase.class);
                    BSVToast.showLong(modelBase.getDesc());
                }
                break;
            }
        }

        @Override
        protected void onError(int what, int code, String message) {
            dismissProgressDialog();
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!checkPermission(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, 199)) {
            ImgSelConfig config = new ImgSelConfig.Builder(loader).multiSelect(true)//是否多选
                    .title("图片")//标题名称
                    .textColor(Color.WHITE)//字体颜色
                    .needCrop(false) //是否裁剪
                    .bgColor(getResources().getColor(R.color.colorPrimary))//背景色
                    .needCamera(true)//第一个item是否显示相机
                    .maxNum(selectImage)//做多选几张
                    .butText("确定")
                    .butImage(R.mipmap.back_word)
                    .build();
            ImgSelActivity.startImageActivity(this, config);
        }
    }
}
