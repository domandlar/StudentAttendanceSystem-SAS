package com.foi.air.studentattendancesystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton mBtnStudent;
    ImageButton mBtnProfesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnStudent = findViewById(R.id.btnStudent);
        mBtnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginStudent.class);
                startActivity(intent);
            }
        });

        mBtnProfesor = findViewById(R.id.btnProfesor);
        mBtnProfesor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), LoginProfesor.class);
                startActivity(intent);
            }
        });
    }

}
