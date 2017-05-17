package com.aands.wefamily.Family;

import android.content.Context;
import android.widget.Toast;

import com.aands.wefamily.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;


/**
 * Created by LemonNoel on 2017/5/10.
 * 设置条件有待判断，包括归属地API，天气查询API，空字符串判断
 */

public class Family extends DataSupport {
    private int imageId;
    private String name;
    private String number;
    private String label;
    private String location;
    private String weather;
    private List<Messages> messagesList;

    public Family() {
        this.imageId = R.drawable.default_portrait;
        this.name = "";
        this.number = "";
        this.label = "";
        this.location = "";
        this.weather = "";
        this.messagesList = new ArrayList<>();
    }

    public Family(String name, String number, String label) {
        this.imageId = R.drawable.default_portrait;
        this.name = name;
        this.number = number;
        this.label = label;
        this.location = this.autoLocation(number);
        this.weather = this.autoWeather(this.location);
        this.messagesList = new ArrayList<>();
    }

    public Family(int imageId, String name, String number, String label,
                  String location, String weather, List<Messages> messagesList) {
        this.imageId = imageId;
        this.name = name;
        /*if (number.length() == 13) {
            this.number = number.substring(2);
        } else {
            this.number = number;
        }*/
        this.number = number;
        this.label = label;
        this.location = location;
        this.weather = weather;
        this.messagesList = messagesList;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getLabel() {
        return label;
    }

    public String getLocation() {
        return location;
    }

    public String getWeather() {
        return weather;
    }

    public Messages getLastMessage() {
        if (messagesList.size() > 0) {
            return this.messagesList.get(0);
        } else {
            return null;
        }
    }

    public List<Messages> getMessagesList() {
        return messagesList;
    }

    public void setImageId(Context context, int imageId) {
        if (imageId != NULL) {
            this.imageId = imageId;
        } else {
            Toast.makeText(context, "Photo Change Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void setName(Context context, String name) {
        if (name.equals("")) {
            Toast.makeText(context, "Name Is Empty", Toast.LENGTH_SHORT).show();
        } else {
            this.name = name;
        }
    }

    public void setNumber(Context context, String number) {
        if (number.length() == 11) {
            this.number = number;
        } else if (number.length() == 13 && number.substring(0, 3).equals("+86")) {
            this.number = number.substring(3);
        } else {
            Toast.makeText(context, "Invalid Number", Toast.LENGTH_SHORT).show();
        }
    }

    public void setLabel(Context context, String label) {
        this.label = label;
    }

    public void setLocation(Context context, String location) {
        this.location = location;
    }

    public void setWeather(Context context, String weather) {
        this.weather = weather;
    }

    public void setMessagesList(List<Messages> messagesList) {
        this.messagesList = messagesList;
    }

    public void addMessage(Messages newMessage) {
        messagesList.add(newMessage);
    }

    //TODO 自动从电话获取位置信息
    private String autoLocation(String number) {
        String location = null;
        return location;
    }
    //TODO 根据位置获取天气信息
    private String autoWeather(String location) {
        String weather = null;
        return  weather;
    }

}
