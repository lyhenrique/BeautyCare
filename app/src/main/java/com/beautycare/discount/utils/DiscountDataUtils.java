package com.beautycare.discount.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.beautycare.discount.adapter.ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric.Lau on 16/4/14.
 */
public class DiscountDataUtils extends AsyncTask<String, Void, ArrayList<ItemBean>> {
    ArrayList<ItemBean> tmplist = new ArrayList<>();
    String urlID,banner1ID,banner2ID;
    public onDiscountDataCompleted mDataCompleted = null;

    int mAllCounter;


    @Override
    protected ArrayList<ItemBean> doInBackground(String... params) {
        try{
            AVObject mSalesObject;
            AVFile logoObject;
            AVFile banner1;
            AVFile banner2;
            ItemBean tmpItemBean;
            AVQuery<AVObject> query_1 = new AVQuery<>("Sales");
            List<AVObject> result = query_1.find();
            Log.e("sales_size", String.valueOf(result.size()));

            if (!params[0].equals("")) {
                //这个是读第二层页面.
                query_1.whereEqualTo("discount_name", params[0]);
                List<AVObject> specResult = query_1.find();

                if (specResult.size() != 0) {
                    mSalesObject = specResult.get(0);
                    tmpItemBean = new ItemBean();
                    tmpItemBean.setTitle(mSalesObject.getString("title"));
                    tmpItemBean.setContent(mSalesObject.getString("content"));
                    tmpItemBean.setBANNER_1_NAME(mSalesObject.getString("banner1_name"));
                    tmpItemBean.setBANNER_2_NAME(mSalesObject.getString("banner2_name"));

//                    logoObject = mSalesObject.getAVFile("sales_logo");
                    banner1 = mSalesObject.getAVFile("banner_1");
                    banner2 = mSalesObject.getAVFile("banner_2");

                    if (banner1 != null || banner2 != null) {
                        Log.e("banner_1", banner1.toString());
//                        urlID = logoObject.getUrl();
                        banner1ID = banner1.getUrl();
                        banner2ID = banner2.getUrl();
//                        Log.e("url", urlID);
                        Log.e("banner", banner1ID);
                        Log.e("banner", banner2ID);
//                        tmpItemBean.setURL(urlID);
                        tmpItemBean.setBANNER_1(banner1ID);
                        tmpItemBean.setBANNER_2(banner2ID);
                    }
                    tmplist.add(tmpItemBean);

                }
            }
            else {
                List<AVObject> mFirstPageResult = query_1.find();
                if(mFirstPageResult.size() != 0) {
                    mAllCounter = mFirstPageResult.size();

                }

            }
        } catch (AVException e) {
            e.printStackTrace();
        }

        return null;
    }
}
