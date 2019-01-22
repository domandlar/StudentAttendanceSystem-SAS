package com.foi.air.passwordrecord.student;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.AktivnostiProfesora;
import com.foi.air.core.entities.Profesor;
import com.foi.air.core.entities.Student;
import com.foi.air.passwordrecord.R;
import com.foi.air.passwordrecord.loaders.SasWsDataLoadedListener;
import com.foi.air.passwordrecord.loaders.SasWsDataLoader;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubmitAttendance extends Fragment implements SasWsDataLoadedListener {
    BetterSpinner spinnerTipAktivnosti;
    BetterSpinner spinnerTjedanNastave;

    Button btnPotvrdiPrisustvo;

    ArrayAdapter<AktivnostiProfesora> spinnerAdapterAktivnosti;
    ArrayAdapter<Integer> spinnerAdapterTjedni;
    ArrayList<AktivnostiProfesora> aktivnostiList;

    String idStudenta;

    View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_submit_attendance,
                container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        idStudenta = prefs.getString("idStudenta", "");
        Student student = new Student(Integer.parseInt(idStudenta));

        SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
        //sasWsDataLoader.allAktivnostForProfesor(student,this);



        return rootView;
    }

    @Override
    public void onWsDataLoaded(Object message, String status, Object data) {
        /*
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

            spinnerAdapterAktivnosti = new ArrayAdapter<AktivnostiProfesora>(this.getActivity(), R.layout.multiline_spinner_dropdown_item, aktivnostiList);
            spinnerTipAktivnosti.setAdapter(spinnerAdapterAktivnosti);
            spinnerAdapterAktivnosti.notifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();
        }
        */
    }
}
