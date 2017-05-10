package com.aands.wefamily.Family;

/**
 * Created by LemonNoel on 2017/5/10.
 */

public class Messages {
    private String time;
    private String content;
    private boolean type;

    Messages(String time, String content, boolean type) {
        this.time = time;
        this.content = content;
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public boolean getType() {
        return type;
    }
}
