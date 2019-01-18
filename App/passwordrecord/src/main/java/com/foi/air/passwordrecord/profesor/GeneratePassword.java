package com.foi.air.passwordrecord.profesor;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

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

public class GeneratePassword extends Fragment implements SasWsDataLoadedListener {
    BetterSpinner spinnerTipAktivnosti;
    String idProfesora;
    ArrayAdapter<AktivnostiProfesora> spinnerAdapterAktivnosti;
    ArrayList<AktivnostiProfesora> aktivnostiList;
    int idAktivnosti=0;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_generate_password,
                container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        idProfesora = prefs.getString("idProfesora", "");
        Profesor profesor = new Profesor(Integer.parseInt(idProfesora));

        SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
        sasWsDataLoader.allAktivnostForProfesor(profesor,this);

        /*
        String [] values =
                {"Time at Residence","Under 6 months","6-12 months","1-2 years","2-4 years","4-8 years","8-15 years","Over 15 years",};
       BetterSpinner spinner = (BetterSpinner) view.findViewById(R.id.spinnerTpAktivnosti);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        */
        spinnerTipAktivnosti = (BetterSpinner) view.findViewById(R.id.spinnerTpAktivnosti);
        spinnerAdapterAktivnosti = new ArrayAdapter<AktivnostiProfesora>(this.getActivity(), R.layout.multiline_spinner_dropdown_item, aktivnostiList);
        spinnerTipAktivnosti.setAdapter(spinnerAdapterAktivnosti);
        /*
        spinnerTipAktivnosti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                AktivnostiProfesora aktivnostProfesora = (AktivnostiProfesora) parent.getItemAtPosition(position);
                idAktivnosti = aktivnostProfesora.getIdAktivnosti();
            }
        });
        */


        return view;
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

            spinnerAdapterAktivnosti = new ArrayAdapter<AktivnostiProfesora>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, aktivnostiList);
            spinnerTipAktivnosti.setAdapter(spinnerAdapterAktivnosti);
            spinnerAdapterAktivnosti.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
