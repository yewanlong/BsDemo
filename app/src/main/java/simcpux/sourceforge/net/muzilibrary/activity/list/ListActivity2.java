package simcpux.sourceforge.net.muzilibrary.activity.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.beisheng.mybslibary.imgsel.cameralibrary.util.ScreenUtils;
import com.beisheng.mybslibary.pull.BGANormalRefreshViewHolder;
import com.beisheng.mybslibary.pull.BGARefreshLayout;
import com.beisheng.mybslibary.utils.BSVToast;
import com.beisheng.mybslibary.utils.OptionsUtils;
import com.beisheng.mybslibary.volley.BSHttpUtils;
import com.beisheng.mybslibary.volley.HttpRequestListener;
import com.beisheng.mybslibary.widget.ImageCycleView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.okhttpfinal.RequestParams;
import simcpux.sourceforge.net.Constant;
import simcpux.sourceforge.net.muzilibrary.R;
import simcpux.sourceforge.net.muzilibrary.activity.BaseActivity;
import simcpux.sourceforge.net.muzilibrary.adapter.ListRecyclerAdapter;
import simcpux.sourceforge.net.muzilibrary.adapter.ViewPagerAdapter;
import simcpux.sourceforge.net.muzilibrary.model.AdvData;

public class ListActivity2 extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    public BGARefreshLayout refreshLayout;
    private ImageCycleView imageCycleView;
    private RecyclerView recyclerView;
    private ViewPager viewPager;
    private LinearLayoutManager layoutManager;
    private ListRecyclerAdapter recyclerAdapter;
    private ViewPagerAdapter viewPagerAdapter;
    private int currentPage = 0;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_list2;
    }

    @Override
    protected void initView() {
        refreshLayout = $(R.id.layout_refresh);
        imageCycleView = $(R.id.imageCycleView);
        recyclerView = $(R.id.recyclerView);
        viewPager = findViewById(R.id.viewPager);
        setSwipeBackEnable(false);
    }

    @Override
    protected void initData() {
        titleView().setText("多功能列表页");
        getAdv();
    }

    @Override
    protected void initListener() {
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(app, true));
        initRecycler();
        viewPager.addOnPageChangeListener(pageChangeListener);
    }

    @Override
    protected boolean isApplyEventBus() {
        return false;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        ((ListFragment) fragmentList.get(currentPage)).page = 1;
        ((ListFragment) fragmentList.get(currentPage)).getList();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (((ListFragment) fragmentList.get(currentPage)).isLoad) {
            ((ListFragment) fragmentList.get(currentPage)).isLoad = false;
            ((ListFragment) fragmentList.get(currentPage)).getList();
            return true;
        } else {
            return false;
        }
    }

    public void getAdv() {
        if (isNetWorkConnected(this)) {
            RequestParams params = new RequestParams(this);//请求参数
            params.addFormDataPart("type", "3");
            BSHttpUtils.OkHttpGet(Constant.HTTP_IP_API + "/publics/getAdv.html", params, listener, 1001);
        } else {
            BSVToast.showShort(R.string.net_work_msg);
        }
    }

    private HttpRequestListener<String> listener = new HttpRequestListener<String>() {
        @Override
        protected void onSuccess(int what, String response) {
            switch (what) {
                case 1001:
                    AdvData advData = JSON.parseObject(response, AdvData.class);
                    if (Constant.HTTP_CODE200.equals(advData.getCode())) {
                        initAvd(advData);
                    } else {
                        BSVToast.showLong(advData.getDesc());
                    }
                    break;
            }

        }

        @Override
        protected void onError(int what, int code, String message) {
            dismissProgressDialog();
            refreshLayout.endRefreshing();
            refreshLayout.endLoadingMore();
        }
    };

    private void initAvd(AdvData advData) {
        String[] strings = new String[advData.getData().getList().size()];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = advData.getData().getList().get(i).getImg();
        }
        initCycleView(strings, advData.getData().getList());
    }

    public void initCycleView(String[] urlList, final List<AdvData.Adv.AdvDataList> list) {
        imageCycleView.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.height = (int) (ScreenUtils.getScreenWidth(this) * 0.5);
        imageCycleView.setLayoutParams(params);
        ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
            @Override
            public void onImageClick(int position, View imageView) {
                if (!TextUtils.isEmpty(list.get(position).getUrl())) ;
            }

            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                ImageLoader.getInstance().displayImage(imageURL, imageView, OptionsUtils.options(R.mipmap.ic_friends_sends_pictures_no));
            }
        };
        imageCycleView.setImageResources(urlList, mAdCycleViewListener);
        imageCycleView.startImageCycle();
    }

    private void initRecycler() {
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        List<String> strings = new ArrayList<>();
        strings.add("广告");
        strings.add("新闻");
        strings.add("香车");
        strings.add("美女");
        strings.add("生活");
        for (int i = 0; i < strings.size(); i++) {
            Bundle bundle = new Bundle();
            ListFragment oneFragment = new ListFragment();
            bundle.putInt("position", i + 1);
            oneFragment.setArguments(bundle);
            fragmentList.add(oneFragment);
        }
        recyclerAdapter = new ListRecyclerAdapter(this, strings, new ListRecyclerAdapter.MyItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                if (currentPage != position) {
                    currentPage = position;
                    recyclerAdapter.setPos(position);
                    viewPager.setCurrentItem(position);
                }
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(layoutManager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(currentPage);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int arg0) {
            if (recyclerAdapter != null) {
                currentPage = arg0;
                recyclerAdapter.setPos(arg0);
                if (layoutManager.findLastVisibleItemPosition() < arg0) {
                    layoutManager.scrollToPositionWithOffset(arg0, 0);
                } else if (layoutManager.findFirstVisibleItemPosition() >= arg0) {
                    layoutManager.scrollToPositionWithOffset(arg0, 0);
                }
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageCycleView.pushImageCycle();
    }
}
