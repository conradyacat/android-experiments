package com.raywenderlich.adaptivescenery;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScreenUtility utility = new ScreenUtility(this);
        if (utility.getWidth() < 400.0) {
            ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
            viewPager.setAdapter(new SceneryFragmentPagerAdapter(getSupportFragmentManager()));
            TabLayout tabLayout = (TabLayout)findViewById(R.id.sliding_tabs);
            tabLayout.setupWithViewPager(viewPager);
        }
    }
}
