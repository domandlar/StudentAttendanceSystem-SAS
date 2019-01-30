package com.foi.air.studentattendancesystem.uiprofesor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.foi.air.core.SasWsDataLoadedListener;
import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.Profesor;
import com.foi.air.studentattendancesystem.MainActivity;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.adaptersprofesor.ListOfLecturesAdapter;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoader;
import com.foi.air.studentattendancesystem.attendance.CheckActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListOfLectures extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SasWsDataLoadedListener {

    private Toolbar toolBar;

    private DrawerLayout drawer;

    RecyclerView recyclerView;
    ListOfLecturesAdapter adapter;

    List<Aktivnost> lectureList;

    Aktivnost aktivnost;

    String idProfesora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_lectures);
        setTitle("Moja Predavanja");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        idProfesora = prefs.getString("idProfesora", "");

        Profesor profesor = new Profesor(Integer.parseInt(idProfesora));
        aktivnost = new Aktivnost("Predavanje");

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
        sasWsDataLoader.aktivnostForProfesor(profesor,aktivnost,this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_seminars:
                Intent intent = new Intent(ListOfLectures.this, ListOfSeminars.class);
                startActivity(intent);
                break;
            case R.id.nav_labs:
                intent = new Intent(ListOfLectures.this, ListOfLabs.class);
                startActivity(intent);
                break;
            case R.id.nav_courses:
                intent = new Intent(ListOfLectures.this, ListOfCourses.class);
                startActivity(intent);
                break;
            case R.id.nav_schedule:
                intent = new Intent(ListOfLectures.this, ScheduleProfesor.class);
                startActivity(intent);
                break;
            case R.id.nav_lectures:
                intent = new Intent(ListOfLectures.this, ListOfLectures.class);
                startActivity(intent);
                break;
            case R.id.nav_generate_passwords:
                intent = new Intent(ListOfLectures.this, CheckActivity.class);
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
        menuInflater.inflate(R.menu.app_bar_menu_lecture,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_dodaj_predavanje:
                Intent intent = new Intent(ListOfLectures.this, AddLecture.class);
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
        lectureList = new ArrayList<Aktivnost>();
        String dataString = String.valueOf(data);
        try {
            JSONArray array = new JSONArray(dataString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject row = array.getJSONObject(i);
                Aktivnost novaAktivnost = new Aktivnost("Predavanje");
                novaAktivnost.setIdAktivnosti(row.getInt("id"));
                novaAktivnost.setKolegij(row.getString("kolegij"));
                novaAktivnost.setDanIzvodenja(row.getString("dan_izvodenja"));
                novaAktivnost.setPocetak(row.getString("pocetak"));
                novaAktivnost.setKraj(row.getString("kraj"));
                //novaAktivnost.setDozvoljenoIzostanaka(row.getInt("dozvoljeno_izostanaka"));
                novaAktivnost.setDvorana(row.getString("dvorana"));
                lectureList.add(novaAktivnost);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter=new ListOfLecturesAdapter(this, lectureList);
        recyclerView.setAdapter(adapter);
    }
}
