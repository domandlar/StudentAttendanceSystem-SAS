package com.foi.air.studentattendancesystem;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.foi.air.core.NavigationItem;
import com.foi.air.passwordrecord.profesor.GeneratePassword;
import com.foi.air.passwordrecord.student.SubmitAttendance;

import java.util.ArrayList;
import java.util.List;

public class NavigationManager {
    //TODO - manage data

    //singleton
    private static NavigationManager instance;

    //manage modules
    private List<NavigationItem> navigationItems;

    //manage drawer
    private AppCompatActivity activity;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private int dynamicGroupId;

    private NavigationManager()
    {
        navigationItems = new ArrayList<>();
        navigationItems.add(new GeneratePassword());
        navigationItems.add(new SubmitAttendance());
    }

    public static NavigationManager getInstance()
    {
        if (instance == null)
            instance = new NavigationManager();

        return instance;
    }



    public void startMainModule(String role) {
        if(role == "profesor"){
            NavigationItem mainModule = navigationItems != null ? navigationItems.get(0) : null;
            if (mainModule != null)
                startModule(mainModule);
        }else if(role == "student"){
            NavigationItem mainModule = navigationItems != null ? navigationItems.get(1) : null;
            if (mainModule != null)
                startModule(mainModule);
        }

    }

    private void startModule(NavigationItem module) {
        FragmentManager mFragmentManager = activity.getSupportFragmentManager();
        mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, module.getFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
    public void setDrawerDependencies(AppCompatActivity activity)
    {
        this.activity = activity;
    }

    public void selectNavigationItem(MenuItem menuItem) {
        if (!menuItem.isChecked()) {
            menuItem.setChecked(true);
            drawerLayout.closeDrawer(GravityCompat.START);

            NavigationItem selectedItem = navigationItems.get(menuItem.getItemId());
            startModule(selectedItem);
        }
    }
}
