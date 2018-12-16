package com.foi.air.studentattendancesystem.uistudent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.Profesor;
import com.foi.air.core.entities.Seminar;
import com.foi.air.core.entities.Student;
import com.foi.air.studentattendancesystem.MainActivity;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.adaptersStudent.SeminarListAdapter;
import com.foi.air.studentattendancesystem.adaptersprofesor.ListOfActivitiesAdapter;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoadedListener;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoader;
import com.foi.air.studentattendancesystem.uiprofesor.AddSeminar;
import com.foi.air.studentattendancesystem.uiprofesor.ListOfLabs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SeminarList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SasWsDataLoadedListener {

    private Toolbar toolBar;

    private DrawerLayout drawer;

    RecyclerView recyclerView;
    ListOfActivitiesAdapter adapter;

    List<Aktivnost> seminarList;

    Aktivnost aktivnost;

    String idStudenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seminar_list);

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        idStudenta = prefs.getString("idStudenta", "");
        Student student = new Student(Integer.parseInt(idStudenta));
        aktivnost = new Aktivnost("Seminar");

        //hohvacanje podataka sa servisa
        SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
        sasWsDataLoader.aktivnostForStudent(student, aktivnost, this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_seminars:
                Intent intent = new Intent(SeminarList.this, SeminarList.class);
                startActivity(intent);
                break;
            case R.id.nav_labs:
                intent = new Intent(SeminarList.this, LabsList.class);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }

    //drawer
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onWsDataLoaded(Object message, String status, Object data) {
        seminarList = new ArrayList<Aktivnost>();
        String dataString = String.valueOf(data);
        try {
            JSONArray array = new JSONArray(dataString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject row = array.getJSONObject(i);
                aktivnost.setIdAktivnosti(row.getInt("id"));
                aktivnost.setKolegij(row.getString("kolegij"));
                aktivnost.setDanIzvodenja(row.getString("dan_izvodenja"));
                aktivnost.setPocetak(row.getString("pocetak"));
                aktivnost.setKraj(row.getString("kraj"));
                //aktivnost.setDozvoljenoIzostanaka(row.getInt("dozvoljeno_izostanaka"));
                aktivnost.setDvorana(row.getString("dvorana"));
                seminarList.add(aktivnost);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new ListOfActivitiesAdapter(this, seminarList);
        recyclerView.setAdapter(adapter);
    }
}

