package com.foi.air.studentattendancesystem.uistudent;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.BookedLab;
import com.foi.air.core.entities.Dvorana;
import com.foi.air.core.entities.Kolegij;
import com.foi.air.core.entities.Lab;
import com.foi.air.core.entities.Student;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.adaptersStudent.LabListAdapter;
import com.foi.air.studentattendancesystem.adaptersprofesor.ListOfActivitiesAdapter;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoadedListener;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoader;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    private Button btnPredbiljezi;
    private Button btnPonisti;
    private Kolegij kolegij;
    private SasWsDataLoader sasWsDataLoader;
    private Student student;

    RecyclerView recyclerView;
    LabListAdapter adapter;

    List<BookedLab> labList;

    Aktivnost aktivnost;

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

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewLabBooking);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        idStudenta = sharedPreferences.getString("idStudenta", "");
        student = new Student(Integer.parseInt(idStudenta));

        sasWsDataLoader = new SasWsDataLoader();
        sasWsDataLoader.kolegijForStudent(student,this);

        btnPonisti = findViewById(R.id.btnPonistiOdabir);
        spinnerKolegiji = findViewById(R.id.spinnerKolegiji);
        spinnerAdapterKolokviji = new ArrayAdapter<Kolegij>(this, android.R.layout.simple_dropdown_item_1line, kolegijiList);
        spinnerKolegiji.setAdapter(spinnerAdapterKolokviji);
        spinnerKolegiji.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                kolegij = (Kolegij) parent.getItemAtPosition(position);
                btnPonisti.setVisibility(View.INVISIBLE);
                sasWsDataLoader.labosForKolegij(kolegij, student,LabsBooking.this);
            }
        });
        btnPredbiljezi = findViewById(R.id.btnPredbiljezi);
        btnPredbiljezi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int brOdabira=0;
                for (int i=0; i < recyclerView.getChildCount(); i++){
                    LabListAdapter.LabViewHolder labViewHolder = (LabListAdapter.LabViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                    if(labViewHolder.odabir.isChecked()){
                        brOdabira++;
                    }

                }
                if(brOdabira > 1){
                    Toast.makeText(LabsBooking.this,"Ne možete odabrati više od 1 termina.", Toast.LENGTH_LONG).show();
                }else if(brOdabira < 1){
                    Toast.makeText(LabsBooking.this,"Odaberite termin labosa.", Toast.LENGTH_LONG).show();
                }else{
                    for (int i=0; i < recyclerView.getChildCount(); i++){
                        LabListAdapter.LabViewHolder labViewHolder = (LabListAdapter.LabViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                        if(labViewHolder.odabir.isChecked()){
                            aktivnost = new Aktivnost();
                            aktivnost.setIdAktivnosti(labViewHolder.odabir.getId());
                            break;
                        }
                    }
                    sasWsDataLoader.upisLabosa(student, aktivnost,LabsBooking.this);
                }

            }
        });

        btnPonisti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i < recyclerView.getChildCount(); i++){
                    LabListAdapter.LabViewHolder labViewHolder = (LabListAdapter.LabViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                    if(labViewHolder.odabir.isChecked()){
                        aktivnost = new Aktivnost();
                        aktivnost.setIdAktivnosti(labViewHolder.odabir.getId());
                        break;
                    }
                }
                sasWsDataLoader.ponistiOdabirLabosa(student, aktivnost,LabsBooking.this);
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
        } else if(status.equals("OK") && message.equals("Dohvaćeni su labosi odabranog kolegija")){
            labList = new ArrayList<BookedLab>();
            boolean upisan = false;
            String dataString = String.valueOf(data);
            try {
                JSONArray array = new JSONArray(dataString);
                JSONObject row = array.getJSONObject(0);
                int upisanaAktivnost = -1;
                try {
                    upisanaAktivnost = row.getInt("upisana_aktivnost");
                }catch (JSONException e){
                    e.printStackTrace();
                }
                for (int i = 1; i < array.length(); i++) {
                    row = array.getJSONObject(i);
                    BookedLab noviLab = new BookedLab();
                    noviLab.setIdAktivnosti(row.getInt("id_aktivnosti"));
                    noviLab.setDanIzvodenja(row.getString("dan_izvodenja"));
                    noviLab.setPocetak(row.getString("pocetak"));
                    noviLab.setKraj(row.getString("kraj"));
                    noviLab.setDvorana(row.getString("dvorana"));
                    noviLab.setBrojUpisanih(row.getInt("broj_upisanih"));
                    noviLab.setKapacitet(row.getInt("kapacitet"));
                    if(noviLab.getIdAktivnosti()==upisanaAktivnost){
                        noviLab.setUpisan(true);
                        upisan = true;
                    }
                    labList.add(noviLab);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(upisan)
                btnPonisti.setVisibility(View.VISIBLE);
            adapter=new LabListAdapter(this, labList, upisan);
            recyclerView.setAdapter(adapter);
        }else if(status.equals("NOT OK") && message.equals("Nema labosa za odabrani kolegij")){
            labList.clear();
            adapter= new LabListAdapter(this, labList, false);
            recyclerView.setAdapter(adapter);
        }else if(status.equals("OK") && message.equals("Labos upisan.")){
            sasWsDataLoader.labosForKolegij(kolegij, student,LabsBooking.this);
        }else if(status.equals("OK") && message.equals("Odabir labosa je poništen.")){
            sasWsDataLoader.labosForKolegij(kolegij, student,LabsBooking.this);
            btnPonisti.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
