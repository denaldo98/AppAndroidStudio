package com.progetto.progmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
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
                }
                return true;
            }
        });
    }
}
