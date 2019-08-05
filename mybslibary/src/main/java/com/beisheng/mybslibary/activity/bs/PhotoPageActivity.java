package com.beisheng.mybslibary.activity.bs;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beisheng.mybslibary.R;
import com.beisheng.mybslibary.activity.BSDialog;
import com.beisheng.mybslibary.imgsel.bean.ImageSelEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lkn on 2016/8/23.
 */
public class PhotoPageActivity extends BSBaseSwipeBackActivity {
    private ArrayList<String> list = new ArrayList<>();
    private PhotoPageAdapter mPagerAdapter;
    private TextView titleTextView;
    private ViewPager viewPager;
    private int position;
    private String imgSrc;

    @Override
    protected int getContentView() {
        return R.layout.activity_imagepager;
    }

    public static void startPhotoPageActivity(Context context, ArrayList<String> list, int position, String imgSrc) {
        context.startActivity(new Intent(context, PhotoPageActivity.class)
                .putExtra("LIST_PHOTO", list).putExtra("imgSrc", imgSrc)
                .putExtra("POSITION", position));
    }

    @Override
    protected void initView() {
        list = getIntent().getStringArrayListExtra("LIST_PHOTO");
        position = getIntent().getIntExtra("POSITION", 0);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        titleTextView = (TextView) findViewById(R.id.tv_title);
        imgSrc = getIntent().getStringExtra("imgSrc");
        if (imgSrc != null && list.get(0).equals(imgSrc)) {
            list.remove(0);
            position--;
        }
    }

    @Override
    protected void initData() {
        mPagerAdapter = new PhotoPageAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(mPagerAdapter);
        titleTextView.setText((position + 1) + "/" + list.size());
        viewPager.setCurrentItem(position);
    }

    @Override
    protected void initListener() {
        findViewById(R.id.layout_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDelect();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("ywl", "onPageScrolled:" + position);
            }

            @Override
            public void onPageSelected(int position) {
                titleTextView.setText((position + 1) + "/" + list.size());
                PhotoPageActivity.this.position = position;
                Log.i("ywl", "onPageSelected:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected boolean isApplyEventBus() {
        return false;
    }

    private class PhotoPageAdapter extends FragmentStatePagerAdapter {
        private ArrayList<String> list;

        public PhotoPageAdapter(FragmentManager fm, ArrayList<String> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {

            return PhotoFragment.
                    newInstance(list.get(position));
        }


        @Override
        public int getCount() {

            return list.size();
        }


    }

    private void initDelect() {
        BSDialog dialog = new BSDialog(this, "提示", "内容你确定要这么做吗",
                "确定", "取消", new BSDialog.BSDialogListener() {
            @Override
            public void confirmOnclick() {
                list.remove(position);
                mPagerAdapter = new PhotoPageAdapter(getSupportFragmentManager(), list);
                viewPager.setAdapter(mPagerAdapter);
                position--;
                EventBus.getDefault().post(new ImageSelEvent(list, 1));
                if (list.size() == 0) {
                    finish();
                } else {
                    if (position < 0) {
                        position = 0;
                    }
                    viewPager.setCurrentItem(position);
                    titleTextView.setText((position + 1) + "/" + list.size());
                }
            }

            @Override
            public void cancelOnclick() {
            }
        });
        dialog.show();

    }
}
