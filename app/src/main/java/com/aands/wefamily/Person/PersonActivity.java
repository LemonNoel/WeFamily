package com.aands.wefamily.Person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.aands.wefamily.Contact.ContactActivity;
import com.aands.wefamily.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renwendi on 17/5/31.
 */

public class PersonActivity extends Activity {
    private final static String[] data = {"大伯","小舅"};

    //创建数据源.
    Zhang[] data2 = new Zhang[]{
            new Zhang("大伯","18826012345","广州","晴"),
            new Zhang("小舅","18826012983","长沙","小雨"),
    };
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        ListView listview = (ListView)findViewById(R.id.listview);


        /*
         * 第一种：普通字符串
         */
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,data);

        /*
         * 第二种：文艺类对象
         */
        ArrayAdapter<Zhang> adapter2 = new ArrayAdapter<Zhang>(this,
                android.R.layout.simple_list_item_1,data2);

        /*
         * 第三种：自定义适配器
         */
        ListAdapter adapter3 = new ListAdapter(this, R.layout.personlistview,data2) ;//okay, the resource id is passed.

        listview.setAdapter(adapter3);


        /*Button test = (Button)findViewById(R.id.button_person);

        test.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PersonActivity.this, ContactActivity.class);
                startActivity(intent);
                finish();//停止当前的Activity,如果不写,则按返回键会跳转回原来的Activity
            }

        });*/
    }
}
