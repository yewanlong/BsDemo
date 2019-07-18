package simcpux.sourceforge.net.muzilibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import simcpux.sourceforge.net.muzilibrary.R;
import com.beisheng.mybslibary.utils.OptionsUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * 发票列表Adapter
 */
public class ImageAdapter extends BaseAdapter {

    private List<String> list = new ArrayList<>();
    private Context context;

    public ImageAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_image, null);
            holder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage("file://" + list.get(position), holder.imageView, OptionsUtils.optionsFlase());

        return convertView;
    }


    class ViewHolder {
        ImageView imageView;
    }

    public List<String> getList() {
        return list;
    }
}
