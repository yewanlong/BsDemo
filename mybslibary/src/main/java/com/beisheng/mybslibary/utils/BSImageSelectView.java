package com.beisheng.mybslibary.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.beisheng.mybslibary.R;
import com.beisheng.mybslibary.activity.bs.PhotoPageActivity;
import com.beisheng.mybslibary.adapter.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class BSImageSelectView extends BSGridView implements AdapterView.OnItemClickListener {
    private int horizontalSpacing, verticalSpacing, numColumns; //横向间距，纵向间距，一行最多显示几张图片
    private ImageAdapter imageAdapter;
    private ArrayList<String> list = new ArrayList<>();//图片集合
    private String imgSrc;      //图片名称
    public int maxImgSize = 8; //最多显示几张图片
    public boolean isShowAdd = true; //是否显示添加图片按钮
    private OnImageSelectViewListener onImageSelectViewListener;

    public BSImageSelectView(Context context) {
        super(context);
    }

    public BSImageSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BSImage_Select);
        numColumns = a.getInteger(R.styleable.BSImage_Select_numColumns, 4);
        horizontalSpacing = a.getInteger(R.styleable.BSImage_Select_horizontalSpacing, 10);
        verticalSpacing = a.getInteger(R.styleable.BSImage_Select_verticalSpacing, 10);
        imgSrc = a.getString(R.styleable.BSImage_Select_imgSrc);
        maxImgSize = a.getInteger(R.styleable.BSImage_Select_maxImgSize, 8);
        if (imgSrc == null)
            imgSrc = "icon_add_img";
        list.add(imgSrc);
        setHorizontalSpacing(horizontalSpacing);
        setVerticalSpacing(verticalSpacing);
        setNumColumns(numColumns);
        //将从资源文件中加载的属性设置给子控件
        imageAdapter = new ImageAdapter(context, list, numColumns, horizontalSpacing, isShowAdd);
        setAdapter(imageAdapter);
        setOnItemClickListener(this);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (imageAdapter != null) {
            imageAdapter.setWidth(getWidth());
            imageAdapter.notifyDataSetChanged();
        }
    }

    public void setMaxImgSize(int maxImgSize) {
        this.maxImgSize = maxImgSize;
    }

    public void setShowAdd(boolean showAdd) {
        isShowAdd = showAdd;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setList(List<String> mList) {
        list.addAll(mList);
        if (list.size() > 8) {
            isShowAdd = false;
            list.remove(0);
        } else {
            if (list.size() > 0 && !list.get(0).equals(imgSrc)) {
                list.add(0, imgSrc);
            } else {
                if (list.size() == 0)
                    list.add(imgSrc);
            }
            isShowAdd = true;
        }
        imageAdapter.setShowAdd(isShowAdd);
        imageAdapter.notifyDataSetChanged();
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
        list.set(0, imgSrc);
        imageAdapter.notifyDataSetChanged();
    }

    //注意这个参数
    public List<String> getList() {
        ArrayList<String> list = new ArrayList<>(this.list);
        if (list.size() > 0 && list.get(0).equals(imgSrc)) {
            list.remove(0);
        }
        return list;
    }

    public int size() {
        int size = list.size();
        if (size > 0 && list.get(0).equals(imgSrc)) {
            size--;
        }
        return size;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0 && isShowAdd) {
            if (onImageSelectViewListener == null) {
                BSVToast.showLong("onImageSelectViewListener不能为空");
            } else {
                onImageSelectViewListener.onItemClick(parent, view, position, id);
            }
        } else {
            PhotoPageActivity.startPhotoPageActivity(getContext(), list, position, imgSrc);
        }
    }

    public void setOnImageSelectViewListener(OnImageSelectViewListener onImageSelectViewListener) {
        this.onImageSelectViewListener = onImageSelectViewListener;
    }

    public interface OnImageSelectViewListener {
        void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }

}
