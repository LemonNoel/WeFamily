package com.aands.wefamily.Record;

import org.litepal.crud.DataSupport;

/**
 * Created by LemonNoel on 2017/5/10.
 */

public class Records extends DataSupport {
    private int imageId;
    private String name;
    private String lastTime;
    private String lastMessage;

    public Records(String name, String lastTime, String lastMessage, int imageId) {
        this.name = name;
        this.lastTime = lastTime;
        this.lastMessage = lastMessage;
        this.imageId = imageId;
    }

    public int getImageId() {
        return  imageId;
    }

    public String getName() {
        return  name;
    }

    public String getLastTime() {
        return lastTime;
    }

    public String getLastMessage() {
        return  lastMessage;
    }
}
