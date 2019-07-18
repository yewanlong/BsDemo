package simcpux.sourceforge.net.muzilibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beisheng.mybslibary.utils.OptionsUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import simcpux.sourceforge.net.muzilibrary.R;
import simcpux.sourceforge.net.muzilibrary.model.ListData;

/**
 * Created by Lkn on 2017/4/1.
 */

public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<ListData.Adv.HomeListData> list = new ArrayList<>();

    public ListAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.bs_item_home_list, parent, false);
            holder.imageView = view.findViewById(R.id.imageView);
            holder.tv_time = view.findViewById(R.id.tv_time);
            holder.tv_source = view.findViewById(R.id.tv_source);
            holder.tv_title = view.findViewById(R.id.tv_title);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_time.setText(list.get(position).getCreate_time());
        holder.tv_source.setText(list.get(position).getSource());
        holder.tv_title.setText(list.get(position).getTitle());
        ImageLoader.getInstance().displayImage(list.get(position).getImg(),
                holder.imageView, OptionsUtils.options(R.mipmap.ic_friends_sends_pictures_no));
        return view;
    }

    public class ViewHolder {
        TextView tv_time, tv_source, tv_title;
        ImageView imageView;
    }


    public List<ListData.Adv.HomeListData> getList() {
        return list;
    }
}
