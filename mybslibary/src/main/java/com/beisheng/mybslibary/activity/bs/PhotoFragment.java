package com.beisheng.mybslibary.activity.bs;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.beisheng.mybslibary.R;
import com.nostra13.universalimageloader.core.ImageLoader;


public class PhotoFragment extends BaseFragment {
    private String url;
    private ImageView imageView;


    public static PhotoFragment newInstance(String url) {
        PhotoFragment belleImageFragment = new PhotoFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("url", url);
        belleImageFragment.setArguments(mBundle);
        return belleImageFragment;
    }


    @Override
    protected void initView() {
        assert getArguments() != null;
        url = getArguments().getString("url");
        imageView = (ImageView) view.findViewById(R.id.imageView);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_photo;
    }

    @Override
    protected void initData() {
        ImageLoader.getInstance().displayImage("file://" + url, imageView);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected boolean isApplyEventBus() {
        return false;
    }
}
