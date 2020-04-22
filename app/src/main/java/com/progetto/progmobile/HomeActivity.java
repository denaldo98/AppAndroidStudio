package com.progetto.progmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.progetto.progmobile.fragments.FragmentAppelli;
import com.progetto.progmobile.fragments.FragmentCorsi;
import com.progetto.progmobile.fragments.FragmentOrario;
import com.progetto.progmobile.fragments.FragmentTodo;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    //Per non instaziare ogni volta i nostri fragment, li istanziamo una volta sola
    private FragmentTodo fragmentTodo;
    private FragmentAppelli fragmentAppelli;
    private FragmentCorsi fragmentCorsi;
    private FragmentOrario fragmentOrario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Get access to the custom title view
        final TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);



        //instanziamo prima di tutto i fragment
        fragmentTodo = new FragmentTodo();
        fragmentAppelli = new FragmentAppelli();
        fragmentCorsi = new FragmentCorsi();
        fragmentOrario = new FragmentOrario();

        //gestione BottomNavigationBar
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentTodo).commit(); //predispongo il Fragment iniziale

        bottomNav = findViewById(R.id.bottom_nav);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                switch(menuItem.getItemId()){
                    case R.id.menu_todo:
                        selectedFragment = fragmentTodo;
                        break;
                    case R.id.menu_appelli:
                        selectedFragment = fragmentAppelli;
                        break;
                    case R.id.menu_corsi:
                        selectedFragment = fragmentCorsi;
                        break;
                    case R.id.menu_orario:
                        selectedFragment =fragmentOrario;
                        break;
                }
                if(selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    mTitle.setText(menuItem.getTitle().toString());  //metto come titolo della toolbar il titolo dell'elemento del menu selezionato
                }
                return true;
            }
        });
    }



    // Menu icons are inflated just as they were with actionbar
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            //noinspection RestrictedApi
            m.setOptionalIconsVisible(true);
        }
        return true;
    }
}




