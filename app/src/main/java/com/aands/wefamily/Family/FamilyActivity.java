package com.aands.wefamily.Family;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aands.wefamily.Contact.ContactActivity;
import com.aands.wefamily.R;
import com.aands.wefamily.Record.RecordActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static com.aands.wefamily.Constants.ADD_CONTACT_PERSON;

/**
 * Created by LemonNoel on 2017/5/10.
 */

public class FamilyActivity  extends AppCompatActivity {
    private List<Tag> tagsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_family);
        setSupportActionBar(toolbar);

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
        familyButton.setBackgroundResource(R.drawable.account_filling);
        familyButton.setEnabled(false);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_family, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                ContactActivity.actionStart(this, ADD_CONTACT_PERSON, "");
                break;
            default:
        }
        return true;
    }

    public void StartRecordActivity(View source) {
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);
    }

    public void initTags() {
        tagsList = DataSupport.findAll(Tag.class);
    }
}
