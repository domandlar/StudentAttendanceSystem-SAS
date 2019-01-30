package com.example.facerecognition.uiprofesor;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import android.app.*;
import android.os.*;
import android.graphics.*;
import android.widget.*;


public class CheckingAttendance extends Fragment implements NavigationItem {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    File photoFile = null;
    String mCurrentPhotoPath;
    private static final String IMAGE_DIRECTORY_NAME = "faceregognition";

    ImageView imageView;
    Button btnSlikaj;
    Button btnProvijeri;
    View rootView;
    int brPrepoznatihStudenta=0;
    int brDohvacenihStudenta=0;

    Bitmap mBitmap;
    Face[] facesDetected;

    String idProfesora;
    int idAktivnosti=0;
    int tjedanNastve=0;
    ArrayList<Dolazak> prisutniStudenti;

    OnCallbackReceived mCallback;
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
        //mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.test);
        //imageView = (ImageView)rootView.findViewById(R.id.cameraView);
        prisutniStudenti = new ArrayList<Dolazak>();

        btnSlikaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    captureImage();
                }
                else
                {
                    captureImage2();
                }


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
        //if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
        //    Bundle extras = data.getExtras();
        //    Bitmap imageBitmap = (Bitmap) extras.get("data");
        //    mBitmap = imageBitmap;
        //    imageView.setImageBitmap(imageBitmap);

        //}
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            mBitmap = imageBitmap;
            imageView.setImageBitmap(imageBitmap);
        }
        else
        {
            displayMessage(getActivity(),"Request cancelled or something went wrong.");
        }
    }

    private void captureImage2() {

        try {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            photoFile = createImageFile4();
            if(photoFile!=null)
            {
                Uri photoURI  = Uri.fromFile(photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
        catch (Exception e)
        {
            displayMessage(getActivity(),"Camera is not available."+e.toString());
        }
    }

    private void captureImage() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                // Create the File where the photo should go
                try {

                    photoFile = createImageFile();

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getActivity(),
                                "com.sas.faceregognition.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                } catch (Exception ex) {
                    // Error occurred while creating the File
                    displayMessage(getActivity(), ex.getMessage().toString());
                }


            } else {
                displayMessage(getActivity(), "Nullll");
            }
        }

    }
    private File createImageFile4()
    {
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                displayMessage(getActivity(),"Unable to create directory.");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void displayMessage(Context context, String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            }
        }

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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnCallbackReceived) activity;
        } catch (ClassCastException e) {

        }

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
                {
                    brPrepoznatihStudenta++;
                    new PersonDetectionTask(personGroupId).execute(identifyResult.candidates.get(0).personId);
                }

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
            prisutanStudent.setPrisustvo(true);
            prisutanStudent.setIdAktivnosti(idAktivnosti);
            prisutniStudenti.add(prisutanStudent);
            brDohvacenihStudenta++;
            if(brDohvacenihStudenta==brPrepoznatihStudenta)
                mCallback.Update();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mDialog.setMessage(values[0]);
        }
    }

}
