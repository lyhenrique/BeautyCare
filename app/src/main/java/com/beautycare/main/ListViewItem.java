package com.beautycare.main;

/**
 * Created by owner on 2016/3/10.
 * 目前沒有繼續使用該封裝類了
 */
public class ListViewItem {
    // SearchFragment
    private String num;
    private String category;
    private String name;
    String makeupContent;
    private String brand;
    String brandContent;
    private  int icon;
    private  int flag;

    int item_mark;
    int item_price;
    private  String like;

    String location1;
    String location2;
    String location3;

    String imageURL0;
    String imageURL1;
    String imageURL2;
    String img_content1;
    String img_content2;
    String img_content3;

    public String getNum() {return num;    }
    public String getCategory(){ return category; }
     //SearchFragment
    public ListViewItem(String num, String category ,
                        String name,String brand,String makeupContent,
                        String brandContent,int item_mark,int item_price,
                        String location1,String location2,String location3,
                        String imageURL0,String imageURL1,String imageURL2,
                        String img_content1,String img_content2,String img_content3,
                        int icon,int flag,String like) {
        super();
        this.num = num;
        this.category = category;
        this.name = name;
        this.brand = brand;
        this.makeupContent =makeupContent;
        this.brandContent =brandContent;
        this.item_mark=item_mark;
        this.item_price=item_price;
        this.location1=location1;
        this.location2=location2;
        this.location3=location3;

        this.imageURL0 = imageURL0;
        this.imageURL1 = imageURL1;
        this.imageURL2 = imageURL2;

        this.img_content1= img_content1;
        this.img_content2= img_content2;
        this.img_content3= img_content3;
        this.icon = icon;
        this.flag = flag;
        this.like = like;
    }
    public int getFlag() {
        return flag;
    }
    public String getLike(){ return like; }


    public String getName(){ return name; }

    public String getMakeup_content(){ return makeupContent; }
    public String getBrand_content(){ return brandContent; }
    public String getLocation1(){ return location1; }
    public String getLocation2(){ return location2; }
    public String getLocation3(){ return location3; }
    public String getImg_content1(){ return img_content1; }
    public String getImg_content2(){ return img_content2; }
    public String getImg_content3(){ return img_content3; }


    public int getMark(){ return item_mark; }
    public int getPrice(){ return item_price; }
    public int getIcon(){ return icon; }

    public String getBrand() { return brand; }
    public String getImageURL0() {
        return imageURL0;
    }
    public String getImageURL1() {
        return imageURL1;
    }
    public String getImageURL2() {
        return imageURL2;
    }

    public void setName(String name){ this.name = name; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setNum(String num) {
        this.num = num;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }
    public void setCategory(String category){  this.category = category; }
    public void setLike(String like) {
        this.like = like;
    }
    public void setMakeup_content(String Makeup_content){ this.makeupContent=makeupContent; }
    public void setBrand_content(String Brand_content){ this.brandContent=brandContent; }
    public void setMark(int item_mark) {
        this.item_mark = item_mark;
    }
    public void setPrice(int item_price) {
        this.item_price = item_price;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setImageURL0(String imageURL0) {
        this.imageURL0 = imageURL0;
    }
    public void setImageURL1(String imageURL1) {
        this.imageURL1 = imageURL1;
    }
    public void setImageURL2(String imageURL2) {
        this.imageURL2 = imageURL2;
    }

    public void setLocation1(String location1) {
        this.location1 = location1;
    }
    public void setLocation2(String location2) {
        this.location2 = location2;
    }
    public void setLocation3(String location3) {
        this.location3 = location3;
    }

    public void setImg_content1(String img_content1) {
        this.img_content1 = img_content1;
    }
    public void setImg_content2(String img_content2) {
        this.img_content2 = img_content2;
    }
    public void setImg_content3(String img_content3) {
        this.img_content3 = img_content3;
    }

}
