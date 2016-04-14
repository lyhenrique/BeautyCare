package com.beautycare.discount;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.beautycare.discount.adapter.ItemBean;
import com.beautycare.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class DiscountDetails extends AppCompatActivity {

    TextView title_detail,content_detail;
    ImageView img_detail;
    ItemBean mDetail = new ItemBean();
    DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_details);
        setTitle("Details");
        //显示系统Actionbar的返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory()
                .cacheOnDisc()
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

        initView();

        Bundle bundle = getIntent().getExtras();
        mDetail.setTitle(bundle.getString("Title"));
        mDetail.setContent(bundle.getString("Content"));
        mDetail.setURL(bundle.getString("Photo"));

        title_detail.setText(mDetail.getTitle());
        content_detail.setText(mDetail.getContent());

        ImageLoader.getInstance().displayImage(mDetail.getURL(), img_detail, options);


    }

    private void initView() {
        title_detail = (TextView)findViewById(R.id.tv_detail_title);
        content_detail = (TextView)findViewById(R.id.tv_detail_content);
        img_detail = (ImageView)findViewById(R.id.img_sales);
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
