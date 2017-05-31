package com.aands.wefamily.Contact;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aands.wefamily.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.aands.wefamily.Constants.CHOOSE_PHOTO;
import static com.aands.wefamily.Constants.CROP_PHOTO;
import static com.aands.wefamily.Constants.TAKE_PHOTO;

/**
 * Created by LemonNoel on 2017/5/30.
 */

public class PhotoDialog extends Dialog {

    private View.OnClickListener onNegateClickListener;
    private View.OnClickListener onPositiveClickListener;

    public PhotoDialog(Context context) {
        super(context);
    }

    public PhotoDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.camera_op);

        //设置大小
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth() - 200;  //设置宽度为当前手机屏幕的宽度-100
        getWindow().setAttributes(p);

        ImageView negate = (ImageView) findViewById(R.id.my_album);
        if (onNegateClickListener != null) {
            negate.setVisibility(View.VISIBLE);
            negate.setOnClickListener(onNegateClickListener);
        } else {
            negate.setVisibility(View.GONE);
        }
        ImageView positive = (ImageView) findViewById(R.id.camera);
        if (onPositiveClickListener != null) {
            positive.setVisibility(View.VISIBLE);
            positive.setOnClickListener(onPositiveClickListener);
        } else {
            positive.setVisibility(View.GONE);
        }
    }

    public void setOnPositiveListener(View.OnClickListener onPositiveClickListener) {
        this.onPositiveClickListener = onPositiveClickListener;
    }

    public void setOnNegateListener(View.OnClickListener onNegateClickListener) {
        this.onNegateClickListener = onNegateClickListener;
    }
}
