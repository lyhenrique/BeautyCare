package com.beautycare.makeup;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.beautycare.R;
import com.beautycare.mall.MallDetail;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.maps.model.LatLng;
import com.beautycare.mall.MallData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MakeupDetails extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

 private SliderLayout mDemoSlider;

    TextView makeupName;
    TextView price;
    TextView mark;
    RatingBar rating;
    TextView description;
    ListView location;
    ListView comment;
    TextView brandDescription;
    ImageView brandLogo;

    String item_name;
    String item_brand;

    String category;
    String makeupContent;
    String brandContent;

    int item_mark;
    int item_price;
    int item_like;

    String location1;
    String location2;
    String location3;

    String url1;
    String url2;
    String url3;

    String img_content1;
    String img_content2;
    String img_content3;

    String clickLocation;

    ArrayList<commentItems> comments = new ArrayList<commentItems>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeup_details);

        //makeupImage = (ImageView) findViewById(R.id.makeupImage);
        makeupName = (TextView) findViewById(R.id.makeupName);
        price = (TextView) findViewById(R.id.price);
        mark = (TextView) findViewById(R.id.mark);
        rating = (RatingBar) findViewById(R.id.rating);
        description = (TextView) findViewById(R.id.description);
        location = (ListView) findViewById(R.id.makeup_detail_LocationList);

        brandDescription = (TextView)findViewById(R.id.brandContent);
        brandLogo = (ImageView)findViewById(R.id.brandImage);

        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        item_name = bundle.getString("MakeupName");
        item_brand = bundle.getString("MakeupBrand");

        item_like = bundle.getInt("like");
        item_mark = bundle.getInt("mark");
        item_price = bundle.getInt("price");

        category = bundle.getString("category");

        url1 = bundle.getString("url1");
        url2 = bundle.getString("url2");
        url3 = bundle.getString("url3");

        img_content1 = bundle.getString("img_content1");
        img_content2 = bundle.getString("img_content2");
        img_content3 = bundle.getString("img_content3");

        makeupContent = bundle.getString("MakeupContent");
        brandContent = bundle.getString("brandContent");

        location1 = bundle.getString("location1");
        location2 = bundle.getString("location2");
        location3 = bundle.getString("location3");

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put(img_content1, url1);
        url_maps.put(img_content2, url2);
        url_maps.put(img_content3, url3);

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4500);
        mDemoSlider.addOnPageChangeListener(this);



        //item name
        makeupName.setText(item_name);
        setTitle(item_brand + " " + item_name);

        //price
        price.setText(Integer.toString(item_price) + "HKD");

        //mark and RatingBar
        mark.setText(Integer.toString(item_mark));
        rating.setMax(10);
        String item_mark = mark.getText().toString();
        rating.setRating(Float.parseFloat(item_mark));

        //makeup content
        description.setText(makeupContent);

        //brand
        brandDescription.setText(brandContent);

        //location
        final ArrayList<String> loc = new ArrayList<String>();
        loc.add(location1);
        loc.add(location2);
        loc.add(location3);
        ArrayAdapter locAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,loc);
        location.setAdapter(locAdapter);
        Utility.setListViewHeightBasedOnChildren(location);

        location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MakeupDetails.this,loc.get(position).toString(), Toast.LENGTH_SHORT).show();

                clickLocation = loc.get(position).toString();

                new MyTask().execute();
            }
        });

        //comment listview
