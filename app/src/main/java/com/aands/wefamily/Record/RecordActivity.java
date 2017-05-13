package com.aands.wefamily.Record;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aands.wefamily.Family.Family;
import com.aands.wefamily.Family.FamilyActivity;
import com.aands.wefamily.Family.Messages;
import com.aands.wefamily.Family.Tag;
import com.aands.wefamily.R;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecordActivity extends AppCompatActivity {
    private List<Records> recordsList = new ArrayList<>();
    private List<Family> familyList = new ArrayList<>();
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static final int RE_READ_CONTACTS = 1;
    private static final int RE_READ_SMS = 2;
    private static final String[] contactKeyWords = new String[]{
            "爷", "奶", "爸", "妈", "哥", "姐", "弟", "妹", "王"};
    private static final String[] labelSet = new String[] {
            "祖辈", "父辈", "同辈"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        //安装应用时根据本地电话本初始化联系人列表
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstLaunch = pref.getBoolean("first_launch", true);
        if (firstLaunch) {
            //创建数据库
            LitePal.getDatabase();
            //读取联系人
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.
                    READ_CONTACTS}, RE_READ_CONTACTS);
            } else {
                readContacts();
            }
            editor = pref.edit();
            editor.putBoolean("first_launch", false);
            editor.apply();
            //保存默认标签
            for (int i = 0; i < labelSet.length; i++) {
                Tag tag = new Tag(labelSet[i]);
                tag.save();
            }
            //初始化联系记录
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.
                        READ_SMS}, RE_READ_SMS);
            } else {
                initRecords();
            }
        } else {
            recordsList = DataSupport.findAll(Records.class);
        }

        //显示联系记录
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.chat_record);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ChatRecordAdapter adapter = new ChatRecordAdapter(recordsList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button recordButton = (Button)findViewById(R.id.record_button);
        recordButton.setBackgroundResource(R.drawable.email_filling_b);
        recordButton.setEnabled(false);
        Button familyButton = (Button)findViewById(R.id.family_button);
        familyButton.setBackgroundResource(R.drawable.account);
        familyButton.setEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case RE_READ_CONTACTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.
                        PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    Toast.makeText(this, "Read Contacts Permission Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case RE_READ_SMS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.
                        PERMISSION_GRANTED) {
                    initRecords();
                } else {
                    Toast.makeText(this, "Read SMS Permission Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void readContacts() {
        Cursor cursor = null;
        try {
            //查询联系人数据
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.
                Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String displayName = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    for (int i = 0; i < contactKeyWords.length; ++i) {
                        if (displayName.contains(contactKeyWords[i])) {
                            String displayNumber = cursor.getString(cursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            String label = null;
                            if (i < 2) {
                                label = labelSet[0];
                            } else if (i < 4) {
                                label = labelSet[1];
                            } else {
                                label = labelSet[2];
                            }
                            Family family = new Family(displayName, displayNumber, label);
                            familyList.add(family);
                            family.save();
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void initRecords() {
        final String SMS_URI_ALL = "content://sms/";
        //查联系人的信息
        for (int i = 0; i < familyList.size(); ++i) {
            Cursor cursor = null;
            try {
                Uri uri = Uri.parse(SMS_URI_ALL);
                String[] projection = new String[]{"address", "date", "read", "type", "body"};
                String selections = "address = " + familyList.get(i).getNumber();
                cursor = getContentResolver().query(uri, projection, selections, null, "date desc");

                if (cursor != null) {
                    int index_address = cursor.getColumnIndex("address");
                    int index_date = cursor.getColumnIndex("date");
                    int index_read = cursor.getColumnIndex("read");
                    int index_type = cursor.getColumnIndex("type");
                    int index_body = cursor.getColumnIndex("body");

                    cursor.moveToFirst();
                    Log.d("initRecords", familyList.get(i).getName());
                    for (int j = 0; j < cursor.getCount(); j++, cursor.moveToNext()) {
                        String strAddress = cursor.getString(index_address);
                        Log.d("initRecord", strAddress);
                        String strBody = cursor.getString(index_body);
                        int intType = cursor.getInt(index_type);
                        int intRead = cursor.getInt(index_read);
                        long longDate = cursor.getLong(index_date);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd hh:mm:ss",
                                Locale.getDefault());
                        Date date = new Date(longDate);
                        String strDate = dateFormat.format(date);

                        Log.d("initRecord", strDate);
                        Log.d("initRecord", strBody);

                        familyList.get(i).addMessage(new Messages(strDate, strBody, intRead, true, intType));
                    }
                    Messages tmpMessage = familyList.get(i).getLastMessage();
                    if (tmpMessage != null) {
                        String content = tmpMessage.getContent();
                        if (content.length() > 20) { content = content.substring(0, 19) + "..."; }
                        Records record = new Records(familyList.get(i).getName(), tmpMessage.getTime(),
                                content, familyList.get(i).getImageId());
                        recordsList.add(record);
                        record.save();
                    }
                    familyList.get(i).save();
                    Log.d("initRecord", Integer.toString(recordsList.size()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }

    public void StartFamilyActivity(View source) {
        Intent intent = new Intent(this, FamilyActivity.class);
        startActivity(intent);
    }
}

