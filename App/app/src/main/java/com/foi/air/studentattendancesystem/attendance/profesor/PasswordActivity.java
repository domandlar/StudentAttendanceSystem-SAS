package com.foi.air.studentattendancesystem.attendance.profesor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.foi.air.core.NavigationItem;
import com.foi.air.core.SasWsDataLoadedListener;
import com.foi.air.core.entities.AktivnostiProfesora;
import com.foi.air.core.entities.Profesor;
import com.foi.air.studentattendancesystem.MainActivity;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.attendance.ModuleActivity;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoader;
import com.foi.air.studentattendancesystem.uiprofesor.ListOfLabs;
import com.foi.air.studentattendancesystem.uiprofesor.ListOfSeminars;
import com.foi.air.studentattendancesystem.uiprofesor.ScheduleProfesor;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PasswordActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SasWsDataLoadedListener, NavigationItem {

    private Toolbar toolBar;

    private DrawerLayout drawer;
    BetterSpinner spinnerTipAktivnosti;
    BetterSpinner spinnerTjedanNastave;
    BetterSpinner spinnerModuli;
    Button btnOdaberi;
    ArrayAdapter<AktivnostiProfesora> spinnerAdapterAktivnosti;
    ArrayAdapter<Integer> spinnerAdapterTjedni;
    ArrayAdapter<String> spinnerAdapterModuli;

    private String uloga;
    String idProfesora;
    int idAktivnosti=0;
    int tjedanNastve=-1;
    int modul=-1;
    ArrayList<AktivnostiProfesora> aktivnostiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        setTitle("Odabir aktivnosti");


        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        uloga = getIntent().getStringExtra("uloga");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        idProfesora = prefs.getString("idProfesora", "");
        Profesor profesor = new Profesor(Integer.parseInt(idProfesora));

        SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
        sasWsDataLoader.allAktivnostForProfesor(profesor,this);

        spinnerTipAktivnosti = (BetterSpinner) findViewById(com.foi.air.passwordrecord.R.id.spinnerTpAktivnosti);
        spinnerAdapterAktivnosti = new ArrayAdapter<AktivnostiProfesora>(getApplicationContext(), com.foi.air.passwordrecord.R.layout.multiline_spinner_dropdown_item, aktivnostiList);
        spinnerTipAktivnosti.setAdapter(spinnerAdapterAktivnosti);
        spinnerTipAktivnosti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                AktivnostiProfesora aktivnostProfesora = (AktivnostiProfesora) parent.getItemAtPosition(position);
                idAktivnosti = aktivnostProfesora.getIdAktivnosti();
            }
        });


        Integer[] weeks = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        spinnerTjedanNastave = (BetterSpinner) findViewById(R.id.spinnerTjedanNastave);
        spinnerAdapterTjedni = new ArrayAdapter<Integer>(getApplicationContext(), R.layout.multiline_spinner_dropdown_item, weeks);
        spinnerTjedanNastave.setAdapter(spinnerAdapterTjedni);
        spinnerTjedanNastave.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                tjedanNastve = (Integer) parent.getItemAtPosition(position);
            }
        });

        String[] modules = new String[]{"Lozinkom", "Prepoznavanjem lica"};
        spinnerModuli = (BetterSpinner) findViewById(R.id.spinnerModuli);
        spinnerAdapterModuli = new ArrayAdapter<String>(getApplicationContext(), R.layout.multiline_spinner_dropdown_item, modules);
        spinnerModuli.setAdapter(spinnerAdapterModuli);
        spinnerModuli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                modul =  position;
            }
        });

        btnOdaberi = findViewById(R.id.buttonOdaberi);
        btnOdaberi.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(modul != -1 && idAktivnosti != 0 && tjedanNastve != -1){
                    Intent intent = new Intent (PasswordActivity.this, ModuleActivity.class);
                    intent.putExtra("uloga", uloga);
                    intent.putExtra("modul", modul);
                    intent.putExtra("idAktivnosti",idAktivnosti);
                    intent.putExtra("idUloge",Integer.parseInt(idProfesora) );
                    intent.putExtra("tjedanNastave", tjedanNastve);
                    startActivity(intent);
                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(PasswordActivity.this).create();
                    alertDialog.setTitle("Rezultat prijave");
                    alertDialog.setMessage("Niste odabrali sva polja!");
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
                Intent intent = new Intent(PasswordActivity.this, ListOfSeminars.class);
                startActivity(intent);
                break;
            case R.id.nav_labs:
                intent = new Intent(PasswordActivity.this, ListOfLabs.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                //finish();
                break;
            case R.id.nav_schedule:
                intent = new Intent(PasswordActivity.this, ScheduleProfesor.class);
                startActivity(intent);
                break;
            case R.id.nav_generate_passwords:
                intent = new Intent(PasswordActivity.this, PasswordActivity.class);
                intent.putExtra("uloga","profesor");
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onWsDataLoaded(Object message, String status, Object data) {
        aktivnostiList = new ArrayList<AktivnostiProfesora>();
        String dataString = String.valueOf(data);
        try {
            JSONArray array = new JSONArray(dataString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject row = array.getJSONObject(i);
                AktivnostiProfesora aktivnostiProfesora= new AktivnostiProfesora(
                        row.getInt("id"),
                        row.getString("pocetak"),
                        row.getString("kraj"),
                        row.getString("dan_izvodenja") ,
                        row.getString("dvorana"),
                        row.getInt("id_tip_aktivnosti"),
                        row.getString("tip_aktivnosti"),
                        row.getString("kolegij"));
                aktivnostiList.add(aktivnostiProfesora);
            }

            spinnerAdapterAktivnosti = new ArrayAdapter<AktivnostiProfesora>(getApplicationContext(), com.foi.air.passwordrecord.R.layout.multiline_spinner_dropdown_item, aktivnostiList);
            spinnerTipAktivnosti.setAdapter(spinnerAdapterAktivnosti);
            spinnerAdapterAktivnosti.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Fragment getFragment() {
        return null;
    }

    @Override
    public void setData(int idAktivnosti, int idUloge, int tjedanNastave) {

    }

    @Override
    public boolean getData() {
        return false;
    }

}
