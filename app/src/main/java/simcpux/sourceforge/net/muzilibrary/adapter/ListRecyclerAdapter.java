package simcpux.sourceforge.net.muzilibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beisheng.mybslibary.imgsel.cameralibrary.util.ScreenUtils;
import com.beisheng.mybslibary.utils.ScreenUtil;

import java.util.List;

import simcpux.sourceforge.net.muzilibrary.R;


/**
 * Created by Lkn on 2016/5/18.
 */
public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.MyViewHolder> {
    private List<String> newList;
    private Context context;
    private MyItemClickListener mListener;
    private int pos = 0;
    private int max = 4;

    public ListRecyclerAdapter(Context context, List<String> list, MyItemClickListener mListener) {
        this.context = context;
        newList = list;
        this.mListener = mListener;

    }


    public void setMax(int max) {
        this.max = max;
    }

    public void setPos(int pos) {
        this.pos = pos;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bs_item_car_infor, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.title_one.setText(newList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v, position);
            }
        });
        if (pos == position) {
            holder.image_one.setVisibility(View.VISIBLE);
            holder.title_one.setTextAppearance(context, R.style.bs_car_text_style);
        } else {
            holder.image_one.setVisibility(View.INVISIBLE);
            holder.title_one.setTextAppearance(context, R.style.bs_car_text_style2);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ScreenUtil.getInstance(context).dip2px(50));
        if (newList.size() >= max) {
            params.width = (ScreenUtils.getScreenWidth(context) / max);
        } else {
            params.width = (ScreenUtils.getScreenWidth(context) / newList.size());
        }
        holder.layout.setLayoutParams(params);
    }


    @Override
    public int getItemCount() {
        return newList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title_one, image_one;
        LinearLayout layout;

        public MyViewHolder(View view) {
            super(view);
            title_one = view.findViewById(R.id.title_one);
            image_one = view.findViewById(R.id.image_one);
            layout = view.findViewById(R.id.layout);
        }

    }

    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }
}
