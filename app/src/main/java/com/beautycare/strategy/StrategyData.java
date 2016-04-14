package com.beautycare.strategy;

import java.util.ArrayList;

/**
 * Created by chenyangyingjie on 16/3/12.
 */
public class StrategyData {
    private int ID;
    private String category;
    private String itemTitle;
    private String itemAbstract;
    private String itemDetails;
    private int mark;
    private int like;

    private ArrayList<String> flipper = new ArrayList<String>();;
    private String more;
    private int iconSpread;
    private int iconShrink_up;
    //private ArrayList<MakeupImage>images = new ArrayList<MakeupImage>();


    //set methods
    public void setID(int ID){this.ID = ID;}
    public void setCategory(String category){this.category=category;}
    public void setItemTitle(String itemTitle){this.itemTitle = itemTitle; }
    public void setItemAbstract(String itemAbstract){ this.itemAbstract = itemAbstract;}
    public void setItemDetails(String itemDetails){ this.itemDetails = itemDetails;}
    public void setMark(int mark){this.mark = mark; }
    //public void setImages(String image_url, String image_content){this.images.add(new MakeupImage(image_url,image_content)); }
    public void setLike(int like){this.like = like;}

    public void setFlipper(String flipper){this.flipper.add(flipper); }
    public void setMore(String more){this.more = more;}
    public void setIconSpread(int iconSpread){this.iconSpread = iconSpread;}
    public void setIconShrink_up(int iconShrink_up){this.iconShrink_up = iconShrink_up;}


    //getData
    public int getID() {return ID;}
    public String getCategory() {return category;}
    public String getItemTitle(){return itemTitle;}
    public String getItemAbstract(){return itemAbstract;}
    public String getItemDetails(){return itemDetails;}
    public int getMark(){return mark;}
    //public ArrayList<MakeupImage> getImages(){return images;}
    public int getLike(){return like;}

    public ArrayList<String> getFlipper(){return flipper;}
    public String getMore() { return  more; }
    public int getIconSpread() {return iconSpread; }
    public int getIconShrink_up() {return iconShrink_up; }

}
