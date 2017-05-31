package com.aands.wefamily.Chat;

//reference http://blog.csdn.net/duchunchao/article/details/6093776

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission_group.SMS;
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
    private List<Messages> mMessages;

    String SENT_SMS_ACTION="SENT_SMS_ACTION";
    String DELIVERED_SMS_ACTION="DELIVERED_SMS_ACTION";

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
        if (!tmpList.isEmpty()) {
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

                        //TODO
                        //doSendSMSTo(familyItem.getNumber(), content);
                        if (validate(familyItem.getNumber(), content)) {
                            sendSMS(familyItem.getNumber(), content);
                        } else {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });

            String SENT_SMS_ACTION = "SENT_SMS_ACTION";
            Intent sentIntent = new Intent(SENT_SMS_ACTION);
            PendingIntent sentPI = PendingIntent.getBroadcast(getContext(), 0, sentIntent, 0);

            getContext().registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            break;
                    }
                }
            }, new IntentFilter(SENT_SMS_ACTION));

        } else {
            Toast.makeText(this, "Runtime Error", Toast.LENGTH_SHORT);
            Log.d("ChatActivity", "没有联系人" + name);
            finish();
        }
    }

    public void doSendSMSTo(String phoneNumber, String message) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+phoneNumber));
            intent.putExtra("sms_body", message);
            startActivity(intent);
        }
    }

    private void sendSMS(String phoneNumber, String message) {

        //create the sentIntent parameter
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent,
                0);
        // create the deliverIntent parameter
        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        PendingIntent deliverPI = PendingIntent.getBroadcast(this, 0,
                deliverIntent, 0);

        SmsManager sms = SmsManager.getDefault();
        if (message.length() > 70) {
            List<String> msgs = sms.divideMessage(message);
            for (String msg : msgs) {
                sms.sendTextMessage(phoneNumber, null, msg, sentPI, deliverPI);
            }
        } else {
            sms.sendTextMessage(phoneNumber, null, message, sentPI, deliverPI);
        }

        //register the Broadcast Receivers
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context _context,Intent _intent)
            {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent success actions",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        break;
                }
            }
        }, new IntentFilter(SENT_SMS_ACTION));
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context _context,Intent _intent) {
                Toast.makeText(getBaseContext(), "SMS delivered actions",
                        Toast.LENGTH_SHORT).show();
            }
        }, new IntentFilter(DELIVERED_SMS_ACTION));
    }

    public boolean validate(String telNo, String content){

        if((null==telNo)||("".equals(telNo.trim()))){
            Toast.makeText(this, "please input the telephone No.!",Toast.LENGTH_LONG).show();
            return false;
        }
        if((null==content)||("".equals(content.trim()))){
            Toast.makeText(this, "please input the message content!",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    public static void actionStart(Context context, String name) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }
}
