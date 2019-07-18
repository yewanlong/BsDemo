package com.beisheng.mybslibary.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beisheng.mybslibary.R;
import com.beisheng.mybslibary.imgsel.cameralibrary.util.ScreenUtils;


/**
 * Created by Lkn on 2016/7/25.
 */
public class BSDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private TextView tv_confirm, tv_cancel, tv_title, tv_content;
    private BSDialogListener dialogListener;
    private String title, content, confirm, cancel;

    /**
     * @param context
     * @param title
     * @param content
     * @param confirm
     * @param cancel
     */
    public BSDialog(Context context, String title, String content, String confirm,
                    String cancel, BSDialogListener dialogOnclickListener) {
        super(context, R.style.bs_my_dialog_theme);
        this.context = context;
        this.title = title;
        this.content = content;
        this.confirm = confirm;
        this.dialogListener = dialogOnclickListener;
        this.cancel = cancel;
    }

    /**
     * @param context
     * @param title
     * @param content
     * @param confirm
     */
    public BSDialog(Context context, String title, String content, String confirm
            , BSDialogListener dialogOnclickListener) {
        super(context, R.style.bs_my_dialog_theme);
        this.context = context;
        this.title = title;
        this.content = content;
        this.confirm = confirm;
        this.dialogListener = dialogOnclickListener;
    }

    public void setConfirmBackground(int drawable) {
        tv_confirm.setBackgroundResource(drawable);
    }

    public void setConfirmTextColor(int color) {
        tv_confirm.setTextColor(color);
    }

    public void setCancelBackground(int drawable) {
        tv_cancel.setBackgroundResource(drawable);
    }

    public void setCancelTextColor(int color) {
        tv_cancel.setTextColor(color);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bs_dialog_comment);
        initManager();
        initView();
        initData();
    }


    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        tv_confirm = findViewById(R.id.tv_confirm);
        tv_content = findViewById(R.id.tv_content);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
    }

    private void initData() {
        if (cancel == null) {
            tv_cancel.setVisibility(View.GONE);
        } else {
            tv_cancel.setText(cancel);
        }
        tv_title.setText(title);
        tv_confirm.setText(confirm);
        tv_content.setText(content);

    }

    private void initManager() {
        WindowManager.LayoutParams params = getWindow().getAttributes(); // 获取对话框当前的参数值
        params.width = (int) (ScreenUtils.getScreenWidth(context) * 0.75);
        params.gravity = Gravity.CENTER;
        onWindowAttributesChanged(params);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_confirm) {
            dialogListener.confirmOnclick();
            dismiss();
        } else if (i == R.id.tv_cancel) {
            dialogListener.cancelOnclick();
            dismiss();
        }
    }

    public void setTitle(String title) {
        this.title = title;
        initData();
    }

    public void setContent(String content) {
        this.content = content;
        initData();
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
        initData();
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
        initData();
    }


    public interface BSDialogListener {
        void confirmOnclick();

        void cancelOnclick();
    }

}
