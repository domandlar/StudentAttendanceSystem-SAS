package com.foi.air.passwordrecord.profesor;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.foi.air.passwordrecord.R;

public class ShowPassword extends Fragment {
    View rootView;
    TextView txtCountDown;
    CountDownTimer countDownTimer;
    long timeLeftInMilliseconds=20000; //20 sec
    boolean timerRunning;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.show_passwod,container,false);

        txtCountDown = rootView.findViewById(R.id.txtCountDown);
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds,1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateTimer();
            }

            @Override
            public void onFinish() {

                //Toast.makeText(rootView.getContext(),"Vrijeme je isteklo!",Toast.LENGTH_SHORT).show();
            }
        }.start();

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
}
