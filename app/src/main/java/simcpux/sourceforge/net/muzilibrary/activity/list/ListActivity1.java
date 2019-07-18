package simcpux.sourceforge.net.muzilibrary.activity.list;

import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.beisheng.mybslibary.pull.BGANormalRefreshViewHolder;
import com.beisheng.mybslibary.pull.BGARefreshLayout;
import com.beisheng.mybslibary.utils.BSVToast;
import com.beisheng.mybslibary.volley.BSHttpUtils;
import com.beisheng.mybslibary.volley.HttpRequestListener;

import cn.finalteam.okhttpfinal.RequestParams;
import simcpux.sourceforge.net.Constant;
import simcpux.sourceforge.net.muzilibrary.R;
import simcpux.sourceforge.net.muzilibrary.activity.BaseActivity;
import simcpux.sourceforge.net.muzilibrary.adapter.ListAdapter;
import simcpux.sourceforge.net.muzilibrary.model.ListData;

public class ListActivity1 extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout refreshLayout;
    private ListView listView;
    private ListAdapter adapter;
    private int page = 1;
    private boolean isLoad = false;
    private int type = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_list1;
    }

    @Override
    protected void initView() {
        refreshLayout = $(R.id.layout_refresh);
        listView = $(R.id.listView);
    }

    @Override
    protected void initData() {
        titleView().setText("普通列表页");
        adapter = new ListAdapter(this);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(app, true));
        refreshLayout.beginRefreshing();//刷新
    }

    @Override
    protected boolean isApplyEventBus() {
        return false;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        getList();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (isLoad) {
            isLoad = false;
            getList();
            return true;
        } else {
            return false;
        }
    }

    public void getList() {
        if (isNetWorkConnected(this)) {
            RequestParams params = new RequestParams(this);//请求参数
            params.addFormDataPart("page", page);
            params.addFormDataPart("type", type);
            BSHttpUtils.OkHttpGet(Constant.HTTP_IP_API + "/car/get_car_pedia.html", params, listener, 1001);
        } else {
            BSVToast.showShort(R.string.net_work_msg);
        }

    }

    private HttpRequestListener<String> listener = new HttpRequestListener<String>() {
        @Override
        protected void onSuccess(int what, String response) {
            dismissProgressDialog();
            switch (what) {
                case 1001:
                    ListData listData = JSON.parseObject(response, ListData.class);
                    if (Constant.HTTP_CODE200.equals(listData.getCode())) {
                        layoutNoDataGone();
                        listData(listData);
                    } else if (Constant.HTTP_CODE300.equals(listData.getCode())) {
                        if (page == 1)
                            layoutNoDataVis();
                    } else {
                        BSVToast.showLong(listData.getDesc());
                    }
                    refreshLayout.endRefreshing();
                    refreshLayout.endLoadingMore();
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

    private void listData(ListData listData) {
        if (page == 1) {
            adapter.getList().clear();
            if (listData.getData().getList().size() == 0) {
                layoutNoDataVis();
            }
        }
        if (page < listData.getData().getCount())
            isLoad = true;
        adapter.getList().addAll(listData.getData().getList());
        adapter.notifyDataSetChanged();
        page++;
    }

    private void layoutNoDataVis() {
        layoutNoDataVis(new NoDataListener() {
            @Override
            public void noDataOnclick() {
                page = 1;
                type = 2;
                getList();
            }
        });
    }
}
