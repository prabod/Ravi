package com.emergelk.ravindrab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TabsAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Fragment_main tab1 = new Fragment_main();
                return tab1;
            case 2:
                Fragment_second tab2 = new Fragment_second();
                return tab2;
            case 1:
                Fragment_third tab3 = new Fragment_third();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
