package com.foi.air.studentattendancesystem.uiprofesor;

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.Profesor;
import com.foi.air.core.entities.Seminar;
import com.foi.air.core.entities.Student;
import com.foi.air.studentattendancesystem.MainActivity;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.adaptersprofesor.ListOfSeminarsAdapter;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoadedListener;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoader;

import java.util.ArrayList;
import java.util.List;

public class ListOfSeminars extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SasWsDataLoadedListener {

    private Toolbar toolBar;

    private DrawerLayout drawer;

    RecyclerView recyclerView;
    ListOfSeminarsAdapter adapter;

    List<Aktivnost> seminarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_seminars);

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //podaci s web servisa


        /*
        seminarList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        seminarList.add(
                new Seminar(
                        1,
                        "Analiza i razvoj programa",
                        "Petak",
                        "14:00-16:00",
                        "D9"));

        seminarList.add(
                new Seminar(
                        1,
                        "Vanjskotrgovinsko poslovanje",
                        "Srijeda",
                        "17:00-18:00",
                        "D10"));

        seminarList.add(
                new Seminar(
                        1,
                        "Operacijski sustavi",
                        "Utorak",
                        "10:00-14:00",
                        "D7"));
        seminarList.add(
                new Seminar(
                        1,
                        "Diskretne strukture s teorijom grafova",
                        "Utorak",
                        "10:00-14:00",
                        "D7"));
        seminarList.add(
                new Seminar(
                        1,
                        "Sigurnost informacijskih sustava",
                        "Utorak",
                        "10:00-14:00",
                        "D7"));
        seminarList.add(
                new Seminar(
                        1,
                        "Računalom posredovana komunikacija",
                        "Utorak",
                        "10:00-14:00",
                        "D7"));
        */

        Profesor profesor = new Profesor(29);
        Aktivnost aktivnost = new Aktivnost("Seminar");

        SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
        sasWsDataLoader.aktivnostForProfesor(profesor,aktivnost,this);

        //populate recyclerView
        seminarList = new ArrayList<Aktivnost>();

        adapter=new ListOfSeminarsAdapter(this, seminarList);
        //recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_seminars:
                Intent intent = new Intent(ListOfSeminars.this, ListOfSeminars.class);
                startActivity(intent);
                break;
            case R.id.nav_labs:
                intent = new Intent(ListOfSeminars.this, ListOfLabs.class);
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

    }
}
