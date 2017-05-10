package com.aands.wefamily.Record;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.aands.wefamily.Family.FamilyActivity;
import com.aands.wefamily.R;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    private List<Records> recordsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        initRecords(); //初始化联系记录
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.chat_record);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ChatRecordAdapter adapter = new ChatRecordAdapter(recordsList);
        recyclerView.setAdapter(adapter);
    }

    public void initRecords() {
        for (int i = 0; i < 10; ++i) {
            Records brother = new Records("Brother", "15:09", "周末别忘了哈~", R.drawable.account_b);
            recordsList.add(brother);
            Records sister = new Records("Sister", "12:00", "我也去吃饭233", R.drawable.account_b);
            recordsList.add(sister);
        }
    }

    public void StartRecordActivity(View source) {
        Intent intent = new Intent(this, RecordActivity.class);
        Button recordButton = (Button)findViewById(R.id.record_button);
        recordButton.setBackgroundResource(R.drawable.email_b);
        Button familyButton = (Button)findViewById(R.id.family_button);
        familyButton.setBackgroundResource(R.drawable.account);
        startActivity(intent);
    }

    public void StartFamilyActivity(View source) {
        Intent intent = new Intent(this, FamilyActivity.class);
        Button recordButton = (Button)findViewById(R.id.record_button);
        recordButton.setBackgroundResource(R.drawable.email);
        Button familyButton = (Button)findViewById(R.id.family_button);
        familyButton.setBackgroundResource(R.drawable.account_b);
        startActivity(intent);
    }
}

