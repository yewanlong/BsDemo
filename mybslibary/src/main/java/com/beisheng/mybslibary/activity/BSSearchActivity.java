package com.beisheng.mybslibary.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beisheng.mybslibary.R;
import com.beisheng.mybslibary.activity.bs.BSBaseSwipeBackActivity;
import com.beisheng.mybslibary.mode.EventSearch;
import com.beisheng.mybslibary.utils.cache.BSCacheHelper;
import com.beisheng.mybslibary.widget.WarpLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class BSSearchActivity extends BSBaseSwipeBackActivity implements View.OnClickListener {
    private EditText editText;
    private BSCacheHelper<Map<String, String>> cacheHelper = new BSCacheHelper<>();
    private Map<String, String> cacheList = new HashMap<>();
    private WarpLinearLayout warpLayout;
    private ImageView iv_close, iv_delete;
    private ListView listView;
    public static String BS_SEARCH = "BS_SEARCH";
    private String mCache;
    private  int mBgColor;
    private LinearLayout layout_top;
    private TextView tv_right;
    private ImageView iv_back;

    /**
     *
     * @param context
     * @param mCache 缓存名称 eventBus 中也会用到这个参数
     * @param mBgColor 背景颜色
     */
    public static void startSearchActivity(Context context, String mCache, int mBgColor) {
        context.startActivity(new Intent(context, BSSearchActivity.class).
                putExtra("cache", mCache).putExtra("bgColor", mBgColor));
    }

    @Override
    protected int getContentView() {
        return R.layout.bs_activity_search;
    }

    @Override
    protected void initView() {
        mCache = getIntent().getStringExtra("cache");
        mBgColor = getIntent().getIntExtra("bgColor",0);
        if (mCache == null)
            mCache = "";
        editText = $(R.id.et_search);
        warpLayout = $(R.id.warpLayout);
        listView = $(R.id.listView);
        iv_close = $(R.id.iv_close);
        iv_delete = $(R.id.iv_delete);
        layout_top = $(R.id.layout_top);
        tv_right = $(R.id.tv_right);
        iv_back = $(R.id.iv_back);
    }

    @Override
    protected void initData() {
        if (mBgColor!=0){
            setStatusBar(mBgColor);
            layout_top.setBackgroundColor(mBgColor);
            if (isLightColor(mBgColor)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    iv_back.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.bs_black)));
                }
                tv_right.setTextColor(ContextCompat.getColor(this,R.color.bs_black));
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    iv_back.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.bs_white)));
                }
                tv_right.setTextColor(ContextCompat.getColor(this,R.color.bs_white));
            }

        }
        initCache();
    }

    private void initCache() {
        cacheHelper.openObject(BS_SEARCH + mCache, new BSCacheHelper.CacheListener<Map<String, String>>() {
            @Override
            public void onRead(Map<String, String> object) {
                cacheList.clear();
                if (object != null)
                    cacheList.putAll(object);
                initAddCache();
            }
        });
    }

    @Override
    protected void initListener() {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    toSearch(v, editText.getText().toString());
                }
                return false;
            }
        });
        findViewById(R.id.layout_right).setOnClickListener(this);
        iv_delete.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        TextChangeWatcher textWatcher = new TextChangeWatcher();
        editText.addTextChangedListener(textWatcher);
    }

    private class TextChangeWatcher implements TextWatcher {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (editText.getText().toString().length() > 0) {
                listView.setVisibility(View.VISIBLE);
                iv_close.setVisibility(View.VISIBLE);
            } else {
                iv_close.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
            }

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void toSearch(View v, String content) {
        cacheList.put(content, content);
        closeInput(v);
        addCache(cacheList);
        toStart(content);
        initAddCache();
    }

    private void toStart(String content) {
        EventBus.getDefault().post(new EventSearch(mCache, content));
        editText.setText("");
        finish();
    }

    @Override
    protected boolean isApplyEventBus() {
        return false;
    }


    public void addCache(Map<String, String> list) {
        cacheHelper.saveObject(BS_SEARCH + mCache, list);
    }

    private void initAddCache() {
        warpLayout.removeAllViews();
        for (String s : cacheList.keySet()) {
            View view = View.inflate(this, R.layout.bs_item_search_cache, null);
            TextView textView = view.findViewById(R.id.tv_cache);
            textView.setText(cacheList.get(s));
            warpLayout.addView(view);
            textView.setOnClickListener(this);
        }
        if (cacheList.size() == 0) {
            findViewById(R.id.layout_cache).setVisibility(View.GONE);
            iv_delete.setVisibility(View.GONE);
        } else {
            iv_delete.setVisibility(View.VISIBLE);
            findViewById(R.id.layout_cache).setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onClick(View v) {
        closeInput(v);
        int i = v.getId();
        if (i == R.id.iv_delete) {
            cacheList.clear();
            addCache(cacheList);
            warpLayout.removeAllViews();
            findViewById(R.id.layout_cache).setVisibility(View.GONE);
        } else if (i == R.id.iv_close) {
            editText.setText("");

        } else if (i == R.id.tv_cache) {
            toSearch(v, ((TextView) v).getText().toString());

        } else if (i == R.id.layout_right) {
            toSearch(v, editText.getText().toString());

        }
    }

    public static void closeInput(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

}
