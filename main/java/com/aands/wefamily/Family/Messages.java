package com.aands.wefamily.Family;

/**
 * Created by LemonNoel on 2017/5/10.
 */

public class Messages {
    private String time;
    private String content;
    private int type;  //1 receive 2 send
    private int read;  //0 not 1 read
    private boolean isSMS;

    public  Messages(String time, String content, int type, boolean isSMS, int read) {
        this.time = time;
        this.content = content;
        this.type = type;
        this.isSMS = isSMS;
        this.read = read;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public boolean getIsSMS() {
        return isSMS;
    }

    public  int getRead() {
        return read;
    }
}
