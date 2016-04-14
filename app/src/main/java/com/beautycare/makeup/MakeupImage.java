package com.beautycare.makeup;

/**
 * Created by ShenLing on 2016/3/12.
 */
public class MakeupImage {
    private String image_url;
    private String image_content;

    public MakeupImage(String image_url, String image_content) {
        this.image_url = image_url;
        this.image_content = image_content;
    }

    public String getImage_url(){
        return image_url;
    }
    public String getImage_content(){
        return image_content;
    }
}
