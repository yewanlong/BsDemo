package simcpux.sourceforge.net.muzilibrary.activity.choose;

import android.graphics.Color;
import android.view.View;

import com.beisheng.mybslibary.activity.BSDialog;
import com.beisheng.mybslibary.utils.BSVToast;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import simcpux.sourceforge.net.muzilibrary.R;
import simcpux.sourceforge.net.muzilibrary.activity.BaseActivity;

//https://github.com/Bigkoo/Android-PickerView
public class ChooseActivity extends BaseActivity implements View.OnClickListener {
    private OptionsPickerView pvOptions, pvOptions2;
    private ArrayList<String> options1Items01 = new ArrayList<>();
    private ArrayList<ArrayList<String>> options1Items2 = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_choose;
    }

    @Override
    protected void initView() {
        options1Items01.add("广东");
        options1Items01.add("湖南");
        options1Items01.add("广西");
        showPickerView();
        initTimePicker();
    }

    @Override
    protected void initData() {
        titleView().setText("选择弹框");
        ArrayList<String> options2Items_01 = new ArrayList<>();
        options2Items_01.add("广州");
        options2Items_01.add("佛山");
        options2Items_01.add("东莞");
        options2Items_01.add("珠海");
        ArrayList<String> options2Items_02 = new ArrayList<>();
        options2Items_02.add("长沙");
        options2Items_02.add("岳阳");
        options2Items_02.add("株洲");
        options2Items_02.add("衡阳");
        ArrayList<String> options2Items_03 = new ArrayList<>();
        options2Items_03.add("桂林");
        options2Items_03.add("玉林");
        options1Items2.add(options2Items_01);
        options1Items2.add(options2Items_02);
        options1Items2.add(options2Items_03);
        showPickerView2();
    }

    @Override
    protected void initListener() {
        findViewById(R.id.bottom1).setOnClickListener(this);
        findViewById(R.id.bottom2).setOnClickListener(this);
        findViewById(R.id.bottom3).setOnClickListener(this);
        findViewById(R.id.bottom4).setOnClickListener(this);
    }

    @Override
    protected boolean isApplyEventBus() {
        return false;
    }


    private void showPickerView() {// 弹出选择器
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                BSVToast.showShort(options1Items01.get(options1));
            }
        })
                .setTitleText("单选")
                .setDividerColor(getResources().getColor(R.color.bs_grey))
                .setTextColorCenter(getResources().getColor(R.color.bs_gray)) //设置选中项文字颜色
                .setContentTextSize(18)
                .setSubmitColor(getResources().getColor(R.color.colorPrimary))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.bs_grey))//取消按钮文字颜色
                .build();
        pvOptions.setPicker(options1Items01);
    }

    private void showPickerView2() {// 弹出选择器
        pvOptions2 = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                BSVToast.showShort(options1Items01.get(options1)
                        + options1Items2.get(options1).get(options2));
            }
        })
                .setTitleText("多选")
                .setDividerColor(getResources().getColor(R.color.bs_grey))
                .setTextColorCenter(getResources().getColor(R.color.bs_gray)) //设置选中项文字颜色
                .setContentTextSize(18).isRestoreItem(true)
                .setLabels("省", "市", null)//设置选择的三级单位
                .setSubmitColor(getResources().getColor(R.color.colorPrimary))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.bs_grey))//取消按钮文字颜色
                .build();
        pvOptions2.setPicker(options1Items01, options1Items2);
    }

    private TimePickerView pvTime;

    private void initTimePicker() {
        Calendar selectedDate = Calendar.getInstance();
        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                BSVToast.showShort(getTime(date));
            }
        }).setTitleText("时间选择")
                .setDate(selectedDate)
                .setType(new boolean[]{true, true, true, true, false, false})
                .setDividerColor(getResources().getColor(R.color.bs_grey))
                .setTextColorCenter(getResources().getColor(R.color.bs_gray)) //设置选中项文字颜色
                .setContentTextSize(18).isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setSubmitColor(getResources().getColor(R.color.colorPrimary))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.bs_grey))//取消按钮文字颜色
                .build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom1:
                pvOptions.show();
                break;
            case R.id.bottom2:
                pvOptions2.show();
                break;
            case R.id.bottom3:
                pvTime.show();
                break;
            case R.id.bottom4:
                BSDialog dialog = new BSDialog(this, "提示", "内容你确定要这么做吗",
                        "确定", "取消", new BSDialog.BSDialogListener() {
                    @Override
                    public void confirmOnclick() {
                    }

                    @Override
                    public void cancelOnclick() {
                    }
                });
                dialog.show();

                break;

        }
    }

    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        return format.format(date);
    }
}
