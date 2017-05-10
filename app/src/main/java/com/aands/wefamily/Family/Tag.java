package com.aands.wefamily.Family;

/**
 * Created by LemonNoel on 2017/5/10.
 */

public class Tag {
    private int imageId;
    private String name;

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
}
