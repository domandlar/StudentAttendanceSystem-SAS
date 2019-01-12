package com.foi.air.studentattendancesystem.uiprofesor;

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
import android.widget.EditText;
import android.widget.Toast;
import com.foi.air.core.entities.Profesor;
import com.foi.air.studentattendancesystem.MainActivity;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoader;
import com.weiwangcn.betterspinner.library.BetterSpinner;


public class AddCourse extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar toolBar;
    private DrawerLayout drawer;
    private Button btnAddCourse;
    EditText mEditNazivKolegija;
    EditText mEditSemestar;
    BetterSpinner spinnerStudij;

    String idProfesora;


    String nazivKolegija=null;
    String nazivStudija=null;
    int semestarIzvodjenja=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setTitle("Novi kolegij");


        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        idProfesora = prefs.getString("idProfesora", "");
        Profesor profesor = new Profesor(Integer.parseInt(idProfesora));

        String[] studiji = {"IPS", "PS", "IS", "EP", "PITUP", "IPI", "OPS", "BPBZ", "IO", "EPDS"};
        spinnerStudij = findViewById(R.id.spinnerStudij);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddCourse.this, android.R.layout.simple_spinner_item, studiji);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStudij.setAdapter(adapter);
        spinnerStudij.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                nazivStudija = String.valueOf(parent.getItemAtPosition(position));
            }
        });

        btnAddCourse = findViewById(R.id.buttonDodajKolegij);
        btnAddCourse.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                mEditNazivKolegija = findViewById(R.id.editTextNazivKolegija);
                nazivKolegija=mEditNazivKolegija.getText().toString();

                mEditSemestar = findViewById(R.id.editTextSemestar);
                semestarIzvodjenja = Integer.parseInt(mEditSemestar.getText().toString());

                if(nazivKolegija !=null && semestarIzvodjenja != 0 && nazivStudija != null){
                    SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
                    sasWsDataLoader.dodajKolegij(Integer.parseInt(idProfesora), nazivKolegija, semestarIzvodjenja, nazivStudija);
                    Toast.makeText(getApplicationContext(),"Kolegij je dodan!", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(AddCourse.this).create();
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
                Intent intent = new Intent(AddCourse.this, ListOfSeminars.class);
                startActivity(intent);
                break;
            case R.id.nav_labs:
                intent = new Intent(AddCourse.this, ListOfLabs.class);
                startActivity(intent);
                break;
            case R.id.nav_courses:
                intent = new Intent(AddCourse.this, ListOfCourses.class);
                startActivity(intent);
                break;
            case R.id.nav_schedule:
                intent = new Intent(AddCourse.this, ScheduleProfesor.class);
                startActivity(intent);
                break;
            case R.id.nav_lectures:
                intent = new Intent(AddCourse.this, ListOfLectures.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
        }
        return true;
    }

}

