package com.example.mymoneytraker;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ViewPager pager;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.pager);
        tabs = findViewById(R.id.tabs);

        pager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), this));
        tabs.setupWithViewPager(pager);

    }
}
