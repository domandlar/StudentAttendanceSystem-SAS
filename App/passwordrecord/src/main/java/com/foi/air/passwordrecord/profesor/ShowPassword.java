package com.foi.air.passwordrecord.profesor;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.foi.air.core.entities.Kolegij;
import com.foi.air.passwordrecord.R;
import com.foi.air.passwordrecord.loaders.SasWsDataLoadedListener;
import com.foi.air.passwordrecord.loaders.SasWsDataLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowPassword extends Fragment implements SasWsDataLoadedListener {
    View rootView;
    TextView txtCountDown;
    TextView txtPassword;
    CountDownTimer countDownTimer;
    long timeLeftInMilliseconds=20000; //20 sec
    boolean timerRunning;

    int idAktivnosti;
    int tjedanNastave;
    String generatedPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.show_passwod,container,false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            idAktivnosti = bundle.getInt("idAktivnosti");
            tjedanNastave = bundle.getInt("tjedanNastave");
        }


        SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
        sasWsDataLoader.generatePassword(idAktivnosti,tjedanNastave,this);

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
        if(status.equals("OK") && message.equals("Lozinka je generirana.")){
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
            Toast.makeText(getContext(),"Došlo je do pogreške!", Toast.LENGTH_SHORT).show();
        }
    }
}
