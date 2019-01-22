package com.foi.air.studentattendancesystem.uistudent;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;

import com.foi.air.core.entities.Aktivnost;
import com.foi.air.studentattendancesystem.MainActivity;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.adaptersStudent.ScheduleAdapterStudent;
import com.foi.air.studentattendancesystem.adaptersprofesor.ListOfActivitiesAdapter;
import com.foi.air.studentattendancesystem.adaptersprofesor.ScheduleAdapter;
import com.foi.air.studentattendancesystem.password.PasswordFragment;

import java.util.ArrayList;
import java.util.List;

public class ScheduleStudent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolBar;

    private DrawerLayout drawer;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    ArrayList<String> daysList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_student);
        setTitle("Raspored za dan");

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        String[] days = {"Ponedjeljak",
                "Utorak",
                "Srijeda",
                "Cetvrtak",
                "Petak"};


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //adapter=new ListOfSeminarsAdapter(this, daysList);
        //recyclerView.setAdapter(adapter);

        adapter = new ScheduleAdapterStudent(this, days);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_schedule:
                Intent intent = new Intent(ScheduleStudent.this, ScheduleStudent.class);
                startActivity(intent);
                break;
            case R.id.nav_seminars:
                intent = new Intent(ScheduleStudent.this, SeminarList.class);
                startActivity(intent);
                break;
            case R.id.nav_labs:
                intent = new Intent(ScheduleStudent.this, LabsList.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_generate_passwords:
                intent = new Intent(ScheduleStudent.this, PasswordFragment.class);
                intent.putExtra("uloga","student");
                startActivity(intent);
                break;

        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu,menu);

        return true;
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_dodaj_seminar:
                Intent intent = new Intent(ScheduleProfesor.this, AddSeminar.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }*/

    //drawer
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
