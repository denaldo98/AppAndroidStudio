package com.progetto.progmobile.uiutilities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.progetto.progmobile.fragments.tabsFragments.tab1;
import com.progetto.progmobile.fragments.tabsFragments.tab2;
import com.progetto.progmobile.fragments.tabsFragments.tab3;
import com.progetto.progmobile.fragments.tabsFragments.tab4;
import com.progetto.progmobile.fragments.tabsFragments.tab5;
import com.progetto.progmobile.fragments.tabsFragments.tab6;

public class PageAdapter extends FragmentPagerAdapter {

    private int numoftabs;


    public PageAdapter(@NonNull FragmentManager fm, int numoftabs) {
        super(fm);
        this.numoftabs = numoftabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new tab1();

            case 1:
                return new tab2();

            case 2:
                return new tab3();

            case 3:
                return new tab4();

            case 4:
                return new tab5();

            case 5:
                return new tab6();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numoftabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;

    }
}
