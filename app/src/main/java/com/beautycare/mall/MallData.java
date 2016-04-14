package com.beautycare.mall;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by peter on 3/6/2016.
 */
public final class MallData {

    private String URL;
    private String content;
    private String title;
    private String ID;
    private LatLng latLng;

//    public Data(String tmpid, String tmpurl, String tmpcontent, String tmptitle){
//
//        URL=tmpurl;
//        content=tmpcontent;
//        title=tmptitle;
//        ID=tmpid;
//    }


    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public MallData() {

    }

    public String getMallLogoURL() {
        return URL;
    }

    public void setMallLogoURL(String URL) {
        this.URL = URL;
    }

    public String getMallContent() {
        return content;
    }

    public void setMallContent(String content) {
        this.content = content;
    }

    public String getMallName() {
        return title;
    }

    public void setMallName(String title) {
        this.title = title;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}

