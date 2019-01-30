package com.foi.air.studentattendancesystem.attendance;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.facerecognition.uiprofesor.CheckingAttendance;
import com.foi.air.core.NavigationItem;
import com.foi.air.core.SasWsDataLoadedListener;
import com.foi.air.core.entities.Dolazak;
import com.foi.air.passwordrecord.profesor.GeneratePassword;
import com.foi.air.passwordrecord.student.SubmitAttendance;
import com.foi.air.studentattendancesystem.MainActivity;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoader;
import com.foi.air.studentattendancesystem.uiprofesor.CheckAttendance;
import com.foi.air.studentattendancesystem.uiprofesor.ListOfCourses;
import com.foi.air.studentattendancesystem.uiprofesor.ListOfLabs;
import com.foi.air.studentattendancesystem.uiprofesor.ListOfLectures;
import com.foi.air.studentattendancesystem.uiprofesor.ListOfSeminars;
import com.foi.air.studentattendancesystem.uiprofesor.ScheduleProfesor;

import java.util.ArrayList;

public class ModuleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NavigationItem.OnCallbackReceived, SasWsDataLoadedListener {
    private Toolbar toolBar;

    private DrawerLayout drawer;

    int idAktivnosti=0;
    int idUloge=0;
    int tjedanNastave=0;
    int modul=0;
    String uloga="";

    NavigationItem navigationItem;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);
        setTitle("Evidentiraj prisustvo");

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Bundle extras = getIntent().getExtras();
        idAktivnosti = extras.getInt("idAktivnosti");
        idUloge = extras.getInt("idUloge");
        tjedanNastave = extras.getInt("tjedanNastave");
        modul = extras.getInt("modul");
        uloga = extras.getString("uloga");

        if(uloga.equals("profesor")){
            SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
            sasWsDataLoader.postaviPrisustvo(idAktivnosti, tjedanNastave, this);
            switch(modul){
                case 0:
                    navigationItem = new GeneratePassword();
                    navigationItem.setData(idAktivnosti,idUloge,tjedanNastave);

                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.fragment_container, (Fragment) navigationItem).addToBackStack(null);
                    fragmentTransaction.commit();

                    break;
                case 1:
                    navigationItem = new CheckingAttendance();
                    navigationItem.setData(idAktivnosti,idUloge,tjedanNastave);

                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.fragment_container, (Fragment) navigationItem).addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
            }

        }else{//student
            switch(modul){
                case 0:
                    navigationItem = new SubmitAttendance();
                    navigationItem.setData(idAktivnosti,idUloge,tjedanNastave);


                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.fragment_container, (Fragment) navigationItem).addToBackStack(null);
                    fragmentTransaction.commit();


                    //ArrayList<Dolazak> dolazakList = sa.getData();
                    //Toast.makeText(getApplicationContext(),String.valueOf(dolazakList.get(0).isPrisustvo()),Toast.LENGTH_LONG).show();


                    break;
                case 1:
                    break;
            }
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()){
            case R.id.nav_seminars:
                intent = new Intent(this, ListOfSeminars.class);
                startActivity(intent);
                break;
            case R.id.nav_courses:
                intent = new Intent(this, ListOfCourses.class);
                startActivity(intent);
                break;
            case R.id.nav_labs:
                intent = new Intent(this, ListOfLabs.class);
                startActivity(intent);
                break;
            case R.id.nav_lectures:
                intent = new Intent(this, ListOfLectures.class);
                startActivity(intent);
                break;

            case R.id.nav_schedule:
                intent = new Intent(this, ScheduleProfesor.class);
                startActivity(intent);
                break;
            case R.id.nav_attendance_preview:
                intent = new Intent(this, CheckAttendance.class);
                startActivity(intent);
                break;
            case R.id.nav_generate_passwords:
                intent = new Intent(this, CheckActivity.class);
                intent.putExtra("uloga","profesor");
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
    public void onBackPressed() {
        Intent intent = new Intent(ModuleActivity.this, CheckActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void Update() {
        ArrayList<Dolazak> dolazakList = navigationItem.getData();
        if(dolazakList != null){
            if(dolazakList.get(0).isPrisustvo()){
                for(int i=0;i<dolazakList.size();i++){
                    SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
                    sasWsDataLoader.zabiljeziPrisustvo(dolazakList.get(i).getIdStudenta(),dolazakList.get(i).getTjedanNastave(),dolazakList.get(i).getIdAktivnosti(),this);
                    Toast.makeText(getApplicationContext(),"Prisustvo je zabilježeno!",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

    @Override
    public void onWsDataLoaded(Object message, String status, Object data) {

    }
}
