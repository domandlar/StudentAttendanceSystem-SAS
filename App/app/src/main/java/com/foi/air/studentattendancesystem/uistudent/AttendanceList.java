package com.foi.air.studentattendancesystem.uistudent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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


import com.foi.air.core.SasWsDataLoadedListener;
import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.Kolegij;
import com.foi.air.core.entities.Student;
import com.foi.air.studentattendancesystem.MainActivity;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.adaptersStudent.AttendanceListAdapter;
import com.foi.air.studentattendancesystem.attendance.profesor.PasswordActivity;

import java.util.List;

public class AttendanceList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SasWsDataLoadedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;

    RecyclerView recyclerView;
    AttendanceListAdapter adapter;

    List<Kolegij> courseList;
    List<Aktivnost> aktivnostList;

    Kolegij kolegij;
    Aktivnost aktivnost;

    String idStudenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);
        setTitle("Popis prisustva");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        idStudenta = prefs.getString("idStudenta","");
        Student student = new Student(Integer.parseInt(idStudenta));

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(
                new RecyclerView.SimpleOnItemTouchListener()
        );

        //SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
        //sasWsDataLoader.evidencijaStudent(kolegij, aktivnost, student,this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_seminars:
                Intent intent = new Intent(AttendanceList.this, SeminarList.class);
                startActivity(intent);
                break;
            case R.id.nav_labs:
                intent = new Intent(AttendanceList.this, LabsList.class);
                startActivity(intent);
                break;
            case R.id.nav_courses:
                intent = new Intent(AttendanceList.this, ListCourses.class);
                startActivity(intent);
                break;
            case R.id.nav_schedule:
                intent = new Intent(AttendanceList.this, ScheduleStudent.class);
                startActivity(intent);
                break;
            case R.id.nav_lectures:
                intent = new Intent(AttendanceList.this, LecturesList.class);
                startActivity(intent);
                break;
            case R.id.nav_generate_passwords:
                intent = new Intent(AttendanceList.this, PasswordActivity.class);
                intent.putExtra("uloga","student");
                startActivity(intent);
                break;
            case R.id.nav_logout:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
        }
        return true;
    }

    @Override
    public void onWsDataLoaded(Object message, String status, Object data) {

    }
}
