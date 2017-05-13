package com.aands.wefamily.Family;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.aands.wefamily.R;
import com.aands.wefamily.Record.RecordActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LemonNoel on 2017/5/10.
 */

public class FamilyActivity  extends AppCompatActivity {
    private List<Tag> tagsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        //初始化标签记录
        initTags();

        //显示标签列表
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.family_tag);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FamilyAdapter adapter = new FamilyAdapter(tagsList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button recordButton = (Button)findViewById(R.id.record_button);
        recordButton.setBackgroundResource(R.drawable.email);
        recordButton.setEnabled(true);
        Button familyButton = (Button)findViewById(R.id.family_button);
        familyButton.setBackgroundResource(R.drawable.account_filling_b);
        familyButton.setEnabled(false);
    }

    public void StartRecordActivity(View source) {
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);
    }

    public void initTags() {
        tagsList = DataSupport.findAll(Tag.class);
    }
}
