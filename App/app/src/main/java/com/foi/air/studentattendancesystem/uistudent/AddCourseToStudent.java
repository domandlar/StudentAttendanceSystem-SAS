package com.foi.air.studentattendancesystem.uistudent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.foi.air.core.entities.Kolegij;
import com.foi.air.core.entities.Profesor;
import com.foi.air.core.entities.Student;
import com.foi.air.studentattendancesystem.MainActivity;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoadedListener;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoader;
import com.foi.air.studentattendancesystem.uiprofesor.ListOfCourses;
import com.foi.air.studentattendancesystem.uiprofesor.ListOfLabs;
import com.foi.air.studentattendancesystem.uiprofesor.ListOfLectures;
import com.foi.air.studentattendancesystem.uiprofesor.ListOfSeminars;
import com.foi.air.studentattendancesystem.uiprofesor.ScheduleProfesor;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddCourseToStudent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SasWsDataLoadedListener {

    private Toolbar toolBar;
    private DrawerLayout drawer;
    private Button btnAddCourseToProfessor;

    BetterSpinner spinnerKolegiji;
    Student student;
    String idStudenta;

    ArrayList<Kolegij> kolegijList;
    ArrayList<Kolegij> upisaniKolegijList;
    ArrayAdapter<Kolegij> spinnerAdapterKolegiji;

    int idKolegija=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_to_professor);
        setTitle("Upisivanje kolegija");



        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        idStudenta = prefs.getString("idStudenta", "");
        student = new Student(Integer.parseInt(idStudenta));

        SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
        sasWsDataLoader.sviKolegiji(student.getIdStudenta(),this);
        sasWsDataLoader.kolegijForStudent(student,this);

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



        btnAddCourseToProfessor = findViewById(R.id.buttonUpisiKolegij);
        btnAddCourseToProfessor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(idKolegija !=0 && !UpisaniKolegij(idKolegija) ){
                    SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
                    sasWsDataLoader.dodajKolegijStudentu(Integer.parseInt(idStudenta), idKolegija);
                    Toast.makeText(getApplicationContext(),"Kolegij je upisan!", Toast.LENGTH_SHORT).show();
                }else{
                    if(idKolegija==0) {
                        AlertDialog alertDialog = new AlertDialog.Builder(AddCourseToStudent.this).create();
                        alertDialog.setTitle("Pogreška");
                        alertDialog.setMessage("Niste unjeli sva polja!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }else {
                        AlertDialog alertDialog = new AlertDialog.Builder(AddCourseToStudent.this).create();
                        alertDialog.setTitle("Pogreška");
                        alertDialog.setMessage("Odabrani kolegij je već upisan!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_seminars:
                Intent intent = new Intent(AddCourseToStudent.this, SeminarList.class);
                startActivity(intent);
                break;
            case R.id.nav_labs:
                intent = new Intent(AddCourseToStudent.this, LabsList.class);
                startActivity(intent);
                break;
            case R.id.nav_courses:
                intent = new Intent(AddCourseToStudent.this, ListCourses.class);
                startActivity(intent);
                break;
            case R.id.nav_schedule:
                intent = new Intent(AddCourseToStudent.this, ScheduleStudent.class);
                startActivity(intent);
                break;
            case R.id.nav_lectures:
                intent = new Intent(AddCourseToStudent.this, LecturesList.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
        }
        return true;
    }

    public boolean UpisaniKolegij(int idKolegija){
        for (Kolegij k: upisaniKolegijList)
        {
            if(k.getId()==idKolegija)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onWsDataLoaded(Object message, String status, Object data) {
        if(status.equals("OK") && message.equals("Pronađeni svi kolegiji.")){
            kolegijList = new ArrayList<Kolegij>();
            String dataStringKolegij = String.valueOf(data);
            try {
                JSONArray array = new JSONArray(dataStringKolegij);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject row = array.getJSONObject(i);
                    Kolegij noviKolegij= new Kolegij(row.getInt("id"),row.getString("naziv"), row.getInt("semestar"), row.getString("studij"));
                    kolegijList.add(noviKolegij);
                }
                spinnerAdapterKolegiji = new ArrayAdapter<Kolegij>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, kolegijList);
                spinnerKolegiji.setAdapter(spinnerAdapterKolegiji);
                spinnerAdapterKolegiji.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(status.equals("OK") && message.equals("Pronađeni kolegiji.")){
            upisaniKolegijList = new ArrayList<Kolegij>();
            String dataStringKolegij = String.valueOf(data);
            try {
                JSONArray array = new JSONArray(dataStringKolegij);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject row = array.getJSONObject(i);
                    Kolegij noviKolegij= new Kolegij(row.getInt("id"),row.getString("naziv"), row.getInt("semestar"), row.getString("studij"));
                    upisaniKolegijList.add(noviKolegij);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



}

