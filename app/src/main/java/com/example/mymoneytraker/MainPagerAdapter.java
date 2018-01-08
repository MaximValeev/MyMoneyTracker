package com.example.mymoneytraker;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "MainPagerAdapter";

    private final static int PAGE_EXPENSES = 0;
    private final static int PAGE_INCOMES = 1;
    private final static int PAGE_BALANCE = 2;

    private String[] tabsTitles;

    MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        tabsTitles = context.getResources().getStringArray(R.array.tabs_titles);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case PAGE_EXPENSES: {
                return ItemsFragment.createItemFragment(Item.TYPE_EXPENSES);
            }
            case PAGE_INCOMES: {
                return ItemsFragment.createItemFragment(Item.TYPE_INCOME);
            }
            case PAGE_BALANCE:
                return new BalanceFragment();

            default: return  null;
        }

    }

    @Override
    public int getCount() {
        return tabsTitles.length;
//        return tabsTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabsTitles[position];
    }
}
