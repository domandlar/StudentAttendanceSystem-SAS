package com.foi.air.studentattendancesystem.uiprofesor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.foi.air.core.SasWsDataLoadedListener;
import com.foi.air.core.entities.Dvorana;
import com.foi.air.core.entities.Kolegij;
import com.foi.air.core.entities.Profesor;
import com.foi.air.studentattendancesystem.MainActivity;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoader;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class AddLab extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SasWsDataLoadedListener {

    private Toolbar toolBar;
    private DrawerLayout drawer;
    private Button btnAddLab;
    Button btnPocetakSataPicker;
    Button btnKrajSataPicker;
    EditText mEditPocetakSata;
    EditText mEditKrajSata;
    EditText mEditPocetakUpisa;
    EditText mEditKrajUpisa;
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
    String danOdrzavanja="";
    String pocetakSata="";
    String krajStata="";
    String pocetakUpisa="";
    String krajUpisa="";
    int dozvoljenoIzostanaka=0;

    int hour;
    int minute_x;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lab);
        setTitle("Dodaj Labos");



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
        sasWsDataLoader.Dvorane("laboratorij");

        mEditPocetakSata = findViewById(R.id.editTextPocetak);
        mEditKrajSata = findViewById(R.id.editTextKraj);

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

        btnAddLab = findViewById(R.id.buttonDodajLabos);
        btnAddLab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(idKolegija !=0 && idDvorane !=0 && danOdrzavanja != null){
                    pocetakSata = mEditPocetakSata.getText().toString();
                    krajStata = mEditKrajSata.getText().toString();
                    mEditPocetakUpisa = findViewById(R.id.editTextPocetakUpisa);
                    pocetakUpisa = mEditPocetakUpisa.getText().toString();
                    mEditKrajUpisa = findViewById(R.id.editTextKrajUpisa);
                    krajUpisa = mEditKrajUpisa.getText().toString();
                    mEditDozvoljenoIzostanaka = findViewById(R.id.editTextDozvoljenoIzostanaka);
                    dozvoljenoIzostanaka = Integer.parseInt(mEditDozvoljenoIzostanaka.getText().toString());
                    SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
                    sasWsDataLoader.dodajAktivnost(Integer.parseInt(idProfesora),idKolegija,dozvoljenoIzostanaka,pocetakSata,krajStata,danOdrzavanja,idDvorane,"Labosi",pocetakUpisa,krajUpisa);
                    Toast.makeText(getApplicationContext(),"Labos je dodan!", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(AddLab.this).create();
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
        btnPocetakSataPicker = findViewById(R.id.btnPocetakSataPicker);
        btnPocetakSataPicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(0);
            }
        });
        btnKrajSataPicker = findViewById(R.id.btnKrajSataPicker);
        btnKrajSataPicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(1);
            }
        });

    }
    @Override
    protected Dialog onCreateDialog(int id){
        if(id == 0){
            return new TimePickerDialog(AddLab.this, pocetakTimePickerListener, hour, minute_x, true);
        }else if(id == 1){
            return new TimePickerDialog(AddLab.this, krajTimePickerListener, hour, minute_x, true);
        }else return null;
    }
    protected TimePickerDialog.OnTimeSetListener pocetakTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            minute_x = minute;
            pocetakSata = String.format("%02d:%02d:%02d", hour, minute_x, 0);
            mEditPocetakSata.setText(pocetakSata);
        }
    };
    protected TimePickerDialog.OnTimeSetListener krajTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            minute_x = minute;
            krajStata = String.format("%02d:%02d:%02d", hour, minute_x, 0);
            mEditKrajSata.setText(krajStata);
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_seminars:
                Intent intent = new Intent(AddLab.this, ListOfSeminars.class);
                startActivity(intent);
                break;
            case R.id.nav_labs:
                intent = new Intent(AddLab.this, ListOfLabs.class);
                startActivity(intent);
                break;
            case R.id.nav_courses:
                intent = new Intent(AddLab.this, ListOfCourses.class);
                startActivity(intent);
                break;
            case R.id.nav_schedule:
                intent = new Intent(AddLab.this, ScheduleProfesor.class);
                startActivity(intent);
                break;
            case R.id.nav_lectures:
                intent = new Intent(AddLab.this, ListOfLectures.class);
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

