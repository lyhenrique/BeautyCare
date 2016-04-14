package com.beautycare.strategy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.beautycare.R;

public class StrategyMain extends AppCompatActivity {
    ViewPager viewPager;
    PagerSlidingTabStrip tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy_main);

        setTitle("Strategy");

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager()));
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回键
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

    class myPagerAdapter extends FragmentPagerAdapter {
        String[] title = { "All","Lady", "Gentlemen" };
        StrategyAll all;
        StrategyLady lady;
        StrategyGentlemen gentlemen;



        public myPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    all = new StrategyAll();
                    return all;
                case 1:
                    lady = new StrategyLady();
                    return lady;
                case 2:
                    gentlemen = new StrategyGentlemen();
                    return gentlemen;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {

            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];

        }

    }
}
