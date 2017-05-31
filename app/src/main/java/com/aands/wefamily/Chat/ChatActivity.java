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
    //private EditText contentEditText;
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
                    String text=familyItem.getWeather();
                        if (!familyItem.getWeather().equals("")) {
                            //增加关怀短信内容消息,在输入框加入关怀短信

                            /*int temprature_today = 0;//今天的最低温度
                            int temprature_tomorrow = 0;//明天的最低温度

                            int code_tomorrow = 100;//明天的天气代码
                            CharSequence text = "晴,";//明天的天气状况描述
                            CharSequence care1 = "";//温度变化内容
                            CharSequence care = "";//详细关怀内容
                            if(temprature_today-temprature_tomorrow>=3){
                                care1="气温降低明显，";
                            }*/
                            CharSequence care = "";
                            //根据天气状况代码定制关怀短信
                            if(text.equals("冷")) {//冷
                                care = "天气较冷，记得及时增添衣物哦！";
                            }else if(text.equals("热")){//热
                                care = "天气较热，可适当减少衣物哦！";
                            }else if(text.equals("中雨")){//雨雪天气
                                care = "记得出门带上雨伞哦！";
                            }else if(text.equals("霾")){//雾霾及沙尘暴天气
                                care = "能见度低，雾霾沙尘天气记得出门戴上口罩哦！";
                            }else if(text.equals("飓风")){//大风天气及沙尘暴
                                care = "剧烈天气注意安全，尽量少出门哦！";
                            }else{
                                care = "关注天气变化，请注意身体哦！";
                            }

                            inputText.append("亲爱的"+familyItem.getName() + ", 明天的天气状况为"+ text + care);
                            //关怀短信样板：亲爱的XXX（标签），明天的天气状况为XX（天气状况描述：如晴），XXXXXXX（关怀短信内容：如关注天气变化，请注意身体哦！）
                        }else {
                            Toast.makeText(ChatActivity.this, "天气信息获取错误！", Toast.LENGTH_SHORT).show();
                        }
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
