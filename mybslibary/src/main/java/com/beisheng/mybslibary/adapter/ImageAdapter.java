package com.beisheng.mybslibary.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beisheng.mybslibary.R;
import com.beisheng.mybslibary.imgsel.cameralibrary.util.ScreenUtils;
import com.beisheng.mybslibary.utils.OptionsUtils;
import com.beisheng.mybslibary.utils.ResourcesUtils;
import com.beisheng.mybslibary.utils.ScreenUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;
    private int numColumns;
    private int horizontalSpacing;
    private int width = 0;
    private boolean isShowAdd;

    public ImageAdapter(Context context, List<String> list, int numColumns,
                        int horizontalSpacing, boolean isShowAdd) {
        super();
        this.context = context;
        this.list = list;
        this.horizontalSpacing = horizontalSpacing;
        this.numColumns = numColumns;
        this.isShowAdd = isShowAdd;
        width = ScreenUtil.getInstance(context).getWidth();
    }

    public void setShowAdd(boolean showAdd) {
        isShowAdd = showAdd;
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }

    public void setWidth(int width) {
        this.width = width;
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
        if (isShowAdd&&position == 0) {
            holder.imageView.setImageResource(ResourcesUtils.getDrableId(context, list.get(position)));
        } else
            ImageLoader.getInstance().displayImage("file://" + list.get(position), holder.imageView, OptionsUtils.optionsFlase());
        if (numColumns != 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.width = (width / numColumns) - (horizontalSpacing);
            params.height = params.width;
            holder.imageView.setLayoutParams(params);
        }
        return convertView;
    }


    class ViewHolder {
        ImageView imageView;
    }

    public List<String> getList() {
        return list;
    }
}
