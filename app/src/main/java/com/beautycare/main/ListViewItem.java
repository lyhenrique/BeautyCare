package com.beautycare.main;

/**
 * Created by owner on 2016/3/10.
 */
public class ListViewItem {
    // SearchFragment
    private String num;
    private  String imageURL;
    private String name;
    private String brand;
    private  int icon;
    private  int flag;
    private  String like;
    // SearchFragment
    public ListViewItem(String num, String name,String brand,String imageURL,int icon,int flag,String like) {
        super();
        this.num = num;
        this.name = name;
        this.brand = brand;
        this.imageURL = imageURL;
        this.icon = icon;
        this.flag = flag;
        this.like = like;

    }
    public int getFlag() {
        return flag;
    }

    public String getLike(){ return like; }

    public String getNum() {
        return num;
    }

    public String getName(){ return name; }

    public int getIcon(){ return icon; }

    public String getBrand() { return brand; }

    public String getImageURL() {
        return imageURL;
    }

    public void getName(String name){ this.name = name; }

    public void getBrand(String brand) { this.brand = brand; }

    public void setNum(String num) {
        this.num = num;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
