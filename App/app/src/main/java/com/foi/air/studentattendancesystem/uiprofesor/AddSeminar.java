package com.foi.air.studentattendancesystem.uiprofesor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.foi.air.core.entities.Dvorana;
import com.foi.air.core.entities.Kolegij;
import com.foi.air.core.entities.Profesor;
import com.foi.air.studentattendancesystem.LoginStudent;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoadedListener;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoader;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.sleep;

public class AddSeminar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SasWsDataLoadedListener {

    private Toolbar toolBar;
    private DrawerLayout drawer;
    private Button btnAddSeminar;
    EditText mEditPocetakSata;
    EditText mEditKrajSata;
    EditText mEditDozvoljenoIzostanaka;
    BetterSpinner spinnerKolegiji;
    BetterSpinner spinnerDvorane;
    BetterSpinner spinnerDani;

    String idProfesora;

    ArrayList<Kolegij> kolegijList;
    ArrayList<Dvorana> dvoranaList;
    ArrayAdapter<Kolegij> spinnerAdapterKolegiji;
    ArrayAdapter<Dvorana> spinnerAdapterDvorane;
    ArrayAdapter<CharSequence> spinnerAdapterDani;

    int idKolegija=0;
    int idDvorane=0;
    String danOdrzavanja=null;
    String pocetakSata=null;
    String krajStata=null;
    int dozvoljenoIzostanaka=0;

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


        spinnerKolegiji = findViewById(R.id.spinnerKolegiji);
        spinnerAdapterKolegiji = new ArrayAdapter<Kolegij>(this, android.R.layout.simple_dropdown_item_1line, kolegijList);
        spinnerKolegiji.setAdapter(spinnerAdapterKolegiji);
        spinnerKolegiji.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Kolegij kolegij = (Kolegij) parent.getItemAtPosition(position);
                idKolegija = kolegij.getId();
            }
        });

        spinnerDvorane = findViewById(R.id.spinnerDvorane);
        spinnerAdapterDvorane = new ArrayAdapter<Dvorana>(this, android.R.layout.simple_dropdown_item_1line, dvoranaList);
        spinnerDvorane.setAdapter(spinnerAdapterDvorane);
        spinnerDvorane.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Dvorana dvorana = (Dvorana) parent.getItemAtPosition(position);
                idDvorane = dvorana.getIdDvorane();
            }
        });

        spinnerDani = findViewById(R.id.spinnerDanOdrzavanja);
        spinnerAdapterDani = ArrayAdapter.createFromResource(this,R.array.dani_u_tjednu,android.R.layout.simple_dropdown_item_1line);
        spinnerDani.setAdapter(spinnerAdapterDani);
        spinnerDani.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                danOdrzavanja = String.valueOf(parent.getItemAtPosition(position));
            }
        });

        btnAddSeminar = findViewById(R.id.buttonDodajSeminar);
        btnAddSeminar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(idKolegija !=0 && idDvorane !=0 && danOdrzavanja != null && pocetakSata != null && krajStata != null && dozvoljenoIzostanaka !=0){
                    mEditPocetakSata = findViewById(R.id.editTextPocetak);
                    pocetakSata = mEditPocetakSata.getText().toString();
                    mEditKrajSata = findViewById(R.id.editTextKraj);
                    krajStata = mEditKrajSata.getText().toString();
                    mEditDozvoljenoIzostanaka = findViewById(R.id.editTextDozvoljenoIzostanaka);
                    dozvoljenoIzostanaka = Integer.parseInt(mEditDozvoljenoIzostanaka.getText().toString());
                    SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
                    sasWsDataLoader.dodajSeminar(Integer.parseInt(idProfesora),idKolegija,dozvoljenoIzostanaka,pocetakSata,krajStata,danOdrzavanja,idDvorane,"Seminar");
                    Toast.makeText(getApplicationContext(),"Seminar je dodan!", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(AddSeminar.this).create();
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
                spinnerAdapterKolegiji = new ArrayAdapter<Kolegij>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, kolegijList);
                spinnerKolegiji.setAdapter(spinnerAdapterKolegiji);
                spinnerAdapterKolegiji.notifyDataSetChanged();
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
                spinnerAdapterDvorane = new ArrayAdapter<Dvorana>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, dvoranaList);
                spinnerDvorane.setAdapter(spinnerAdapterDvorane);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

