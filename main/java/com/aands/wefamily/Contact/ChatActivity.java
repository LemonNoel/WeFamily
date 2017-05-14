package com.aands.wefamily.Contact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.aands.wefamily.Family.Family;
import com.aands.wefamily.R;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by LemonNoel on 2017/5/14.
 */

//TODO 对话界面
public class ChatActivity extends AppCompatActivity {
    private Family familyItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        List<Family> tmpList = DataSupport.where("name = ", name).find(Family.class);
        if (tmpList.size() > 0) {
            familyItem = tmpList.get(0);

            //TODO 执行到这里表示联系人信息已取，在这里添加逻辑代码即可

        } else {
            Toast.makeText(this, "Runtime Error", Toast.LENGTH_SHORT);
            Log.d("ChatActivity", "没有联系人" + name);
            finish();
        }
    }
}
