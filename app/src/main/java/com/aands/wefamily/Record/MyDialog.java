package com.aands.wefamily.Record;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aands.wefamily.R;

public class MyDialog extends Dialog {
    private String title;
    private String message;
    private View.OnClickListener onNegateClickListener;
    private View.OnClickListener onPositiveClickListener;

    public MyDialog(Context context) {
        super(context);
    }

    public MyDialog(Context context, int theme) {
        super(context, theme);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog);

        //设置大小
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth() - 100;  //设置宽度为当前手机屏幕的宽度-100
        getWindow().setAttributes(p);

        TextView textTitle = (TextView) findViewById(R.id.callback_dialog_tv_title);
        if (!TextUtils.isEmpty(title)) {
            textTitle.setVisibility(View.VISIBLE);
            textTitle.setText(title);
        } else {
            textTitle.setVisibility(View.GONE);
        }
        TextView textMsg = (TextView) findViewById(R.id.callback_dialog_tv_msg);
        if (!TextUtils.isEmpty(message)) {
            textMsg.setVisibility(View.VISIBLE);
            textMsg.setText(message);
        }
        TextView divider = (TextView) findViewById(R.id.callback_dialog_tv_dividers);
        TextView negate = (TextView) findViewById(R.id.callback_dialog_tv_negate);
        if (onNegateClickListener != null) {
            negate.setVisibility(View.VISIBLE);
            negate.setOnClickListener(onNegateClickListener);
        } else {
            divider.setVisibility(View.GONE);
            negate.setVisibility(View.GONE);
        }
        TextView positive = (TextView) findViewById(R.id.callback_dialog_tv_positive);
        if (onPositiveClickListener != null) {
            positive.setVisibility(View.VISIBLE);
            positive.setOnClickListener(onPositiveClickListener);
        } else {
            divider.setVisibility(View.GONE);
            positive.setVisibility(View.GONE);
        }

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMsg(String message) {
        this.message = message;
    }

    public void setOnPositiveListener(View.OnClickListener onPositiveClickListener) {
        this.onPositiveClickListener = onPositiveClickListener;
    }

    public void setOnNegateListener(View.OnClickListener onNegateClickListener) {
        this.onNegateClickListener = onNegateClickListener;
    }
}