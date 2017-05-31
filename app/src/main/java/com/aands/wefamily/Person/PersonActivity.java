package com.aands.wefamily.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.aands.wefamily.Contact.ContactActivity;
import com.aands.wefamily.Family.Family;
import com.aands.wefamily.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static com.aands.wefamily.Constants.EDIT_CONTACT_PERSON;

/**
 * Created by renwendi on 17/5/31.
 */

public class PersonActivity extends AppCompatActivity {
    private Context getContext() {
        return this;
    }
    private Button test;
    private Family familyItem = new Family();

    /*public void btnclick(View v){
        Intent intent=new Intent(PersonActivity.this,ContactActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name", "大伯");
        startActivity(intent);
    }*/

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
        /*test = (Button) findViewById(R.id.button_person);
        test.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(PersonActivity.this,ContactActivity.class);
                intent.putExtra("Name","大伯");
                startActivity(intent);
            }
        });*/
        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        List<Family> tmpList = DataSupport.where("name = ?", name).find(Family.class);
        if (tmpList.size() > 0) {
            familyItem = tmpList.get(0);
            test = (Button) findViewById(R.id.button_person);
            test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContactActivity.actionStart(getContext(), EDIT_CONTACT_PERSON, name);
                }
            });
        }


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

    }
}

