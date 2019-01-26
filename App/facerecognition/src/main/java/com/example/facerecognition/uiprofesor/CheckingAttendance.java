package com.example.facerecognition.uiprofesor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.facerecognition.R;
import com.foi.air.core.NavigationItem;
import com.foi.air.core.entities.Student;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class CheckingAttendance extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView slika;
    Button btnSlikaj;
    Button btnProvijeri;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_checking_attendance, container, false);

        slika = rootView.findViewById(R.id.cameraView);
        btnSlikaj = rootView.findViewById(R.id.btnPoslikaj);
        btnProvijeri = rootView.findViewById(R.id.btnProvijeri);

        btnSlikaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        return rootView;
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            slika.setImageBitmap(imageBitmap);
        }
    }
}
