package com.hfad.bountyhunter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(BountyHunterApp.firstLoad) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    BountyHunter.generateBountyHunters();
                    setContentView(R.layout.activity_main);
                    setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
                    SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
                    ViewPager myPager= findViewById(R.id.pager);
                    myPager.setAdapter(adapter);
                    TabLayout tabLayout = findViewById(R.id.tabs);
                    tabLayout.setupWithViewPager(myPager);
                }
            }, 20000);
            BountyHunterApp.firstLoad = false;
        } else {
            setContentView(R.layout.activity_main);
            setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
            SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
            ViewPager myPager= findViewById(R.id.pager);
            myPager.setAdapter(adapter);
            TabLayout tabLayout = findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(myPager);
        }


    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter{
        public SectionsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public int getCount(){
            return 3;
        }

        @Override
        public Fragment getItem(int position){
            switch(position){
                case 0:
                    return new BountyHunterListFragment();
                case 1:
                    return new BountyHunterDisplayFragment();
                case 2:
                    return new BountyHunt();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch(position){
                case 0:
                    return getResources().getText(R.string.list_hunters);
                case 1:
                    return getResources().getText(R.string.view_hunters);
                case 2:
                    return getResources().getText(R.string.bounty_hunt);
            }
            return null;
        }
    }



}
