package simcpux.sourceforge.net.muzilibrary.activity.list;

import android.os.Bundle;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.beisheng.mybslibary.activity.bs.BaseFragment;
import com.beisheng.mybslibary.utils.BSVToast;
import com.beisheng.mybslibary.volley.BSHttpUtils;
import com.beisheng.mybslibary.volley.HttpRequestListener;

import java.util.Objects;

import cn.finalteam.okhttpfinal.RequestParams;
import simcpux.sourceforge.net.Constant;
import simcpux.sourceforge.net.muzilibrary.R;
import simcpux.sourceforge.net.muzilibrary.adapter.ListAdapter;
import simcpux.sourceforge.net.muzilibrary.model.ListData;

public class ListFragment extends BaseFragment {
    private ListView listView;
    private ListAdapter adapter;
    public int page = 1, position = 0;
    public boolean isLoad = false;

    @Override
    protected int getContentView() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView() {
        Bundle bundle = this.getArguments();//得到从Activity传来的数据
        assert bundle != null;
        position = bundle.getInt("position");
        listView = $(R.id.listView);
    }

    @Override
    protected void initData() {
        adapter = new ListAdapter(getActivity());
        listView.setAdapter(adapter);
        getList();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected boolean isApplyEventBus() {
        return false;
    }

    public void getList() {
        if (isNetWorkConnected(getActivity())) {
            RequestParams params = new RequestParams(this);//请求参数
            params.addFormDataPart("page", page);
            params.addFormDataPart("type", position);
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
                        listData(listData);
                    } else {
                        BSVToast.showLong(listData.getDesc());
                    }
                    ((ListActivity2) Objects.requireNonNull(getActivity())).refreshLayout.endRefreshing();
                    ((ListActivity2) Objects.requireNonNull(getActivity())).refreshLayout.endLoadingMore();
                    break;
            }

        }

        @Override
        protected void onError(int what, int code, String message) {
            dismissProgressDialog();
            ((ListActivity2) Objects.requireNonNull(getActivity())).refreshLayout.endRefreshing();
            ((ListActivity2) Objects.requireNonNull(getActivity())).refreshLayout.endLoadingMore();
        }
    };

    private void listData(ListData listData) {
        if (page == 1)
            adapter.getList().clear();
        if (page < listData.getData().getCount())
            isLoad = true;
        adapter.getList().addAll(listData.getData().getList());
        adapter.notifyDataSetChanged();
        page++;
    }

}
