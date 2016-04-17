package com.beautycare.mall;

import android.os.AsyncTask;
import android.util.Log;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by peter on 4/12/2016.
 */
public class GetMallDataTask extends AsyncTask<String, Void, ArrayList<MallData>> {

    ArrayList<MallData> tmplist = new ArrayList<MallData>();
    String mallLogoURL, shopListURL;
    public OnTaskCompleted taskCompleted = null;
    private List<String> floorListHeader;
    private LinkedHashMap<String, String> shopListData;

    @Override
    protected ArrayList<MallData> doInBackground(String... params) {
        try {

            AVObject mallObject, floorObject;
            AVFile logoObject, shopListObject;
            String floor;
            floorListHeader = new ArrayList<String>();
            shopListData = new LinkedHashMap<String, String>();
            Double lat, lon;
            AVQuery<AVObject> mallQuery = new AVQuery<>("Mall");//封装后，这里可以考虑传parms进来
            AVQuery<AVObject> mall2shopQuery = new AVQuery<>("Shops");
//            AVQuery<AVObject> shop2floorQuery = new AVQuery<>("Shops");
            MallData tmpMallData;
            if (!params[0].equals("")) {

                mallQuery.whereEqualTo("mall_name", params[0]);
                List<AVObject> specResult = mallQuery.find();

                mall2shopQuery.whereEqualTo("mall_name", params[0]);
                List<AVObject> floorShopResult = mall2shopQuery.find();
                if (specResult.size() != 0 || floorShopResult.size() != 0) {
                    mallObject = specResult.get(0);
                    Log.d("dataNameMall", mallObject.getString("mall_name"));
                    tmpMallData = new MallData();
                    tmpMallData.setMallName(mallObject.getString("mall_name"));
                    lat = mallObject.getDouble("Lat");
                    lon = mallObject.getDouble("Lon");
                    tmpMallData.setLatLng(new LatLng(lat, lon));

                    logoObject = mallObject.getAVFile("mall_logo");
                    if (logoObject != null) {
                        mallLogoURL = logoObject.getUrl();
                        tmpMallData.setMallLogoURL(mallLogoURL);
                    }


                    for (int x = floorShopResult.size(); x > 0; x--) {
                        floorObject = floorShopResult.get(x - 1);
                        floor = floorObject.getString("floor");
                        floorListHeader.add(floor);
                        shopListObject = floorObject.getAVFile("floor_shops");

                        if (shopListObject != null) {
                            shopListURL = shopListObject.getUrl();
                            shopListData.put(floor, shopListURL);
                        }
                    }

                    tmpMallData.setFloorListHeader(floorListHeader);
                    tmpMallData.setShopListData(shopListData);

//                    Log.d("testShopResult", floorShopResult.toString());


                    tmplist.add(tmpMallData);
                }

                Log.d("mall_size_notnull", String.valueOf(specResult.size()));
                Log.d("tmpLIST", String.valueOf(tmplist.size()));
            } else {
                List<AVObject> result = mallQuery.find();//查询并传数据到result
                Log.d("mall_size", String.valueOf(result.size()));
                if (result.size() != 0) {

                    for (int i = 0; i < result.size(); i++) {

                        tmpMallData = new MallData();//封装好的数据类型
                        mallObject = result.get(i);//获取第i个AVObject
                        /*通过key获取AVObject（即数据库）相应的列的内容并调用封装数据类的方法添加数据*/
                        tmpMallData.setMallName(mallObject.getString("mall_name"));
                        /*同理，这里因为每个数据库表的列名都不同，
                        我这里直接就写上key名了，如果这个要封装的话不知道怎么封装，所以再不行的话每人写自己的asynctask
                         */
                        tmpMallData.setMallContent(mallObject.getString("content"));

                        tmpMallData.setID(mallObject.getObjectId());

                        lat = mallObject.getDouble("Lat");//获取坐标
                        lon = mallObject.getDouble("Lon");
                        tmpMallData.setLatLng(new LatLng(lat, lon));

                        /*这个的网络数据库，可以新建一类型是AVFile的列，我这个的列名是mall_logo，这列是我们手动
                        上传图片的一个列，上传后这个图片会在本身的_File类（表）自动添加
                        一个AVFile的数据，包括url，接下来可以通过之前获得的数据直接get这个AVFile，
                        其实如果不想每个图片都放在本身的_File表的话好像也可以，
                        但是暂时看起来有点麻烦，之后再研究，下面就是获取AVFile的代码，一样有个key的值（列名）要提供*/
                        logoObject = mallObject.getAVFile("mall_logo");

                        if (logoObject != null) {
                            Log.d("logoObject", logoObject.toString());
                            mallLogoURL = logoObject.getUrl();//获取了AVFile之后可以直接获取url
                            Log.d("mallLogoURL", mallLogoURL);
                            tmpMallData.setMallLogoURL(mallLogoURL);
                        }
                        tmplist.add(i, tmpMallData);//将获取的数据加入全局的一个ArrayList
                        Log.d("data", tmplist.get(i).getMallName());
                    }
                }
            }
        } catch (Exception e) {

            Log.d("exception", String.valueOf(e));
        }
        return tmplist;
    }

    @Override
    protected void onPostExecute(ArrayList<MallData> tmpdatalist) {

        taskCompleted.processFinish(tmpdatalist);

    }
}
