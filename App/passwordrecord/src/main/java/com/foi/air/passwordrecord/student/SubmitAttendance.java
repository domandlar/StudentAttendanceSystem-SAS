package com.foi.air.passwordrecord.student;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.foi.air.core.NavigationItem;
import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.AktivnostiProfesora;
import com.foi.air.core.entities.AktivnostiStudenta;
import com.foi.air.core.entities.Profesor;
import com.foi.air.core.entities.Student;
import com.foi.air.passwordrecord.R;
import com.foi.air.passwordrecord.loaders.SasWsDataLoadedListener;
import com.foi.air.passwordrecord.loaders.SasWsDataLoader;
import com.foi.air.passwordrecord.profesor.ShowPassword;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubmitAttendance extends Fragment implements SasWsDataLoadedListener, NavigationItem {
    BetterSpinner spinnerTipAktivnosti;
    BetterSpinner spinnerTjedanNastave;
    EditText lozinkaPrisustva;

    Button btnPotvrdiPrisustvo;

    Student student;

    ArrayAdapter<AktivnostiStudenta> spinnerAdapterAktivnosti;
    ArrayAdapter<Integer> spinnerAdapterTjedni;
    ArrayList<AktivnostiStudenta> aktivnostiList;

    String idStudenta;

    int idAktivnosti=0;
    int tjedanNastve=0;

    View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_submit_attendance,
                container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        idStudenta = prefs.getString("idStudenta", "");
        student = new Student(Integer.parseInt(idStudenta));

        SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
        sasWsDataLoader.allAktivnostForStudent(student,this);

        spinnerTipAktivnosti = (BetterSpinner) rootView.findViewById(R.id.spinnerTpAktivnosti);
        spinnerAdapterAktivnosti = new ArrayAdapter<AktivnostiStudenta>(this.getActivity(), R.layout.multiline_spinner_dropdown_item, aktivnostiList);
        spinnerTipAktivnosti.setAdapter(spinnerAdapterAktivnosti);
        spinnerTipAktivnosti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                AktivnostiStudenta aktivnostiStudenta = (AktivnostiStudenta) parent.getItemAtPosition(position);
                idAktivnosti = aktivnostiStudenta.getIdAktivnosti();
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

        lozinkaPrisustva = rootView.findViewById(R.id.txtUnosLozinke);
        btnPotvrdiPrisustvo = rootView.findViewById(R.id.buttonPotvrdiDolazak);
        btnPotvrdiPrisustvo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(idAktivnosti !=0 && tjedanNastve !=0 && lozinkaPrisustva.getText().toString() != ""){
                    String lozinka = lozinkaPrisustva.getText().toString();
                    SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
                    sasWsDataLoader.provjeriLozinku(student,lozinka,tjedanNastve, SubmitAttendance.this);

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
        if(status.equals("OK") && message.equals("Pronađene aktivnosti.")){
            aktivnostiList = new ArrayList<AktivnostiStudenta>();
            String dataString = String.valueOf(data);
            try {
                JSONArray array = new JSONArray(dataString);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject row = array.getJSONObject(i);
                    AktivnostiStudenta aktivnostiStudenta= new AktivnostiStudenta(
                            row.getInt("id"),
                            row.getString("pocetak"),
                            row.getString("kraj"),
                            row.getString("dan_izvodenja") ,
                            row.getString("dvorana"),
                            row.getInt("id_tip_aktivnosti"),
                            row.getString("tip_aktivnosti"),
                            row.getString("kolegij"));
                    aktivnostiList.add(aktivnostiStudenta);
                }

                spinnerAdapterAktivnosti = new ArrayAdapter<AktivnostiStudenta>(this.getActivity(), R.layout.multiline_spinner_dropdown_item, aktivnostiList);
                spinnerTipAktivnosti.setAdapter(spinnerAdapterAktivnosti);
                spinnerAdapterAktivnosti.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(status.equals("NOT OK") && message.equals("Nema generirane lozinke za prisustvo za odabranu aktivnost u odabranom tjednu nastave.")){
            Toast.makeText(getContext(),"Nema generirane lozinke za prisustvo za odabranu aktivnost u odabranom tjednu nastave!", Toast.LENGTH_SHORT).show();
        }else if(status.equals("NOT OK") && message.equals("Isteklo vrijeme.")){
            Toast.makeText(getContext(),"Lozinka više nije važeća!", Toast.LENGTH_SHORT).show();
        }else if(status.equals("NOT OK") && message.equals("Unesena lozinka nije točna.")){
            Toast.makeText(getContext(),"Unesli ste krivu lozinku!", Toast.LENGTH_SHORT).show();
        }else if(status.equals("OK") && message.equals("Zabilježeno prisustvo.")){
            Toast.makeText(getContext(),"Prisustvo za odabranu aktivnost je zabilježeno!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public void setData() {

    }
}
