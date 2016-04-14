/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.beautycare.mall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.avos.avoscloud.AVOSCloud;
import com.beautycare.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;


public class MallDetail extends AppCompatActivity implements OnMapReadyCallback,OnTaskCompleted {


    private RecyclerView mallContRecy;
    private DisplayImageOptions options;
    Bundle receiveBundleData;
    private ImageView mallImage;
    private MallData receiveMallData = new MallData();
    private GetMallDataTask getDataInDetailPage;
    private String mallname = null;
    private LatLng tmpLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mall_detail_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AVOSCloud.initialize(MallDetail.this, "lpjA6quucO5BzlmMPxTrjKwD-gzGzoHsz", "vu6uDC74NlzzJP4YbrJmDFV6");
//        mallRecyList = (RecyclerView) findViewById(R.id.mall_list_main);

        setTitle("Mall");

        options = new DisplayImageOptions.Builder()
         //       .showImageOnLoading(R.drawable.ic_stub)
                .showStubImage(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory()
                .cacheOnDisc()
        //        .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

        getDataInDetailPage = new GetMallDataTask();
        getDataInDetailPage.taskCompleted = this;


        receiveBundleData = getIntent().getExtras();
        mallname = receiveBundleData.getString("MallName");//其他页面传商场名字参数即可，键名统一为为MallName
//        receiveMallData.setMallURL(receiveBundleData.getString("url"));
//        receiveMallData.setMallContent(receiveBundleData.getString("content"));
//        receiveMallData.setMallName(receiveBundleData.getString("name"));
//        receiveMallData.setLatLng((LatLng) receiveBundleData.get("latlng"));

        mallImage = (ImageView) findViewById(R.id.mall_logo_image);



        getDataInDetailPage.execute(mallname);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        setMapLocation(googleMap, new NamedLocation(receiveMallData.getMallName(), receiveMallData.getLatLng()));

    }


    /**
     * Displays a {@link NamedLocation} on a
     * {@link GoogleMap}.
     * Adds a marker and centers the camera on the NamedLocation with the normal map type.
     */
    private static void setMapLocation(GoogleMap map, NamedLocation data) {
        // Add a marker for this item and set the camera
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(data.location, 14.5f));
        map.addMarker(new MarkerOptions().position(data.location).title(data.name));

        // Set the map type back to normal.
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void processFinish(ArrayList<MallData> tmpdatalist) {


        if( tmpdatalist.size() != 0 )
        {
            Log.d("dataTest", tmpdatalist.get(0).toString());
            receiveMallData.setLatLng(tmpdatalist.get(0).getLatLng());
            receiveMallData.setMallLogoURL(tmpdatalist.get(0).getMallLogoURL());
            receiveMallData.setMallContent(tmpdatalist.get(0).getMallContent());
            tmpLatLng = tmpdatalist.get(0).getLatLng();
            mallContRecy = (RecyclerView) findViewById(R.id.mall_detail_recylist);

            mallContRecy.setNestedScrollingEnabled(true);

            mallContRecy.setLayoutManager(new LinearLayoutManager(this));
            mallContRecy.setAdapter(new MallDetailRecyAdapter(this, receiveMallData));

            ImageLoader.getInstance().displayImage(tmpdatalist.get(0).getMallLogoURL(), mallImage, options);
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        else {

            Log.d("TestTest", String.valueOf(tmpdatalist.size()));
        }


    }


    /**
     * Location represented by a position ({@link LatLng} and a
     * name ({@link String}).
     */
    private static class NamedLocation {

        public final String name;

        public final LatLng location;

        NamedLocation(String name, LatLng location) {
            this.name = name;
            this.location = location;
        }
    }

    //菜单返回上一页
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                return true;
        }
        return false;
    }

}
