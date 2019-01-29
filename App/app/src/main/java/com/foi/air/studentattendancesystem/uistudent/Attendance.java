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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.foi.air.core.SasWsDataLoadedListener;
import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.Kolegij;
import com.foi.air.core.entities.Student;
import com.foi.air.studentattendancesystem.MainActivity;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.attendance.profesor.PasswordActivity;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;

public class Attendance extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SasWsDataLoadedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private Button btnPrisutnost;

    BetterSpinner spinnerKolegiji;
    BetterSpinner spinnerAktivnosti;

    String idStudenta;

    Aktivnost aktivnost;
    Student student;
    Kolegij kolegij;

    ArrayList<Kolegij> kolegijList;
    ArrayAdapter<Kolegij> spinnerAdapterKolegiji;
    ArrayList<Aktivnost> aktivnostList;
    ArrayAdapter<Aktivnost> spinnerAdapterAktivnost;


    int idKolegija = 0;
    int idAktivnosti = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        setTitle("Provjeri prisutnost");

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        idStudenta = prefs.getString("idStudenta", "");
        student = new Student(Integer.parseInt(idStudenta));

        //SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
        //sasWsDataLoader.evidencijaStudent(kolegij,aktivnost,student,this);

        spinnerKolegiji = findViewById(R.id.spinnerKolegiji);
        try {
            spinnerAdapterKolegiji = new ArrayAdapter<Kolegij>(this, android.R.layout.simple_dropdown_item_1line, kolegijList);
            spinnerKolegiji.setAdapter(spinnerAdapterKolegiji);
            Thread.sleep(500);
        }catch (Exception e){

        }
        spinnerKolegiji.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Kolegij kolegij = (Kolegij) parent.getItemAtPosition(position);
                idKolegija = kolegij.getId();
            }
        });
        spinnerAktivnosti = findViewById(R.id.spinnerAktivnosti);
        try {
            spinnerAdapterAktivnost = new ArrayAdapter<Aktivnost>(this, android.R.layout.simple_dropdown_item_1line, aktivnostList);
            spinnerAktivnosti.setAdapter(spinnerAdapterAktivnost);
            Thread.sleep(500);
        }catch (Exception e) {

        }

        spinnerAktivnosti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aktivnost aktivnost = (Aktivnost) parent.getItemAtPosition(position);
                idAktivnosti = aktivnost.getIdAktivnosti();
            }
        });



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_seminars:
                Intent intent = new Intent(Attendance.this, SeminarList.class);
                startActivity(intent);
                break;
            case R.id.nav_labs:
                intent = new Intent(Attendance.this, LabsList.class);
                startActivity(intent);
                break;
            case R.id.nav_courses:
                intent = new Intent(Attendance.this, ListCourses.class);
                startActivity(intent);
                break;
            case R.id.nav_schedule:
                intent = new Intent(Attendance.this, ScheduleStudent.class);
                startActivity(intent);
                break;
            case R.id.nav_lectures:
                intent = new Intent(Attendance.this, LecturesList.class);
                startActivity(intent);
                break;
            case R.id.nav_generate_passwords:
                intent = new Intent(Attendance.this, PasswordActivity.class);
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

