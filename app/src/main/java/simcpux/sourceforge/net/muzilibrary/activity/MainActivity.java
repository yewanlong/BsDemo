package simcpux.sourceforge.net.muzilibrary.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.beisheng.mybslibary.BSConstant;
import com.beisheng.mybslibary.activity.BSSearchActivity;
import com.beisheng.mybslibary.mode.EventSearch;
import com.beisheng.mybslibary.utils.BSVToast;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.greenrobot.eventbus.Subscribe;

import simcpux.sourceforge.net.muzilibrary.R;
import simcpux.sourceforge.net.muzilibrary.activity.choose.ChooseActivity;
import simcpux.sourceforge.net.muzilibrary.activity.img.ImageActivity;
import simcpux.sourceforge.net.muzilibrary.activity.list.ListActivity;
import simcpux.sourceforge.net.muzilibrary.activity.msg.MsgActivity;
import simcpux.sourceforge.net.muzilibrary.activity.pay.PayActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ShareAction mShareAction;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initShare();
    }

    @Override
    protected void initData() {
        setSwipeBackEnable(false);
    }

    @Override
    protected void initListener() {
        findViewById(R.id.btn_img).setOnClickListener(this);
        findViewById(R.id.btn_share).setOnClickListener(this);
        findViewById(R.id.btn_money).setOnClickListener(this);
        findViewById(R.id.btn_list).setOnClickListener(this);
        findViewById(R.id.btn_choose).setOnClickListener(this);
        findViewById(R.id.btn_msg).setOnClickListener(this);
        findViewById(R.id.btn_search).setOnClickListener(this);
    }

    @Override
    protected boolean isApplyEventBus() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                BSSearchActivity.startSearchActivity(this, BSConstant.BS_SEARCH, ContextCompat.getColor(this,R.color.colorAccent));
                break;
            case R.id.btn_img:
                startActivity(new Intent(this, ImageActivity.class));
                break;
            case R.id.btn_share:
                mShareAction.open();
                break;
            case R.id.btn_choose:
                startActivity(new Intent(this, ChooseActivity.class));
                break;
            case R.id.btn_money:
                startActivity(new Intent(this, PayActivity.class));
                break;
            case R.id.btn_list:
                startActivity(new Intent(this, ListActivity.class));
                break;
            case R.id.btn_msg:
                startActivity(new Intent(this, MsgActivity.class));
                break;
        }

    }

    private void initShare() {
        mShareAction = new ShareAction(MainActivity.this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (!checkPermission(new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        }, 199)) {
                            UMWeb web = new UMWeb("http://www.baidu.com");
                            web.setTitle("测试");
                            web.setDescription("测试测试测试测试");
                            web.setThumb(new UMImage(MainActivity.this, R.drawable.shfw_11));
                            new ShareAction(MainActivity.this).withMedia(web)
                                    .setPlatform(share_media)
                                    .share();
                        }

                    }
                });
    }
    @Subscribe
    public void onEventMain(EventSearch eventSearch){
        BSVToast.showLong(eventSearch.getContent());
    }
}
