package com.aands.wefamily.Chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aands.wefamily.Contact.ContactActivity;
import com.aands.wefamily.Family.Family;
import com.aands.wefamily.Family.Messages;
import com.aands.wefamily.R;
import com.aands.wefamily.Record.RecordActivity;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.aands.wefamily.Constants.EDIT_CONTACT_PERSON;

/**
 * Created by LemonNoel on 2017/5/14.
 */

//TODO 对话界面
public class ChatActivity extends AppCompatActivity {
    private Family familyItem = new Family();
    private EditText inputText;
    private Button concernMsg, send, back;
    private ImageView detail;
    private RecyclerView msgRecyclerView;
    private ChatAdapter adapter;

    private Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        List<Family> tmpList = DataSupport.where("name = ?", name).find(Family.class);
        if (tmpList.size() > 0) {
            familyItem = tmpList.get(0);

            inputText = (EditText) findViewById(R.id.input_text);
            concernMsg = (Button) findViewById(R.id.concern_msg);
            back = (Button) findViewById(R.id.return_home);
            send = (Button) findViewById(R.id.send);
            detail = (ImageView) findViewById(R.id.detail);
            msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            msgRecyclerView.setLayoutManager(layoutManager);
            adapter = new ChatAdapter(familyItem.getMessagesList());
            msgRecyclerView.setAdapter(adapter);

            concernMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // TODO 添加关怀短信
                }
            });

            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContactActivity.actionStart(getContext(), EDIT_CONTACT_PERSON, name);
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String content = inputText.getText().toString();
                    if (!"".equals(content)) {

                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd hh:mm:ss",
                                Locale.getDefault());
                        Date curDate =  new Date(System.currentTimeMillis());//获取当前时间
                        String time = dateFormat.format(curDate);

                        Messages msg = new Messages(time, content, Messages.TYPE_SEND, true);
                        familyItem.addMessage(msg);
                        adapter.notifyItemInserted(familyItem.getMessagesList().size() - 1);
                        msgRecyclerView.scrollToPosition(familyItem.getMessagesList().size() - 1);
                        inputText.setText("");
                    }
                }
            });

        } else {
            Toast.makeText(this, "Runtime Error", Toast.LENGTH_SHORT);
            Log.d("ChatActivity", "没有联系人" + name);
            finish();
        }
    }

    public static void actionStart(Context context, String name) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }
}
