package com.example.facerecognition.uiprofesor;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.foi.air.core.entities.Dolazak;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.FaceRectangle;

import static android.app.Activity.RESULT_OK;

import com.microsoft.projectoxford.face.contract.*;
import com.microsoft.projectoxford.face.contract.Person;
import com.microsoft.projectoxford.face.rest.ClientException;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

import android.app.*;
import android.os.*;
import android.graphics.*;
import android.widget.*;


public class CheckingAttendance extends Fragment implements NavigationItem {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageView;
    Button btnSlikaj;
    Button btnProvijeri;
    View rootView;

    Bitmap mBitmap;
    Face[] facesDetected;

    String idProfesora;
    int idAktivnosti=0;
    int tjedanNastve=0;
    ArrayList<Dolazak> prisutniStudenti;

    private final String apiEndpoint = "https://westeurope.api.cognitive.microsoft.com/face/v1.0";

    private final String subscriptionKey = "ee85c084b99f4ebc9c8c5c9101105f4d";
    private final String personGroupId = "students";

    private final FaceServiceClient faceServiceClient =
            new FaceServiceRestClient(apiEndpoint, subscriptionKey);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_checking_attendance, container, false);

        imageView = rootView.findViewById(R.id.cameraView);
        btnSlikaj = rootView.findViewById(R.id.btnPoslikaj);
        btnProvijeri = rootView.findViewById(R.id.btnProvijeri);
        prisutniStudenti = new ArrayList<Dolazak>();

        btnSlikaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });
        btnProvijeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

                new detectTask().execute(inputStream);
            }
        });

        return rootView;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mBitmap = imageBitmap;
            imageView.setImageBitmap(imageBitmap);

        }
    }

    @Override
    public Fragment getFragment() {
        return null;
    }

    @Override
    public void setData(int idAktivnosti, int idUloge, int tjedanNastave) {
        this.idAktivnosti=idAktivnosti;
        this.idProfesora=String.valueOf(idUloge);
        this.tjedanNastve=tjedanNastave;
    }

    @Override
    public ArrayList<Dolazak> getData() {
        return prisutniStudenti;
    }

    class detectTask extends  AsyncTask<InputStream,String,Face[]> {
        private ProgressDialog mDialog = new ProgressDialog(getContext());


        @Override
        protected Face[] doInBackground(InputStream... params) {
            try{
                publishProgress("Traženje...");
                Face[] results = faceServiceClient.detect(params[0],true,false,null);
                if(results == null)
                {
                    publishProgress("Traženje je završeno. Nisu pronađena lica na slici.");
                    return null;
                }
                else{
                    publishProgress(String.format("Traženje je završeno. %d studenta(s) pronađeno",results.length));
                    return results;
                }
            }
            catch (Exception ex)
            {
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }

        @Override
        protected void onPostExecute(Face[] faces) {
            mDialog.dismiss();

            facesDetected = faces;

            //Prepoznavanje
            final UUID[] faceIds = new UUID[facesDetected.length];
            for(int i=0;i<facesDetected.length;i++){
                faceIds[i] = facesDetected[i].faceId;
            }

            new IdentificationTask(personGroupId).execute(faceIds);

        }

        @Override
        protected void onProgressUpdate(String... values) {
            mDialog.setMessage(values[0]);
        }

    }

    private class IdentificationTask extends AsyncTask<UUID,String,IdentifyResult[]> {
        String personGroupId;

        private ProgressDialog mDialog = new ProgressDialog(getContext());

        public IdentificationTask(String personGroupId) {
            this.personGroupId = personGroupId;
        }

        @Override
        protected IdentifyResult[] doInBackground(UUID... params) {

            try{
                publishProgress("Dohvaćanje statusa grupe...");
                TrainingStatus trainingStatus  = faceServiceClient.getPersonGroupTrainingStatus(this.personGroupId);
                if(trainingStatus.status != TrainingStatus.Status.Succeeded)
                {
                    publishProgress("Trening status grupe je "+trainingStatus.status);
                    return null;
                }
                publishProgress("Identifikacija...");

                IdentifyResult[] results = faceServiceClient.identity(personGroupId, // person group id
                        params // face ids
                        ,1); // max number of candidates returned

                return results;

            } catch (Exception e)
            {
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }

        @Override
        protected void onPostExecute(IdentifyResult[] identifyResults) {
            mDialog.dismiss();

            for(IdentifyResult identifyResult:identifyResults)
            {
                if(identifyResult.candidates.size() > 0)
                    new PersonDetectionTask(personGroupId).execute(identifyResult.candidates.get(0).personId);
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mDialog.setMessage(values[0]);
        }
    }

    private class PersonDetectionTask extends AsyncTask<UUID,String,Person> {
        private ProgressDialog mDialog = new ProgressDialog(getContext());
        private String personGroupId;

        public PersonDetectionTask(String personGroupId) {
            this.personGroupId = personGroupId;
        }

        @Override
        protected Person doInBackground(UUID... params) {
            try{
                publishProgress("Dohvaćanje osobe...");

                Person person = faceServiceClient.getPerson(personGroupId,params[0]);
                return person;
            } catch (Exception e)
            {
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }

        @Override
        protected void onPostExecute(Person person) {
            mDialog.dismiss();
            Dolazak prisutanStudent = new Dolazak();
            prisutanStudent.setIdStudenta(Integer.parseInt(person.name));
            prisutniStudenti.add(prisutanStudent);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mDialog.setMessage(values[0]);
        }
    }

}
