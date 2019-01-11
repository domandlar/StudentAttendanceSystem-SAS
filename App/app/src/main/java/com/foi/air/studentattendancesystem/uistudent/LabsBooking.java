package com.foi.air.studentattendancesystem.uistudent;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.foi.air.core.entities.Kolegij;
import com.foi.air.core.entities.Student;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoadedListener;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoader;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LabsBooking extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SasWsDataLoadedListener {

    private Toolbar toolBar;
    private DrawerLayout drawer;

    private String idStudenta;
    private ArrayList<Kolegij> kolegijiList;
    private ArrayAdapter<Kolegij> spinnerAdapterKolokviji;
    private EditText mEditDanIzvodenja;
    private EditText mEditVrijemeOdrzavanja;
    private EditText mEditDvorana;
    private EditText mEditPredbiljezeno;
    private BetterSpinner spinnerKolegiji;

    private int idKolegija=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labs_booking);
        setTitle("Odabir Labosa");

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        idStudenta = sharedPreferences.getString("idStudenta", "");
        Student student = new Student(Integer.parseInt(idStudenta));

        SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
        sasWsDataLoader.kolegijForStudent(student,this);

        spinnerKolegiji = findViewById(R.id.spinnerKolegiji);
        spinnerAdapterKolokviji = new ArrayAdapter<Kolegij>(this, android.R.layout.simple_dropdown_item_1line, kolegijiList);
        spinnerKolegiji.setAdapter(spinnerAdapterKolokviji);
        spinnerKolegiji.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Kolegij kolegij = (Kolegij) parent.getItemAtPosition(position);
                idKolegija = kolegij.getId();
            }
        });
    }

    @Override
    public void onWsDataLoaded(Object message, String status, Object data) {
        if(status.equals("OK") && message.equals("Pronađeni kolegiji.")){
            kolegijiList = new ArrayList<Kolegij>();
            String dataStringKolegij = String.valueOf(data);
            try{
                JSONArray array = new JSONArray(dataStringKolegij);
                for (int i=0; i < array.length(); i++){
                    JSONObject row = array.getJSONObject(i);
                    Kolegij noviKolegij = new Kolegij(row.getInt("id"),row.getString("naziv"), row.getInt("semestar"), row.getString("studij"));
                    kolegijiList.add(noviKolegij);
                }
                spinnerAdapterKolokviji = new ArrayAdapter<Kolegij>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, kolegijiList);
                spinnerKolegiji.setAdapter(spinnerAdapterKolokviji);
                spinnerAdapterKolokviji.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
