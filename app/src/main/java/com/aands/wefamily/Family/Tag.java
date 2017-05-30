package com.aands.wefamily.Family;

import com.aands.wefamily.R;

import org.litepal.crud.DataSupport;

/**
 * Created by LemonNoel on 2017/5/10.
 */

public class Tag extends DataSupport{
    private int imageId;
    private String name;
    private String pinyin;
    private String firstLetter;

    public Tag(String name) {
        this.imageId = R.drawable.default_portrait;
        this.name = name;
    }

    public Tag(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getFirstLetter() {
        return firstLetter;
    }
    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }
    public String getPinyin() {
        return pinyin;
    }
    public void setPinyin(String pinyin) {this.pinyin = pinyin;}
}
