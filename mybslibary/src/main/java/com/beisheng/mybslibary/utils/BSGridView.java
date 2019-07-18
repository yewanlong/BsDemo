package com.beisheng.mybslibary.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

/**
 * 无法滚动的GridView
 */
public class BSGridView extends GridView {
    private float mTouchX;
    private float mTouchY;
    private OnTouchBlankPositionListener mTouchBlankPosListener;

    public BSGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BSGridView(Context context) {
        super(context);
    }

    public BSGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTouchBlankPosListener != null) {
            if (!isEnabled()) {
                return isClickable() || isLongClickable();
            }

            int action = event.getActionMasked();
            float x = event.getX();
            float y = event.getY();
            final int motionPosition = pointToPosition((int) x, (int) y);
            if (motionPosition == INVALID_POSITION) {
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchX = x;
                        mTouchY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(mTouchX - x) < 10 && Math.abs(mTouchY - y) < 10) {
                            mTouchBlankPosListener.onTouchBlank(event, this);
                        }
                        break;

                    default:
                        break;
                }
            }
        }
        return super.onTouchEvent(event);
    }


    /**
     * 设置GridView的空白区域的触摸事件
     *
     * @param listener
     */
    public void setOnTouchBlankPositionListener(OnTouchBlankPositionListener listener) {
        mTouchBlankPosListener = listener;
    }

    public interface OnTouchBlankPositionListener {
        void onTouchBlank(MotionEvent event, View view);
    }
}
