package com.beisheng.mybslibary.imgsel.adapter;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.beisheng.mybslibary.R;
import com.beisheng.mybslibary.imgsel.ImgSelConfig;
import com.beisheng.mybslibary.imgsel.bean.Image;
import com.beisheng.mybslibary.imgsel.common.OnItemClickListener;
import com.beisheng.mybslibary.imgsel.easyadapter.recyclerview.EasyRVAdapter;
import com.beisheng.mybslibary.imgsel.easyadapter.recyclerview.EasyRVHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuyh.
 * @date 2016/8/5.
 */
public class ImageListAdapter extends EasyRVAdapter<Image> {

    private boolean showCamera;
    private boolean mutiSelect;

    private ImgSelConfig config;
    private Context context;

    private List<Image> selectedImageList = new ArrayList<>();
    private OnItemClickListener listener;

    public ImageListAdapter(Context context, List<Image> list, ImgSelConfig config) {
        super(context, list, R.layout.item_img_sel, R.layout.item_img_sel_take_photo);
        this.context = context;
        this.config = config;
    }

    @Override
    protected void onBindData(EasyRVHolder viewHolder, final int position, final Image item) {

        viewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(position, item);
            }
        });

        if (position == 0 && showCamera) {
            return;
        }

        final ImageView iv = viewHolder.getView(R.id.ivImage);
        config.loader.displayImage(context, item.path, iv);

        iv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    iv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv.getLayoutParams();
                params.height = params.width;
                iv.setLayoutParams(params);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        });

        if (mutiSelect) {
            viewHolder.setVisible(R.id.ivPhotoCheaked, true);
            if (selectedImageList.contains(item)) {
                viewHolder.setImageResource(R.id.ivPhotoCheaked, R.mipmap.ic_checked);
            } else {
                viewHolder.setImageResource(R.id.ivPhotoCheaked, R.mipmap.ic_uncheck);
            }
        } else {
            viewHolder.setVisible(R.id.ivPhotoCheaked, false);
        }
    }

    public void setShowCamera(boolean showCamera) {
        this.showCamera = showCamera;
    }

    public void setMutiSelect(boolean mutiSelect) {
        this.mutiSelect = mutiSelect;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && showCamera) {
            return 1;
        }
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public void select(Image image) {
        if (selectedImageList.contains(image)) {
            selectedImageList.remove(image);
        } else {
            selectedImageList.add(image);
        }
        notifyDataSetChanged();
    }
}
