package com.foi.air.studentattendancesystem.ui_prof;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.adapters_prof.Courses;
import com.foi.air.studentattendancesystem.adapters_prof.ListOfCoursesAdapter;
import com.foi.air.studentattendancesystem.adapters_prof.ListOfSeminarsAdapter;
import com.foi.air.studentattendancesystem.adapters_prof.Seminars;

import java.util.ArrayList;
import java.util.List;

public class ListOfCourses extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolBar;
    private DrawerLayout drawer;
    RecyclerView recyclerView;
    ListOfCoursesAdapter adapter;
    List<Courses> coursesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_courses);

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        coursesList = new ArrayList<>();
        coursesList.add(
                new Courses(
                        1,
                        "Analiza i razvoj programa",
                        "Petak",
                        "14:00",
                        "16:00",
                        "D9"
                ));
        coursesList.add(
                new Courses(
                        1,
                        "Analiza i razvoj programa",
                        "Petak",
                        "14:00",
                        "16:00",
                        "D9"
                ));
        adapter = new ListOfCoursesAdapter(this, coursesList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id==R.id.nav_courses){
            Intent intent = new Intent(this, ListOfCourses.class);
            startActivity(intent);
        }

        switch (menuItem.getItemId()){
            case R.id.nav_courses:
                Intent intent = new Intent(ListOfCourses.this, ListOfCourses.class);
                startActivity(intent);
        }
        return true;
    }
}
