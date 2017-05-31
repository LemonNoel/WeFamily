package com.aands.wefamily.Family;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.aands.wefamily.Contact.ContactActivity;
import com.aands.wefamily.Person.PersonActivity;
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

    Context getContext() {
        return this;
    }

    private void toNewActivity(int position){
        Intent i = null;
        switch (position){
            case 0:
                i = new Intent(FamilyActivity.this,PersonActivity.class);
                break;
            case 1:
                i = new Intent(FamilyActivity.this, com.aands.wefamily.Person2.PersonActivity.class);
                break;
            case 2:
                i = new Intent(FamilyActivity.this, com.aands.wefamily.Person2.PersonActivity.class);
                break;
        }
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_family);
        setSupportActionBar(toolbar);
        //final ListView listView = (ListView) findViewById(R.id.lv);
        //初始化标签记录
        initTags();

        //显示标签列表
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.family_tag);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);




        final FamilyAdapter adapter = new FamilyAdapter(tagsList);
        //recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new FamilyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Intent intent = new Intent(FamilyActivity.this, PersonActivity.class);
                //intent.putExtra("name", tagsList.get(position).getName());
                //startActivity(intent);
                toNewActivity(position);
            }
        });
        recyclerView.setAdapter(adapter);


        /*Button test = (Button)findViewById(R.id.button);

        test.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FamilyActivity.this, PersonActivity.class);
                startActivity(intent);
                finish();//停止当前的Activity,如果不写,则按返回键会跳转回原来的Activity
            }

        });
        TextView textView = (TextView) findViewById(R.id.show_letter_in_center);
        final LetterIndexView letterIndexView = (LetterIndexView) findViewById(R.id.letter_index_view);
        letterIndexView.setTextViewDialog(textView);
        letterIndexView.setUpdateListView(new LetterIndexView.UpdateListView() {
            @Override
            public void updateListView(String currentChar) {
                int positionForSection = adapter.getPositionForSection(currentChar.charAt(0));
                listView.setSelection(positionForSection);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int sectionForPosition = adapter.getSectionForPosition(firstVisibleItem);
                letterIndexView.updateLetterIndexView(sectionForPosition);
            }
        });*/
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
        /*for(Tag tagName : tagsList)
        {
            String convert = ChineseToPinyinHelper.getInstance().getPinyin(tagName.getName()).toUpperCase();
            tagName.setPinyin(convert);
            String substring = convert.substring(0, 1);
            if (substring.matches("[A-Z]")) {
                tagName.setFirstLetter(substring);
            }else{
                tagName.setFirstLetter("#");
            }
        }

        Collections.sort(tagsList, new Comparator<Tag>() {
            @Override
            public int compare(Tag lhs, Tag rhs) {
                if (lhs.getFirstLetter().contains("#")) {
                    return 1;
                } else if (rhs.getFirstLetter().contains("#")) {
                    return -1;
                }else{
                    return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
                }
            }
        });*/
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FamilyActivity.class);
        context.startActivity(intent);
    }
}
