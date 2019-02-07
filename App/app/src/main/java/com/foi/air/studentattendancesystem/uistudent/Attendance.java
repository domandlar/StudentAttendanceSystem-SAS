package com.foi.air.studentattendancesystem.uistudent;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.foi.air.core.SasWsDataLoadedListener;
import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.Dolazak;
import com.foi.air.core.entities.Kolegij;
import com.foi.air.core.entities.Student;
import com.foi.air.core.entities.TipAktivnosti;
import com.foi.air.studentattendancesystem.MainActivity;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.adaptersStudent.AttendanceListAdapter;
import com.foi.air.studentattendancesystem.attendance.CheckActivity;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoader;

import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Attendance extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SasWsDataLoadedListener {


    private Toolbar toolBar;
    private DrawerLayout drawer;
    private Button btnPrisustvo;
    BetterSpinner spinnerKolegiji;
    BetterSpinner spinnerTipAktivnosti;

    Kolegij odabraniKolegij;
    String idStudenta;


    ArrayList<Kolegij> kolegijList;
    ArrayList<TipAktivnosti> tipAktivnostList;
    ArrayAdapter<Kolegij> spinnerAdapterKolegiji;
    ArrayAdapter<TipAktivnosti> spinnerAdapterTipAktivnosti;


    int idKolegija = 0;
    int idTipAktivnosti = 0;

    RecyclerView recyclerView;
    AttendanceListAdapter adapter;
    List<Dolazak> listaDolazaka;
    SasWsDataLoader sasWsDataLoader;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        setTitle("Provjeri prisutnost");

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        drawer = findViewById(R.id.drawer_layout);

        //osluskivanje gumba "Moji seminari"
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        idStudenta = prefs.getString("idStudenta","");
        Student student = new Student(Integer.parseInt(idStudenta));


        sasWsDataLoader = new SasWsDataLoader();
        sasWsDataLoader.kolegijForStudent(student,this);

        spinnerKolegiji = findViewById(R.id.spinnerKolegiji);
        spinnerAdapterKolegiji = new ArrayAdapter<Kolegij>(this, android.R.layout.simple_dropdown_item_1line,kolegijList);
        spinnerKolegiji.setAdapter(spinnerAdapterKolegiji);
        spinnerKolegiji.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                odabraniKolegij = (Kolegij) parent.getItemAtPosition(position);
                sasWsDataLoader.tipAktivnostiForKolegij(odabraniKolegij,Attendance.this);
                idKolegija = odabraniKolegij.getId();
                spinnerTipAktivnosti.setVisibility(View.VISIBLE);
            }
        });


        spinnerTipAktivnosti = findViewById(R.id.spinnerAktivnosti);
        spinnerAdapterTipAktivnosti = new ArrayAdapter<TipAktivnosti>(this,android.R.layout.simple_dropdown_item_1line, tipAktivnostList);
        spinnerTipAktivnosti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TipAktivnosti tipAktivnosti = (TipAktivnosti) parent.getItemAtPosition(position);
                idTipAktivnosti = tipAktivnosti.getId();
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recyclerAttendance);
        recyclerView.setLayoutManager(new LinearLayoutManager(Attendance.this));

        btnPrisustvo = findViewById(R.id.buttonPrisutnost);
        btnPrisustvo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idKolegija!=0 && idTipAktivnosti!=0){
                    try{

                        sasWsDataLoader.prisustvoStudenta(Integer.parseInt(idStudenta), idKolegija, idTipAktivnosti, Attendance.this);
                    }
                    catch(Exception e){
                        e.getMessage();
                    }
                }else {
                    AlertDialog alertDialog = new AlertDialog.Builder(Attendance.this).create();
                    alertDialog.setTitle("Pogreška");
                    alertDialog.setMessage("Niste unjeli sva polja!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

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
                intent = new Intent(Attendance.this, CheckActivity.class);
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
        if(status.equals("OK") && message.equals("Pronađeni kolegiji.")) {
            kolegijList = new ArrayList<Kolegij>();
            String dataStringKolegij = String.valueOf(data);
            try {
                JSONArray array = new JSONArray(dataStringKolegij);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject row = array.getJSONObject(i);
                    Kolegij noviKolegij = new Kolegij(row.getInt("id"), row.getString("naziv"), row.getInt("semestar"), row.getString("studij"));
                    kolegijList.add(noviKolegij);
                }
                spinnerAdapterKolegiji = new ArrayAdapter<Kolegij>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, kolegijList);
                spinnerKolegiji.setAdapter(spinnerAdapterKolegiji);
                spinnerAdapterKolegiji.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(status.equals("OK") && message.equals("Dohvaćeni su tipovi aktivnosti odabranog kolegija")){
            tipAktivnostList = new ArrayList<TipAktivnosti>();
            String dataStringAktivnosti = String.valueOf(data);
            try {
                JSONArray array = new JSONArray(dataStringAktivnosti);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject row = array.getJSONObject(i);
                    TipAktivnosti tipAktivnosti= new TipAktivnosti();
                    tipAktivnosti.setId(row.getInt("id_tip_aktivnosti"));
                    tipAktivnosti.setNaziv(row.getString("naziv"));
                    tipAktivnostList.add(tipAktivnosti);
                }
                spinnerAdapterTipAktivnosti = new ArrayAdapter<TipAktivnosti>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, tipAktivnostList);
                spinnerTipAktivnosti.setAdapter(spinnerAdapterTipAktivnosti);
                spinnerAdapterTipAktivnosti.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        else if(status.equals("OK") && message.equals("Dohvaćena su prisustva studenta")){
            listaDolazaka = new ArrayList<Dolazak>();
            String dataStringPrisutnost = String.valueOf(data);
            try {
                JSONArray array = new JSONArray(dataStringPrisutnost);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject row = array.getJSONObject(i);
                    Dolazak dolazak= new Dolazak();
                    dolazak.setTjedanNastave(row.getInt("tjedan_nastave"));
                    if(row.getInt("prisutan")==1)
                        dolazak.setPrisustvo(true);
                    else
                        dolazak.setPrisustvo(false);
                    listaDolazaka.add(dolazak);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter= new AttendanceListAdapter(this,listaDolazaka);
            recyclerView.setAdapter(adapter);
        }
        else if (status.equals("NOT OK")&& message.equals("Nema zabilježenih prisustva za odabranog studenta")){
            listaDolazaka=new ArrayList<Dolazak>();
            adapter= new AttendanceListAdapter(this,listaDolazaka);
            recyclerView.setAdapter(adapter);

            AlertDialog alertDialog = new AlertDialog.Builder(Attendance.this).create();
            alertDialog.setTitle("Obavijest");
            alertDialog.setMessage("Student nema zabilježenihh prisustava");
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

