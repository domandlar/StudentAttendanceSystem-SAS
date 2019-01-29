package com.foi.air.studentattendancesystem.uiprofesor;

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
import android.view.View;
import android.widget.ProgressBar;

import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.Profesor;
import com.foi.air.studentattendancesystem.MainActivity;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.adaptersprofesor.ListOfActivitiesAdapter;

import com.foi.air.studentattendancesystem.loaders.SasWsDataLoadedListener;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoader;
import com.foi.air.studentattendancesystem.password.PasswordActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListOfSeminars extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SasWsDataLoadedListener {

    private Toolbar toolBar;

    private DrawerLayout drawer;

    RecyclerView recyclerView;
    ListOfActivitiesAdapter adapter;

    List<Aktivnost> seminarList;

    Aktivnost aktivnost;

    String idProfesora;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_seminars);
        setTitle("Moji Seminari");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        idProfesora = prefs.getString("idProfesora", "");
        Profesor profesor = new Profesor(Integer.parseInt(idProfesora));
        aktivnost = new Aktivnost("Seminar");

        //hohvacanje podataka sa servisa
        SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
        sasWsDataLoader.aktivnostForProfesor(profesor,aktivnost,this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        Log.d("izbornik....: ", menuItem.toString() +  "    " + menuItem.getItemId() + "    " + R.id.nav_attendance);
        switch (menuItem.getItemId()){
            case R.id.nav_seminars:
                intent = new Intent(ListOfSeminars.this, ListOfSeminars.class);
                startActivity(intent);
                break;
            case R.id.nav_courses:
                intent = new Intent(ListOfSeminars.this, ListOfCourses.class);
                startActivity(intent);
                break;
            case R.id.nav_labs:
                intent = new Intent(this, ListOfLabs.class);
                startActivity(intent);
                break;
            case R.id.nav_lectures:
                intent = new Intent(this, ListOfLectures.class);
                startActivity(intent);
                break;

            case R.id.nav_schedule:
                intent = new Intent(this, ScheduleProfesor.class);
                startActivity(intent);
                break;
            case R.id.nav_attendance_preview:
                intent = new Intent(this, CheckAttendance.class);
                startActivity(intent);
                break;
            case R.id.nav_generate_passwords:
                intent = new Intent(ListOfSeminars.this, PasswordActivity.class);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_dodaj_seminar:
                Intent intent = new Intent(ListOfSeminars.this, AddSeminar.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //drawer
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onWsDataLoaded(Object message, String status, Object data) {
        progressBar.setVisibility(View.GONE);
        seminarList = new ArrayList<Aktivnost>();
        String dataString = String.valueOf(data);
        try {
            JSONArray array = new JSONArray(dataString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject row = array.getJSONObject(i);
                Aktivnost novaAktivnost = new Aktivnost("Seminar");
                novaAktivnost.setIdAktivnosti(row.getInt("id"));
                novaAktivnost.setKolegij(row.getString("kolegij"));
                novaAktivnost.setDanIzvodenja(row.getString("dan_izvodenja"));
                novaAktivnost.setPocetak(row.getString("pocetak"));
                novaAktivnost.setKraj(row.getString("kraj"));
                //novaAktivnost.setDozvoljenoIzostanaka(row.getInt("dozvoljeno_izostanaka"));
                //aktivnost.setDozvoljenoIzostanaka(row.getInt("dozvoljeno_izostanaka"));
                novaAktivnost.setDvorana(row.getString("dvorana"));
                seminarList.add(novaAktivnost);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter=new ListOfActivitiesAdapter(this, seminarList);
        recyclerView.setAdapter(adapter);
    }
}
