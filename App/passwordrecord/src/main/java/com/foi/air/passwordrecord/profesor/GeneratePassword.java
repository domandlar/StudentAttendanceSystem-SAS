package com.foi.air.passwordrecord.profesor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.foi.air.core.NavigationItem;
import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.AktivnostiProfesora;
import com.foi.air.core.entities.Kolegij;
import com.foi.air.core.entities.Profesor;
import com.foi.air.passwordrecord.R;
import com.foi.air.passwordrecord.loaders.SasWsDataLoadedListener;
import com.foi.air.passwordrecord.loaders.SasWsDataLoader;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GeneratePassword extends Fragment implements SasWsDataLoadedListener, NavigationItem {
    TextView txtCountDown;
    TextView txtPassword;
    String idProfesora;
    int idAktivnosti=0;
    int tjedanNastve=0;

    CountDownTimer countDownTimer;
    long timeLeftInMilliseconds=20000; //20 sec
    boolean timerRunning;

    String generatedPassword;

    View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_generate_password,
                container, false);


        SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
        sasWsDataLoader.generatePassword(idAktivnosti,tjedanNastve,this);

        txtPassword = rootView.findViewById(R.id.txtLozinka);
        txtCountDown = rootView.findViewById(R.id.txtCountDown);


        return rootView;
    }

    public void updateTimer(){
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        String timeLeftText;
        timeLeftText = "";
        if(seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        txtCountDown.setText(timeLeftText);
    }
    @Override
    public void onWsDataLoaded(Object message, String status, Object data) {
        if(status.equals("OK")){
            generatedPassword = String.valueOf(data);
            txtPassword.setText(generatedPassword);
            countDownTimer = new CountDownTimer(timeLeftInMilliseconds,1000) {
                @Override
                public void onTick(long l) {
                    timeLeftInMilliseconds = l;
                    updateTimer();
                }

                @Override
                public void onFinish() {
                    txtPassword.setText("");
                    Toast.makeText(rootView.getContext(),"Vrijeme je isteklo! Lozinka više nije važeća.",Toast.LENGTH_LONG).show();
                }
            }.start();
        }else{

        }
    }
    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public void setData(int idAktivnosti, int idUloge, int tjedanNastave) {
        this.idAktivnosti=idAktivnosti;
        this.idProfesora=String.valueOf(idUloge);
        this.tjedanNastve=tjedanNastave;
    }

    @Override
    public boolean getData() {
        return false;
    }
}
