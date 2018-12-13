package com.foi.air.studentattendancesystem.uiprofesor;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.Dvorana;
import com.foi.air.core.entities.Kolegij;
import com.foi.air.core.entities.Profesor;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoadedListener;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoader;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class AddSeminar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SasWsDataLoadedListener {

    private Toolbar toolBar;
    private DrawerLayout drawer;
    private Button btnAddSeminar;
    BetterSpinner spinnerKolegiji;

    String idProfesora;

    List<Kolegij> kolegijList;
    List<Dvorana> dvoranaList;
    ArrayAdapter<String> arrayAdapter;
    String[] ssss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seminar);

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

        SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
        sasWsDataLoader.kolegijForProfesor(profesor,this);
        sasWsDataLoader.Dvorane("predavaona");

        btnAddSeminar = findViewById(R.id.buttonDodajSeminar);
        btnAddSeminar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

        spinnerKolegiji = findViewById(R.id.spinnerKolegiji);




    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_seminars:
                Intent intent = new Intent(AddSeminar.this, ListOfSeminars.class);
                startActivity(intent);
        }
        return true;
    }

    @Override
    public void onWsDataLoaded(Object message, String status, Object data) {
        if(status.equals("OK") && message.equals("Pronađeni kolegiji.")){
            kolegijList = new ArrayList<Kolegij>();
            String dataStringKolegij = String.valueOf(data);
            try {
                JSONArray array = new JSONArray(dataStringKolegij);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject row = array.getJSONObject(i);
                    Kolegij noviKolegij= new Kolegij(row.getInt("id"),row.getString("naziv"), row.getInt("semestar"), row.getString("studij"));
                    kolegijList.add(noviKolegij);
                }

                ssss = new String[kolegijList.size()];
                for(int i=0;i<kolegijList.size();i++){
                    ssss[i] = kolegijList.get(i).getNaziv();
                }
                arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_dropdown_item_1line, ssss);
                spinnerKolegiji.setAdapter(arrayAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(status.equals("OK") && message.equals("Pronađene dvorane.")){
            dvoranaList = new ArrayList<Dvorana>();
            String dataStringDvorane = String.valueOf(data);
            try {
                JSONArray array = new JSONArray(dataStringDvorane);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject row = array.getJSONObject(i);
                    Dvorana novaDvorana= new Dvorana(row.getInt("id_dvorane"),row.getString("naziv"), row.getInt("kapacitet"));
                    dvoranaList.add(novaDvorana);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
