package com.example.sidenavigationdrawer;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.transition.Slide;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    NavigationView navView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);   // Add Toolbar


        navView = (NavigationView)findViewById(R.id.navbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);

        toggle = new ActionBarDrawerToggle(this ,drawerLayout , toolbar , R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.menu_home:
                        Toast.makeText(getBaseContext(), "HOME",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START );
                        break;
                    case  R.id.menu_about:
                        Toast.makeText(getBaseContext(), "ABOUT",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START );
                        break;
                    case  R.id.menu_profile:
                        Toast.makeText(getBaseContext(), "PROFILE",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START );
                        break;
                }
                return true;
            }
        });
    }
}
/*
*============================================================ DESIGN NAVIGATION DRAWER ========================================================
* 1. add -     implementation 'com.android.support:design:28.0.0'
* 2. set main_activity root layout to - DrawerLayout
* 3. set theme to - NO_ACTION_BAR
* 4. Make a toolbar
* 5. MAke a Navigaion view which contain
*    i) profile eg.. cover photo and name
*   ii) menu  eg.. home ,about
*
* 6. Code Mainactivity java 
*
*
*
*
*
*
*
 */