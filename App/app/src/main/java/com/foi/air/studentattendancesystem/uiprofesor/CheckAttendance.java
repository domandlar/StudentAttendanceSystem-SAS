package com.foi.air.studentattendancesystem.uiprofesor;

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

import com.foi.air.core.entities.Dolazak;
import com.foi.air.core.entities.Kolegij;
import com.foi.air.core.entities.Profesor;
import com.foi.air.core.entities.Student;
import com.foi.air.core.entities.TipAktivnosti;
import com.foi.air.studentattendancesystem.MainActivity;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.adaptersprofesor.ListOfAttendanceAdapter;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoadedListener;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoader;
import com.foi.air.studentattendancesystem.password.PasswordFragment;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CheckAttendance extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SasWsDataLoadedListener {

    private Toolbar toolBar;
    private DrawerLayout drawer;
    private Button btnPrikaziEvidenciju;
    BetterSpinner spinnerKolegiji;
    BetterSpinner spinnerTipAktivnosti;
    BetterSpinner spinnerStudent;

    String idProfesora;
    Kolegij odabraniKolegij;

    ArrayList<Kolegij> kolegijList;
    ArrayList<Student> studentList;
    ArrayList<TipAktivnosti> tipAktivnostList;
    ArrayAdapter<Kolegij> spinnerAdapterKolegiji;
    ArrayAdapter<TipAktivnosti> spinnerAdapterTipAktivnosti;
    ArrayAdapter<Student> spinnerAdapterStudenti;

    int idKolegija=0;
    int idStudent=0;
    int idTipAktivnosti=0;

    SasWsDataLoader sasWsDataLoader;

    RecyclerView recyclerView;
    ListOfAttendanceAdapter adapter;
    List<Dolazak> listaDolazaka;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_attendance);
        setTitle("Evidencija prisustva");



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
        idProfesora = prefs.getString("idProfesora", "");
        Profesor profesor = new Profesor(Integer.parseInt(idProfesora));


        sasWsDataLoader = new SasWsDataLoader();
        sasWsDataLoader.kolegijForProfesor(profesor,this);



        spinnerKolegiji = findViewById(R.id.spinnerKolegiji);
        spinnerAdapterKolegiji = new ArrayAdapter<Kolegij>(this, android.R.layout.simple_dropdown_item_1line, kolegijList);
        spinnerKolegiji.setAdapter(spinnerAdapterKolegiji);
        spinnerKolegiji.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                odabraniKolegij = (Kolegij) parent.getItemAtPosition(position);
                sasWsDataLoader.tipAktivnostiForKolegij(odabraniKolegij,CheckAttendance.this);
                sasWsDataLoader.studentiForKolegiji(odabraniKolegij,CheckAttendance.this);
                idKolegija=odabraniKolegij.getId();
                spinnerTipAktivnosti.setVisibility(View.VISIBLE);
                spinnerStudent.setVisibility(View.VISIBLE);
            }
        });





        spinnerTipAktivnosti = findViewById(R.id.spinnerTipAktivnosti);
        spinnerAdapterTipAktivnosti = new ArrayAdapter<TipAktivnosti>(this, android.R.layout.simple_dropdown_item_1line, tipAktivnostList);

        spinnerTipAktivnosti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TipAktivnosti tipAktivnost = (TipAktivnosti) parent.getItemAtPosition(position);
                idTipAktivnosti= tipAktivnost.getId();
            }
        });

        spinnerStudent = findViewById(R.id.spinnerStudent);
        spinnerAdapterStudenti = new ArrayAdapter<Student>(this, android.R.layout.simple_dropdown_item_1line, studentList);
        spinnerStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student student = (Student) parent.getItemAtPosition(position);
                idStudent=student.getIdStudenta();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewAttendance);
        recyclerView.setLayoutManager(new LinearLayoutManager(CheckAttendance.this));


        btnPrikaziEvidenciju = findViewById(R.id.buttonPrikaziEvidenciju);
        btnPrikaziEvidenciju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idKolegija!=0 && idStudent!=0 && idTipAktivnosti!=0){
                    try {
                        sasWsDataLoader.prisustvoStudenta(idStudent,idKolegija,idTipAktivnosti,CheckAttendance.this);

                }
                catch (Exception e){
                        e.getMessage();
                    }
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(CheckAttendance.this).create();
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
                Intent intent = new Intent(CheckAttendance.this, ListOfSeminars.class);
                startActivity(intent);
                break;
            case R.id.nav_labs:
                intent = new Intent(CheckAttendance.this, ListOfLabs.class);
                startActivity(intent);
                break;
            case R.id.nav_courses:
                intent = new Intent(CheckAttendance.this, ListOfCourses.class);
                startActivity(intent);
                break;
            case R.id.nav_schedule:
                intent = new Intent(CheckAttendance.this, ScheduleProfesor.class);
                startActivity(intent);
                break;
            case R.id.nav_lectures:
                intent = new Intent(CheckAttendance.this, ListOfLectures.class);
                startActivity(intent);
                break;
            case R.id.nav_generate_passwords:
                intent = new Intent(CheckAttendance.this, PasswordFragment.class);
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
        else if(status.equals("OK") && message.equals("Dohvaćeni su studenti sa odabranog kolegija")){
            studentList = new ArrayList<Student>();
            String dataStringStudenti = String.valueOf(data);
            try {
                JSONArray array = new JSONArray(dataStringStudenti);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject row = array.getJSONObject(i);
                    Student student= new Student();
                    student.setIdStudenta(row.getInt("id_studenta"));
                    student.setIme(row.getString("ime"));
                    student.setPrezime(row.getString("prezime"));
                    studentList.add(student);
                }
                spinnerAdapterStudenti = new ArrayAdapter<Student>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, studentList);
                spinnerStudent.setAdapter(spinnerAdapterStudenti);
                spinnerAdapterStudenti.notifyDataSetChanged();
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

            adapter= new ListOfAttendanceAdapter(this,listaDolazaka);
            recyclerView.setAdapter(adapter);
        }
        else if (status.equals("NOT OK")&& message.equals("Nema zabilježenih prisustva za odabranog studenta")){
            listaDolazaka=new ArrayList<Dolazak>();
            adapter= new ListOfAttendanceAdapter(this,listaDolazaka);
            recyclerView.setAdapter(adapter);

            AlertDialog alertDialog = new AlertDialog.Builder(CheckAttendance.this).create();
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
