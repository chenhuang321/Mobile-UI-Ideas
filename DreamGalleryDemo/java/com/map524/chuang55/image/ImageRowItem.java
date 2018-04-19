package com.map524.chuang55.image;

/**
 *  Created by Chen Huang on Oct 25, 2016
 */

// a bean class - DATA objects for ListView items
public class ImageRowItem {

    private int imageId;
    private String title;
    private String desc;

    public ImageRowItem(int imageId, String title, String desc) {
        this.imageId = imageId;
        this.title = title;
        this.desc = desc;
    }

    public void setImageId(int imageUrl) {
        this.imageId = imageUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public int getImgId() {
        return imageId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title + "\n" + desc;
    }
}
