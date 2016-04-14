package com.beautycare.makeup;

import java.util.ArrayList;

/**
 * Created by ShenLing on 2016/3/12.
 */
public class MakeupData {
    private int ID;
    private String category;
    private String makeup_name;
    private String makeup_content;
    private String brand;
    private String brand_content;
    private int mark;
    private int price;
    private int like;

    private ArrayList<String>location = new ArrayList<String>();

    private ArrayList<MakeupImage>images = new ArrayList<MakeupImage>();


    //set methods
    public void setID(int ID){this.ID = ID;}
    public void setCategory(String category){this.category=category;}
    public void setMakeup_name(String makeup_name){this.makeup_name = makeup_name; }
    public void setMakeup_content(String makeup_content){ this.makeup_content = makeup_content;}
    public void setBrand(String brand){this.brand = brand;}
    public void setBrand_content(String brand_content){this.brand_content = brand_content; }
    public void setMark(int mark){this.mark = mark; }
    public void setPrice(int price){this.price = price;}
    public void setLocation(String location){this.location.add(location); }
    public void setImages(String image_url, String image_content){this.images.add(new MakeupImage(image_url,image_content)); }
    public void setLike(int like){this.like = like;}

    //getData
    public int getID() {return ID;}
    public String getCategory() {return category;}
    public String getMakeup_name(){return makeup_name;}
    public String getMakeup_content(){return makeup_content;}
    public String getBrand(){return brand;}
    public String getBrand_content(){return brand_content;}
    public int getMark(){return mark;}
    public int getPrice(){return price;}
    public ArrayList<String> getLocation() {return location;}
    public ArrayList<MakeupImage> getImages(){return images;}
    public int getLike(){return like;}
}