//        for(int i = 0 ; i < 5; i++){
//            commentItems com = new commentItems("test user"+i,"29/03/2016","Mark: "+"5.0","rather good merchant");
//            comments.add(com);
//
//        }
//        commentAdapter comAdapter = new commentAdapter(this, comments);
//        comment.setAdapter(comAdapter);
//        Utility.setListViewHeightBasedOnChildren(comment);
//        comment = (ListView) findViewById(R.id.commentList);
//        new commentListShow().execute();

    }

    public void comment(View view){
        Intent intent = new Intent(this, comment.class);
        Bundle bundle = new Bundle();
        bundle.putString("merchantName",item_name);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void viewComment(View view){

        new showComments().execute();


    }
    public void collect(View view){
        Toast.makeText(this,"collect",Toast.LENGTH_LONG).show();
    }




    class showComments extends AsyncTask<Void, Void, ArrayList<commentItems>> {

        ArrayList<commentItems> tmplist = new ArrayList<commentItems>();
        String urlID;

        @Override
        protected ArrayList<commentItems> doInBackground(Void... params) {
            //这个方法是在onPreExecute后执行的，params参数的类型是第一个Void，返回的类型是第三个ArrayList<Data>，返回的参数会传到onPostExecute

            try {
                AVObject commentObject;

                AVQuery<AVObject> query_1 = new AVQuery<>("comment");//封装后，这里可以考虑传parms进来


                List<AVObject> result = query_1.whereEqualTo("merchant_name",item_name).addDescendingOrder("createdAt").find();//分类筛选，第一个parameter为属性（key）,第二个为想选择的值，类似where category = 'cosmetics'

                if (result.size() != 0) {

                    for (int i = 0; i < result.size(); i++) {

                        commentItems tmpData = new commentItems();//封装好的数据类型
                        commentObject = result.get(i);//获取第i个AVObject
                        tmpData.setUserName(commentObject.getString("user"));
                        tmpData.setCommentMark("Mark: "+commentObject.getNumber("mark").toString());
                        tmpData.setCommentTime(commentObject.getDate("createdAt").toString());
                        tmpData.setCommentText(commentObject.getString("commentText"));

                        tmplist.add(i, tmpData);//将获取的数据加入全局的一个ArrayList

                    }
                }

            } catch (Exception e) {

                Log.d("exception", String.valueOf(e));
            }
            return tmplist;
        }

        protected void onPostExecute(ArrayList<commentItems> tmpdatalist) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MakeupDetails.this);
            LayoutInflater inflater = getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.comment_list, null);
            alertDialog.setView(convertView);
            alertDialog.setTitle("Comments");
            ListView lv = (ListView) convertView.findViewById(R.id.commentList);

            commentAdapter comAdapter = new commentAdapter(MakeupDetails.this, tmpdatalist);
            lv.setAdapter(comAdapter);

            alertDialog.show();
        }

        @Override
        protected void onPreExecute() {

            /*这个是AsyncTask最开始的动作，这里我们可以初始化一些东西，比如说这个很重要的云数据需要初始，
            * 下面两个很奇怪的参数我自己账户中建了一个应用，然后就会提供应用的id和key,如果大家想试的话，
            * 我把我的账号密码给你们，暂时不知道能不能同时登，
            * 账号：maskliang@gmail.com
            * 密码：fgh159456IOP*/
            AVOSCloud.initialize(MakeupDetails.this, "lpjA6quucO5BzlmMPxTrjKwD-gzGzoHsz", "vu6uDC74NlzzJP4YbrJmDFV6");

        }
    }
    class MyTask extends AsyncTask<Void, Void, ArrayList<MallData>> {

        ArrayList<MallData> tmplist = new ArrayList<MallData>();
        String urlID;

        @Override
        protected ArrayList<MallData> doInBackground(Void... params) {
            //这个方法是在onPreExecute后执行的，params参数的类型是第一个Void，返回的类型是第三个ArrayList<Data>，返回的参数会传到onPostExecute

            try {
                AVObject mallObject;
                AVFile logoObject;
                Double lat, lon;
//                AVCloudQueryResult result = AVQuery.doCloudQuery("select * from Mall");
                AVQuery<AVObject> query_1 = new AVQuery<>("Mall");//封装后，这里可以考虑传parms进来

                //List<AVObject> result = query_1.find();//查询并传数据到result

                List<AVObject> result = query_1.whereEqualTo("mall_name",clickLocation).find();//分类筛选，第一个parameter为属性（key）,第二个为想选择的值，类似where category = 'cosmetics'

                Log.d("mall_size", String.valueOf(result.size()));
                if (result.size() != 0) {

                    for (int i = 0; i < result.size(); i++) {

                        MallData tmpMallData = new MallData();//封装好的数据类型
                        mallObject = result.get(i);//获取第i个AVObject

                        tmpMallData.setMallName(mallObject.getString("mall_name"));
                        tmpMallData.setMallContent(mallObject.getString("content"));

                        tmpMallData.setID(mallObject.getObjectId());

                        lat = mallObject.getDouble("Lat");//获取坐标
                        lon = mallObject.getDouble("Lon");
                        tmpMallData.setLatLng(new LatLng(lat,lon));

                        logoObject = mallObject.getAVFile("mall_logo");

                        if( logoObject != null) {
                            urlID = logoObject.getUrl();//获取了AVFile之后可以直接获取url
                            tmpMallData.setMallLogoURL(urlID);
                        }
                        tmplist.add(i, tmpMallData);//将获取的数据加入全局的一个ArrayList

                    }
                }

            } catch (Exception e) {

                Log.d("exception", String.valueOf(e));
            }
            return tmplist;
        }

        @Override
        protected void onPostExecute(ArrayList<MallData> tmpdatalist) {

            Intent intent = new Intent(MakeupDetails.this, MallDetail.class);
            Bundle bundle = new Bundle();
            bundle.putString("url", tmpdatalist.get(0).getMallLogoURL());
            bundle.putString("content", tmpdatalist.get(0).getMallContent());
            bundle.putString("name", tmpdatalist.get(0).getMallName());
            bundle.putParcelable("latlng", tmpdatalist.get(0).getLatLng());
            intent.putExtras(bundle);
            startActivity(intent);
        }

        @Override
        protected void onPreExecute() {

            /*这个是AsyncTask最开始的动作，这里我们可以初始化一些东西，比如说这个很重要的云数据需要初始，
            * 下面两个很奇怪的参数我自己账户中建了一个应用，然后就会提供应用的id和key,如果大家想试的话，
            * 我把我的账号密码给你们，暂时不知道能不能同时登，
            * 账号：maskliang@gmail.com
            * 密码：fgh159456IOP*/
            AVOSCloud.initialize(MakeupDetails.this, "lpjA6quucO5BzlmMPxTrjKwD-gzGzoHsz", "vu6uDC74NlzzJP4YbrJmDFV6");

        }
    }


    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {}

    @Override
    public void onPageScrollStateChanged(int state) {}

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
