package com.progetto.progmobile.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.progetto.progmobile.R;
import com.progetto.progmobile.dialogs.DialogOrario;
import com.progetto.progmobile.uiutilities.PageAdapter;


public class FragmentOrario extends Fragment {



    public FragmentOrario() {
        // Required empty public constructor
    }

    private TabLayout tablayout;
    private ViewPager viewPager;
    private TabItem tab1, tab2, tab3, tab4, tab5, tab6;
    public PagerAdapter pagerAdapter;

    //bottone per aprire il dialog
    private ImageButton btnAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_orario, container, false);

        //gestione bottoneAdd
        btnAdd = view.findViewById(R.id.button_add_event);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new DialogOrario();
                assert getFragmentManager() != null;
                dialog.show(getFragmentManager(), "tag");
            }
        });



        tablayout = (TabLayout) view.findViewById(R.id.tablayout);
        tab1 = (TabItem ) view.findViewById(R.id.Tab1);
        tab2 = (TabItem ) view.findViewById(R.id.Tab2);
        tab3 = (TabItem ) view.findViewById(R.id.Tab3);
        tab4 = (TabItem ) view.findViewById(R.id.Tab4);
        tab5 = (TabItem ) view.findViewById(R.id.Tab5);
        tab6 = (TabItem ) view.findViewById(R.id.Tab6);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        pagerAdapter = new PageAdapter(getChildFragmentManager(), tablayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        //noinspection deprecation
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

        return view;
    }



}
