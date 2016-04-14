package com.beautycare.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.beautycare.R;
import com.beautycare.discount.DiscountIndex;
import com.beautycare.main.widget.CycleIndicator;
import com.beautycare.main.widget.ImagePager;
import com.beautycare.makeup.makeupIndex;
import com.beautycare.mall.MallActivity;
import com.beautycare.strategy.StrategyMain;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by owner on 2016/2/5.
 */
public class FirstActivity extends FragmentActivity {
    public static Fragment[] mFragments;
    private ImageView btn_mSales;

    private Handler mHandler = new Handler();
    // 图片轮转
    int[] resIds = new int[]{R.drawable.homepage_cycle_1, R.drawable.homepage_cycle_2, R.drawable.homepage_cycle_3,R.drawable.homepage_cycle_4};
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setFragmentIndicator(0);

        //LIU Yuhao 加
        btn_mSales = (ImageView)findViewById(R.id.sales);
        onSalesButtonClick();


        ImagePager ip = (ImagePager) findViewById(R.id.pager0);
        ip.setViews(new int[]{R.drawable.homepage_cycle_1, R.drawable.homepage_cycle_2, R.drawable.homepage_cycle_3,R.drawable.homepage_cycle_4});

        final CycleIndicator indicator0 = (CycleIndicator) findViewById(R.id.indicator0);
        indicator0.setPageCount(5);
        ip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int index) {
                // TODO Auto-generated method stub
                indicator0.onPageSelected(index);
                //      Toast.makeText(MainActivity.this, "index=" + index, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    private void setFragmentIndicator(int whichIsDefault) {
        mFragments = new Fragment[4];

        mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.fragment_home);
        mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.fragment_search);
        mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.fragment_collect);
        mFragments[3] = getSupportFragmentManager().findFragmentById(R.id.fragment_settings);
        getSupportFragmentManager().beginTransaction().hide(mFragments[0])
               .hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).
                show(mFragments[whichIsDefault]).commit();

        FragmentIndicator mIndicator = (FragmentIndicator) findViewById(R.id.indicator);
        FragmentIndicator.setIndicator(whichIsDefault);

        mIndicator.setOnIndicateListener(new FragmentIndicator.OnIndicateListener() {
            @Override
            public void onIndicate(View v, int which) {
                getSupportFragmentManager().beginTransaction()
                        .hide(mFragments[0]).hide(mFragments[1])
                        .hide(mFragments[2]).hide(mFragments[3])
                        .show(mFragments[which]).commit();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }




    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.beautycare/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.beautycare/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public  void newpage(View view){
        Intent intent = new Intent(this, FirstActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }


    //LIU Yuhao 加
    public void onSalesButtonClick() {
        btn_mSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, DiscountIndex.class);
                startActivity(intent);
            }
        });
    }

    //SHEN Ling
    public void makeup(View view){
        Intent intent = new Intent(FirstActivity.this, makeupIndex.class);
        startActivity(intent);
    }

    //CHEN Yangyingjie
    public void strategy(View view){
        Intent intent = new Intent(FirstActivity.this, StrategyMain.class);
        startActivity(intent);
    }

    public void malls(View view){
        Intent intent = new Intent(FirstActivity.this, MallActivity.class);
        startActivity(intent);
    }

    //dsc
    public void p1(View view){
        Uri uri = Uri.parse("http://evonnz.com/2012/02/les-merveilleuses-de-laduree-face-color-rose-laduree-rose-petals-blush-swatches/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


}
