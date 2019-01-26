package com.foi.air.studentattendancesystem.face;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.facerecognition.uiprofesor.CheckingAttendance;
import com.foi.air.studentattendancesystem.MainActivity;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.uiprofesor.ListOfLabs;
import com.foi.air.studentattendancesystem.uiprofesor.ListOfSeminars;
import com.foi.air.studentattendancesystem.uiprofesor.ScheduleProfesor;

public class FaceFragment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolBar;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_fragment);

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CheckingAttendance generatePassword = new CheckingAttendance();
        fragmentTransaction.replace(R.id.fragment_container,generatePassword);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_seminars:
                Intent intent = new Intent(FaceFragment.this, ListOfSeminars.class);
                startActivity(intent);
                break;
            case R.id.nav_labs:
                intent = new Intent(FaceFragment.this, ListOfLabs.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                //finish();
            case R.id.nav_schedule:
                intent = new Intent(FaceFragment.this, ScheduleProfesor.class);
                startActivity(intent);
                break;
            case R.id.nav_attendance:
                intent = new Intent(FaceFragment.this, FaceFragment.class);
                intent.putExtra("uloga","profesor");
                startActivity(intent);
                break;
        }
        return true;
    }
}
