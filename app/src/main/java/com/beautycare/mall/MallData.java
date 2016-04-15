package com.beautycare.mall;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by peter on 3/6/2016.
 */
public final class MallData {

    private String mallLogoURL;
    private String mallContent;
    private String mallName;
    private String ID;
    private LatLng latLng;
    private ArrayList<String> floorListHeader;
    private HashMap<String, ArrayList<String>> ShopListData;

//    public Data(String tmpid, String tmpurl, String tmpcontent, String tmptitle){
//
//        mallLogoURL=tmpurl;
//        mallContent=tmpcontent;
//        mallName=tmptitle;
//        ID=tmpid;
//    }


    public void setShopListData(HashMap<String, ArrayList<String>> shopListData) {
        ShopListData = shopListData;
    }

    public void setFloorListHeader(ArrayList<String> floorListHeader) {

        this.floorListHeader = floorListHeader;
    }

    public HashMap<String, ArrayList<String>> getShopListData() {

        return ShopListData;
    }

    public ArrayList<String> getFloorListHeader() {

        return floorListHeader;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public MallData() {

    }

    public String getMallLogoURL() {
        return mallLogoURL;
    }

    public void setMallLogoURL(String URL) {
        this.mallLogoURL = URL;
    }

    public String getMallContent() {
        return mallContent;
    }

    public void setMallContent(String content) {
        this.mallContent = content;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String title) {
        this.mallName = title;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}

