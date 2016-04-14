package com.beautycare.makeup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.beautycare.R;

public class makeupIndex extends AppCompatActivity {
    ViewPager viewPager;
    PagerSlidingTabStrip tabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeup_index);

        setTitle("Makeup");

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager()));
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                return true;
        }
        return false;
    }

    class myPagerAdapter extends FragmentPagerAdapter{
        String[] title = { "All", "Skin Care","Cosmetics","Whitening","Facial Cleaner","Eye Cream"};
        MakeupAll all;
        MakeupSkincare skincare;
        MakeupCosmetics cosmetics;
        MakeupWhitening whitening;
        MakeupFacialcleaner facialcleaner;
        MakeupEyecream eyecream;


        public myPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    all = new MakeupAll();
                    return all;
                case 1:
                    skincare = new MakeupSkincare();
                    return skincare;
                case 2:
                    cosmetics = new MakeupCosmetics();
                    return  cosmetics;
                case 3:
                    whitening = new MakeupWhitening();
                    return  whitening;
                case 4:
                    facialcleaner = new MakeupFacialcleaner();
                    return facialcleaner;
                case 5:
                    eyecream = new MakeupEyecream();
                    return eyecream;
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
