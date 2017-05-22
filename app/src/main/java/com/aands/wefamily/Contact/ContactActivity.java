package com.aands.wefamily.Contact;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.aands.wefamily.Family.Family;
import com.aands.wefamily.R;
import com.aands.wefamily.Record.MyDialog;

import org.litepal.crud.DataSupport;

import java.util.List;

import static com.aands.wefamily.Constants.ADD_CONTACT_PERSON;
import static com.aands.wefamily.Constants.EDIT_CONTACT_PERSON;

/**
 * Created by LemonNoel on 2017/5/14.
 */

public class ContactActivity extends AppCompatActivity {
    private Family family = null;
    private Context mContext = this;
    private Button save, delete, back;
    private ImageView photo;
    private EditText name, label, number, location, weather;

    private Context getContext() {
        return mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        back = (Button) findViewById(R.id.return_home);
        save = (Button) findViewById(R.id.save_contact);
        delete = (Button) findViewById(R.id.delete_contact);
        photo = (ImageView) findViewById(R.id.contact_photo);
        name = (EditText) findViewById(R.id.contact_name);
        label = (EditText) findViewById(R.id.contact_label);
        number = (EditText) findViewById(R.id.contact_number);
        location = (EditText) findViewById(R.id.contact_location);
        weather = (EditText) findViewById(R.id.contact_weather);

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", ADD_CONTACT_PERSON);
        String contact_name = intent.getStringExtra("name");

        if (type == EDIT_CONTACT_PERSON){
            List<Family> tmpFamily = DataSupport.where("name = ?", contact_name).find(Family.class);
            if (tmpFamily.isEmpty()) {
                family = tmpFamily.get(0);
                name.setText(family.getName());
                label.setText(family.getLabel());
                number.setText(family.getNumber());
                location.setText(family.getLocation());
                weather.setText(family.getWeather());
                delete.setEnabled(true);
            } else {
                family = new Family();
                name.setHint(R.string.default_name);
                label.setHint(R.string.default_label);
                number.setHint(R.string.default_number);
                location.setHint(R.string.default_location);
                weather.setHint(R.string.default_weather);
                delete.setEnabled(false);
            }
        } else {
            family = new Family();
            name.setHint(R.string.default_name);
            label.setHint(R.string.default_label);
            number.setHint(R.string.default_number);
            location.setHint(R.string.default_location);
            weather.setHint(R.string.default_weather);
            delete.setEnabled(false);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (save.isEnabled()) {
                    family.save();
                    finish();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delete.isEnabled()) {
                    final MyDialog myDialog = new MyDialog(getContext(), R.style.MyDialog);//设置自定义背景
                    myDialog.setTitle("删除");
                    myDialog.setMsg("确认删除" + name + "？");
                    myDialog.setOnNegateListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                        }
                    });
                    myDialog.setOnPositiveListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            family.delete();
                            myDialog.dismiss();
                            finish();
                        }
                    });
                    myDialog.show();
                }
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 调用本机相册或者摄像头
            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  }

            @Override
            public void afterTextChanged(Editable s) {
                String tmpStr = name.getText().toString();
                if (!tmpStr.isEmpty()) {
                    family.setName(getContext(), tmpStr);
                    save.setEnabled(true);
                }
            }
        });
        label.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  }

            @Override
            public void afterTextChanged(Editable s) {
                String tmpStr = label.getText().toString();
                if (!tmpStr.isEmpty()) {
                    family.setLabel(getContext(), tmpStr);
                    save.setEnabled(true);
                }
            }
        });
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  }

            @Override
            public void afterTextChanged(Editable s) {
                String tmpStr = number.getText().toString();
                if (!tmpStr.isEmpty()) {
                    family.setNumber(getContext(), tmpStr);
                    save.setEnabled(true);
                }
            }
        });
        location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  }

            @Override
            public void afterTextChanged(Editable s) {
                String tmpStr = location.getText().toString();
                if (!tmpStr.isEmpty()) {
                    family.setLocation(getContext(), tmpStr);
                    save.setEnabled(true);
                }
            }
        });
        weather.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  }

            @Override
            public void afterTextChanged(Editable s) {
                String tmpStr = weather.getText().toString();
                if (!tmpStr.isEmpty()) {
                    family.setWeather(getContext(), tmpStr);
                    save.setEnabled(true);
                }
            }
        });
    }

    public static void actionStart(Context context, int type, String name) {
        Intent intent = new Intent(context, ContactActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }
}
