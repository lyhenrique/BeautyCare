package com.beautycare.discountpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVOSCloud;
import com.beautycare.R;
import com.beautycare.discountpage.adapter.ItemBean;
import com.beautycare.discountpage.utils.DiscountDataUtils;
import com.beautycare.discountpage.utils.onDiscountDataCompleted;
import com.beautycare.discountpage.widget.ImageLoadingDialog;
import com.beautycare.mall.MallDetail;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.HashMap;

public class DiscountDetails extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener,onDiscountDataCompleted {


    SliderLayout mSliderLayout;
    TextView title_detail,content_detail;
    Button btn_detail;
    ItemBean mDetail = new ItemBean();
    DisplayImageOptions options;
    Bundle bundle;
    DiscountDataUtils mDataReader;
    String mDiscountName;
    ArrayList<ItemBean> mDetailData = new ArrayList<ItemBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_details);
        setTitle("Details");
        //显示系统Actionbar的返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AVOSCloud.initialize(DiscountDetails.this, "lpjA6quucO5BzlmMPxTrjKwD-gzGzoHsz", "vu6uDC74NlzzJP4YbrJmDFV6");


        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory()
                .cacheOnDisc()
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

        mDataReader = new DiscountDataUtils();
        mDataReader.mDataCompleted = this;
        bundle = getIntent().getExtras();
        mDiscountName = bundle.getString("title");
        Log.e("Name", mDiscountName);

        initView();

        mDataReader.execute(mDiscountName);

//        bundle = getIntent().getExtras();
//        mDetail.setTitle(bundle.getString("Title"));
//        mDetail.setContent(bundle.getString("Content"));
//        mDetail.setBANNER_1(bundle.getString("Banner1"));
//        mDetail.setBANNER_2(bundle.getString("Banner2"));
//        mDetail.setBANNER_1_NAME(bundle.getString("Banner1_Name"));
//        mDetail.setBANNER_2_NAME(bundle.getString("Banner2_Name"));
//
//        title_detail.setText(mDetail.getTitle());
//        content_detail.setText(mDetail.getContent());

        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle toMallDetail = new Bundle();
                toMallDetail.putString("MallName","Festival Walk");
                Intent jumpToMall = new Intent(DiscountDetails.this, MallDetail.class);
                jumpToMall.putExtras(toMallDetail);
                startActivity(jumpToMall);
            }
        });

    }

    @Override
    public void onSecondDataDone(ArrayList<ItemBean> secondList) {

        final ImageLoadingDialog dialog = new ImageLoadingDialog(this);
        dialog.show();
        // 两秒后关闭后dialog
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000 * 2);

        if(secondList.size() != 0) {
            mDetail.setTitle(secondList.get(0).getTitle());
            mDetail.setContent(secondList.get(0).getContent());
            mDetail.setBANNER_1_NAME(secondList.get(0).getBANNER_1_NAME());
            mDetail.setBANNER_2_NAME(secondList.get(0).getBANNER_2_NAME());
            mDetail.setBANNER_1(secondList.get(0).getBANNER_1());
            mDetail.setBANNER_2(secondList.get(0).getBANNER_2());
            title_detail.setText(mDetail.getTitle());
            content_detail.setText(mDetail.getContent());
            readImg();

        }
    }

    @Override
    public void onDiscountDataDone(ArrayList<ItemBean> arrayList, ArrayList<ItemBean> allData, int currentNum) {

    }

    private void initView() {
        title_detail = (TextView)findViewById(R.id.tv_detail_title);
        content_detail = (TextView)findViewById(R.id.tv_detail_content);
        mSliderLayout = (SliderLayout)findViewById(R.id.discount_slider);
        btn_detail = (Button)findViewById(R.id.btn_jump_to_shoppint_mall);
    }

    private void readImg() {
        HashMap<String,String> url_banners = new HashMap<>();
        url_banners.put(mDetail.getBANNER_1_NAME(), mDetail.getBANNER_1());
        url_banners.put(mDetail.getBANNER_2_NAME(), mDetail.getBANNER_2());
        Log.e("Name", mDetail.getBANNER_1());
        Log.e("Name",mDetail.getBANNER_1_NAME());
        Log.e("Name",mDetail.getBANNER_2());
        Log.e("Name",mDetail.getBANNER_2_NAME());

        for(String name : url_banners.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);

            //initialize the SliderLayout
            textSliderView
                    .description(name)
                    .image(url_banners.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

//            add extra information
//            可以加一下标题什么的
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mSliderLayout.addSlider(textSliderView);
        }

        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setDuration(4000);
        mSliderLayout.addOnPageChangeListener(this);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //滚动到哪个页面就进行什么动作
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        int pos = mSliderLayout.getCurrentPosition();
        Log.e("ShowPos", String.valueOf(pos));
                //那只有在这里通过name来进行点击事件了. 点击之后放大这张图片.大概就是点击出一个alertdialog 再把图传进去.
        switch (pos) {
            case 0:
                String url_1 = mDetail.getBANNER_1();
                passImg(url_1);
                break;
//                Log.e("CurrentID",Integer.toString(mSliderLayout.getCurrentPosition()));
//                LayoutInflater inflater = LayoutInflater.from(this);
//                View imgEntryView = inflater.inflate(R.layout.see_big_img_discount,null);
//
//                final AlertDialog dialog = new AlertDialog.Builder(this).create();
//                PhotoView img1 = (PhotoView)imgEntryView.findViewById(R.id.big_img_discount);
//                img1.enable();
//                ImageLoader.getInstance().displayImage(mDetail.getBANNER_1(), img1, options);
//                dialog.setView(imgEntryView);
//
//                dialog.show();
//                imgEntryView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.cancel();
//                    }
//                });
//                break;
            case 1:
                String url_2 = mDetail.getBANNER_2();
                passImg(url_2);
                break;
//                Log.e("CurrentID",Integer.toString(mSliderLayout.getCurrentPosition()));
//                LayoutInflater inflater2 = LayoutInflater.from(this);
//                View imgEntryView2 = inflater2.inflate(R.layout.see_big_img_discount, null);
//                final AlertDialog dialog2 = new AlertDialog.Builder(this).create();
//                PhotoView img2 = (PhotoView)imgEntryView2.findViewById(R.id.big_img_discount);
//                img2.enable();
//                ImageLoader.getInstance().displayImage(mDetail.getURL(),img2,options);
//                dialog2.setView(imgEntryView2);
//                dialog2.show();
//                imgEntryView2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog2.cancel();
//                    }
//                });
//                break;
        }
    }

    private void passImg(String url) {
        Bundle bundle_big = new Bundle();
        bundle_big.putString("URL",url);
        Intent intent = new Intent(this,SeeBigImg.class);
        intent.putExtras(bundle_big);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mSliderLayout.stopAutoCycle();
        super.onStop();
    }


}
