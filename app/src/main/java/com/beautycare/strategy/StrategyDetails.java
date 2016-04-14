package com.beautycare.strategy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.beautycare.R;

public class StrategyDetails extends AppCompatActivity {
    ImageView item_image;
    TextView strategyTitle;
    TextView mark;
    RatingBar rating;
    TextView strategyAbstract;
    TextView strategyDetail;
    ListView comment;

    String item_title;
    String item_abstract;
    String item_details;

    int item_mark;
    int item_like;

    String url1;
    String url2;
    String url3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy_details);

        strategyTitle = (TextView) findViewById(R.id.strategy_title);
        mark = (TextView) findViewById(R.id.mark);
        rating = (RatingBar) findViewById(R.id.rating);
        strategyAbstract = (TextView) findViewById(R.id.strategy_abstract);
        strategyDetail = (TextView) findViewById(R.id.strategy_detail);
        comment = (ListView) findViewById(R.id.commentList);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回键

        Bundle bundle = getIntent().getExtras();
        item_title = bundle.getString("strategyTitle");
        item_abstract = bundle.getString("strategyAbstract");
        item_details = bundle.getString("strategyDetails");

     //   item_like = bundle.getInt("like");
     //   item_mark = bundle.getInt("mark");

     //   url1 = bundle.getString("url1");
     //   url2 = bundle.getString("url2");
     //   url3 = bundle.getString("url3");

        //item image


        //item name
        setTitle(item_title);

        strategyTitle.setText(item_title);
        strategyAbstract.setText(item_abstract);
        strategyDetail.setText(item_details);

        //mark and RatingBar
     //   mark.setText(item_mark);
    //    rating.setMax(5);
    //    String item_mark = mark.getText().toString();
    //    rating.setRating(Float.parseFloat(item_mark));
    }

    //返回键
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                return true;
        }
        return false;
    }
}
