package com.foi.air.studentattendancesystem;


import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.facerecognition.uiprofesor.CheckingAttendance;
import com.foi.air.core.NavigationItem;

import java.util.ArrayList;
import java.util.List;

public class NavigationManager {

    private static NavigationManager instance;

    private List<NavigationItem> navigationItems;

    private AppCompatActivity activity;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private int dynamicGroupId;

    private NavigationManager(){
        navigationItems = new ArrayList<>();
        navigationItems.add(new CheckingAttendance());
    }
    public static NavigationManager getInstance()
    {
        if(instance==null)
            instance= new NavigationManager();
        return instance;
    }

    public void setDrawerDependencies(
            AppCompatActivity activity,
            NavigationView naviationView,
            DrawerLayout drawerLayout,
            int dynamicGroupId)
    {
        this.activity=activity;
        this.navigationView=naviationView;
        this.drawerLayout=drawerLayout;
        this.dynamicGroupId=dynamicGroupId;
    }
    private void setupDrawer(){
        for (int i=0;i<navigationItems.size();i++){
            NavigationItem item = navigationItems.get(i);
            navigationView.getMenu()
                    .add(dynamicGroupId, i, i+1,item.getName(activity))
                    .setCheckable(true)
                    .setIcon(item.getIcon(activity));
        }
    }

    public void startMainModule(){
        NavigationItem mainModule = navigationItems != null ? navigationItems.get(0) : null;
        if(mainModule!=null)
            startModule(mainModule);
    }
    private void startModule(NavigationItem module){
        FragmentManager mFragmentManager = activity.getSupportFragmentManager();
        mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,module.getFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
        //TODO: DataManager.GetInstance().sendData(module);
    }
    public void selectNavigationItem(MenuItem  menuItem){
        if(!menuItem.isChecked()){
            menuItem.setChecked(true);
            drawerLayout.closeDrawer(GravityCompat.START);

            NavigationItem selectedItem = navigationItems.get(menuItem.getItemId());
            startModule(selectedItem);
        }
    }
}
