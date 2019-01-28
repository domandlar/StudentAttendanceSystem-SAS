package com.foi.air.passwordrecord.profesor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.Toast;

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
    BetterSpinner spinnerTjedanNastave;
    Button btnGenerirajLozinku;
    String idProfesora;
    ArrayAdapter<AktivnostiProfesora> spinnerAdapterAktivnosti;
    ArrayAdapter<Integer> spinnerAdapterTjedni;
    ArrayList<AktivnostiProfesora> aktivnostiList;
    int idAktivnosti=0;
    int tjedanNastve=0;
    View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_generate_password,
                container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        idProfesora = prefs.getString("idProfesora", "");
        Profesor profesor = new Profesor(Integer.parseInt(idProfesora));

        SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
        sasWsDataLoader.allAktivnostForProfesor(profesor,this);

        spinnerTipAktivnosti = (BetterSpinner) rootView.findViewById(R.id.spinnerTpAktivnosti);
        spinnerAdapterAktivnosti = new ArrayAdapter<AktivnostiProfesora>(this.getActivity(), R.layout.multiline_spinner_dropdown_item, aktivnostiList);
        spinnerTipAktivnosti.setAdapter(spinnerAdapterAktivnosti);
        spinnerTipAktivnosti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                AktivnostiProfesora aktivnostProfesora = (AktivnostiProfesora) parent.getItemAtPosition(position);
                idAktivnosti = aktivnostProfesora.getIdAktivnosti();
            }
        });

        Integer[] weeks = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        spinnerTjedanNastave = (BetterSpinner) rootView.findViewById(R.id.spinnerTjedanNastave);
        spinnerAdapterTjedni = new ArrayAdapter<Integer>(this.getActivity(), R.layout.multiline_spinner_dropdown_item, weeks);
        spinnerTjedanNastave.setAdapter(spinnerAdapterTjedni);
        spinnerTjedanNastave.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                tjedanNastve = (Integer) parent.getItemAtPosition(position);
            }
        });

        btnGenerirajLozinku = rootView.findViewById(R.id.buttonGenerirajLozinku);
        btnGenerirajLozinku.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(idAktivnosti !=0 && tjedanNastve !=0){
                    /*
                    mEditPocetakSata = findViewById(R.id.editTextPocetak);
                    pocetakSata = mEditPocetakSata.getText().toString();
                    mEditKrajSata = findViewById(R.id.editTextKraj);
                    krajStata = mEditKrajSata.getText().toString();
                    mEditDozvoljenoIzostanaka = findViewById(R.id.editTextDozvoljenoIzostanaka);
                    dozvoljenoIzostanaka = Integer.parseInt(mEditDozvoljenoIzostanaka.getText().toString());
                    SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
                    sasWsDataLoader.dodajAktivnost(Integer.parseInt(idProfesora),idKolegija,dozvoljenoIzostanaka,pocetakSata,krajStata,danOdrzavanja,idDvorane,"Seminar");
                    Toast.makeText(getApplicationContext(),"Seminar je dodan!", Toast.LENGTH_SHORT).show();
                    */
                    ShowPassword showPasswordFragment = new ShowPassword();
                    Bundle bundle = new Bundle();
                    bundle.putInt("idAktivnosti", idAktivnosti);
                    bundle.putInt("tjedanNastave", tjedanNastve);
                    showPasswordFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, showPasswordFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    alertDialog.setTitle("Pogreška");
                    alertDialog.setMessage("Niste odabrali sva polja!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });



        return rootView;
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

            spinnerAdapterAktivnosti = new ArrayAdapter<AktivnostiProfesora>(this.getActivity(), R.layout.multiline_spinner_dropdown_item, aktivnostiList);
            spinnerTipAktivnosti.setAdapter(spinnerAdapterAktivnosti);
            spinnerAdapterAktivnosti.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
