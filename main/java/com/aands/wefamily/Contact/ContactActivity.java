package com.aands.wefamily.Contact;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aands.wefamily.Family.Family;
import com.aands.wefamily.R;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by LemonNoel on 2017/5/14.
 */

//TODO 联系人界面逻辑
//已经读取所有联系人信息，可以直接用
public class ContactActivity extends AppCompatActivity {
    private List<Family> familyList = DataSupport.findAll(Family.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }
}
