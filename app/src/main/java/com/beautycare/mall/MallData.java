package com.beautycare.mall;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by peter on 3/6/2016.
 */
public final class MallData {

    private String mallLogoURL;
    private String mallContent;
    private String mallName;
    private String ID;
    private LatLng latLng;
    private List<String> floorListHeader;
    private LinkedHashMap<String, String> ShopListData;

    public void setShopListData(LinkedHashMap<String, String> shopListData) {

        ShopListData = shopListData;
    }

    public void setFloorListHeader(List<String> floorListHeader) {

        this.floorListHeader = floorListHeader;
    }

    public LinkedHashMap<String, String> getShopListData() {

        return ShopListData;
    }


    public List<String> getFloorListHeader() {

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

