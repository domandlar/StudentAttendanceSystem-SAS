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

import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.adaptersprofesor.ListOfSeminarsAdapter;
import com.foi.air.studentattendancesystem.adaptersprofesor.Seminars;

import java.util.ArrayList;
import java.util.List;

public class ListOfSeminars extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolBar;

    private DrawerLayout drawer;

    RecyclerView recyclerView;
    ListOfSeminarsAdapter adapter;

    List<Seminars> seminarsList;

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

        seminarsList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        seminarsList.add(
                new Seminars(
                        1,
                        "Analiza i razvoj programa",
                        "Petak",
                        "14:00-16:00",
                        "D9"));

        seminarsList.add(
                new Seminars(
                        1,
                        "Vanjskotrgovinsko poslovanje",
                        "Srijeda",
                        "17:00-18:00",
                        "D10"));

        seminarsList.add(
                new Seminars(
                        1,
                        "Operacijski sustavi",
                        "Utorak",
                        "10:00-14:00",
                        "D7"));
        seminarsList.add(
                new Seminars(
                        1,
                        "Diskretne strukture s teorijom grafova",
                        "Utorak",
                        "10:00-14:00",
                        "D7"));
        seminarsList.add(
                new Seminars(
                        1,
                        "Sigurnost informacijskih sustava",
                        "Utorak",
                        "10:00-14:00",
                        "D7"));
        seminarsList.add(
                new Seminars(
                        1,
                        "Računalom posredovana komunikacija",
                        "Utorak",
                        "10:00-14:00",
                        "D7"));

        adapter=new ListOfSeminarsAdapter(this, seminarsList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_seminars:
                Intent intent = new Intent(ListOfSeminars.this, ListOfSeminars.class);
                startActivity(intent);
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
}
