package com.foi.air.passwordrecord.student;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.foi.air.core.NavigationItem;
import com.foi.air.core.SasWsDataLoadedListener;
import com.foi.air.core.entities.AktivnostiStudenta;
import com.foi.air.core.entities.Dolazak;
import com.foi.air.core.entities.Student;
import com.foi.air.passwordrecord.R;
import com.foi.air.passwordrecord.loaders.SasWsDataLoader;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubmitAttendance extends Fragment implements SasWsDataLoadedListener, NavigationItem {
    EditText lozinkaPrisustva;

    Button btnPotvrdiPrisustvo;

    Student student;

    ArrayList<Dolazak> dolazakList;

    String idStudenta;

    int idAktivnosti=0;
    int tjedanNastve=0;

    View rootView;

    OnCallbackReceived mCallback;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_submit_attendance,
                container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        idStudenta = prefs.getString("idStudenta", "");
        student = new Student(Integer.parseInt(idStudenta));

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

    /**
     *
     * @param message poruka vraćena sa web servisa
     * @param status status vraćen sa web servisa
     * @param data podaci vraćeni sa web servisa
     */
    @Override
    public void onWsDataLoaded(Object message, String status, Object data) {
        if(status.equals("OK")){
            Dolazak dolazak= new Dolazak();
            dolazak.setIdStudenta(Integer.parseInt(idStudenta));
            boolean dataString = Boolean.valueOf(data.toString());
            dolazak.setPrisustvo(dataString);
            dolazak.setTjedanNastave(tjedanNastve);
            dolazak.setIdAktivnosti(idAktivnosti);
            dolazakList = new ArrayList<Dolazak>();
            dolazakList.add(dolazak);

            mCallback.Update();

        }else if(status.equals("NOT OK") && message.equals("Nema generirane lozinke za prisustvo za odabranu aktivnost u odabranom tjednu nastave.")){
            Toast.makeText(getContext(),"Nema generirane lozinke za prisustvo za odabranu aktivnost u odabranom tjednu nastave!", Toast.LENGTH_SHORT).show();
            mCallback.Update();
        }else if(status.equals("NOT OK") && message.equals("Isteklo vrijeme.")){
            Toast.makeText(getContext(),"Lozinka više nije važeća!", Toast.LENGTH_SHORT).show();
            mCallback.Update();
        }else if(status.equals("NOT OK") && message.equals("Unesena lozinka nije točna.")){
            Toast.makeText(getContext(),"Unesli ste krivu lozinku!", Toast.LENGTH_SHORT).show();
            mCallback.Update();
        }else if(status.equals("OK") && message.equals("Zabilježeno prisustvo.")){
            Toast.makeText(getContext(),"Prisustvo za odabranu aktivnost je zabilježeno!", Toast.LENGTH_SHORT).show();
            mCallback.Update();
        }
    }


    /**
     *
     * @param idAktivnosti generiranje lozinke za taj id aktivnosti
     * @param idUloge generiranje lozinke za taj id uloge
     * @param tjedanNastave generiranje lozinke za taj tjedan
     */
    @Override
    public void setData(int idAktivnosti, int idUloge, int tjedanNastave) {
        this.idAktivnosti=idAktivnosti;
        this.idStudenta=String.valueOf(idUloge);
        this.tjedanNastve=tjedanNastave;
    }

    /**
     *
     * @return vraćena lista dolazak
     */
    @Override
    public ArrayList<Dolazak> getData() {
        return dolazakList;
    }
    public interface OnCallbackReceived {
        public void Update();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnCallbackReceived) activity;
        } catch (ClassCastException e) {

        }

    }

}
