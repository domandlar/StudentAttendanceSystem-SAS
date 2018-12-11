package com.foi.air.studentattendancesystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.foi.air.core.entities.Student;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoadedListener;
import com.foi.air.studentattendancesystem.loaders.SasWsDataLoader;
import com.foi.air.studentattendancesystem.uistudent.SeminarList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginStudent extends AppCompatActivity implements SasWsDataLoadedListener {

    private EditText editEmail;
    private EditText editPassword;
    private Button buttonLogin;
    private TextView editText;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Pattern email_check = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private boolean prijavljen = false;

    private String uloga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editText = (TextView) findViewById(R.id.text);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        checkSharedPreferences();


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                editor.putString("Email", email);
                editor.commit();
                String password = editPassword.getText().toString();
                editor.putString("Password", password);
                editor.commit();

                doLogin();


            }
        });

    }

    public void doLogin(){
        if(!editEmail.getText().toString().isEmpty() && !editPassword.getText().toString().isEmpty()){
            if(validateMail(editEmail.getText().toString())){
                Student student = new Student(editEmail.getText().toString(),editPassword.getText().toString());
                SasWsDataLoader sasWsDataLoader = new SasWsDataLoader();
                sasWsDataLoader.prijavaStudent(student,this);
            }
            else Toast.makeText(this,"nije dobar mail", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this, "popunite sva polja", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onWsDataLoaded(Object message, String status, Object data) {
        if(status.equals("OK")) {
            String dataString = String.valueOf(data);
            try {
                JSONObject jo = new JSONObject(dataString);
                String id = jo.getString("id_studenta");
                editor.putString("idStudenta", id);
                editor.commit();
                startNextActivity();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(LoginStudent.this).create();
            alertDialog.setTitle("Rezultat prijave");
            alertDialog.setMessage(message.toString());
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    private void startNextActivity(){
        Intent intent = new Intent(LoginStudent.this, SeminarList.class);
        startActivity(intent);

    }

    private void checkSharedPreferences() {
        String email = sharedPreferences.getString(getString(R.string.email),"");
        String password = sharedPreferences.getString(getString(R.string.password), "");

        editEmail.setText(email);
        editPassword.setText(password);

    }

    private boolean validateMail(String emailStr) {
        Matcher matcher = email_check.matcher(emailStr);
        return matcher.find();
    }
}
