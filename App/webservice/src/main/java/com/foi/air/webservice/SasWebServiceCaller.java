package com.foi.air.webservice;

import android.util.Log;

import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.Kolegij;
import com.foi.air.core.entities.Profesor;
import com.foi.air.core.entities.Seminar;
import com.foi.air.core.entities.Student;
import com.foi.air.webservice.responses.SasWebServiceResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;

import java.lang.reflect.Type;
import java.util.Arrays;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class SasWebServiceCaller {
    SasWebServiceHandler webServiceHandler;
    Retrofit retrofit;
    Call<SasWebServiceResponse> call;

    private final String baseUrl = "https://studentattendancesystem-sas.000webhostapp.com/webservice/";

    public SasWebServiceCaller(SasWebServiceHandler webServiceHandler) {
        this.webServiceHandler = webServiceHandler;
        OkHttpClient okHttpClient  = new OkHttpClient();
        okHttpClient.setProtocols(Arrays.asList(Protocol.HTTP_1_1));
        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public void CallWsForStudenta(Student data) {
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.prijavaStudent(data.getEmail(),data.getLozinka());
        HandleResponseFromCall("prijava");
    }
    public void CallWsForProfesor(Profesor data) {
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.prijavaProfesor(data.getEmail(),data.getLozinka());
        HandleResponseFromCall("prijava");
    }
    public void CallWsForAktivnostiProfesora(Profesor profesor, Aktivnost aktivnost) {
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.getAktivnostForProfesor("profesor", profesor.getIdProfesora(), aktivnost.getTipAktivnosti());
        HandleResponseFromCall("dohvacanje_aktivnosti");
    }
    public void CallWsForAktivnostiStudenta(Student student, Aktivnost aktivnost) {
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.getAktivnostForStudent("student", student.getIdStudenta(), aktivnost.getTipAktivnosti());
        HandleResponseFromCall("dohvacanje_aktivnosti");
    }
    public void CallWsForKolegijiProfesora(Profesor profesor) {
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.getKolegijForProfesor("profesor", profesor.getIdProfesora());
        HandleResponseFromCall("dohvacanje_kolegija_profesora");
    }
    public void CallWsForDvorane(String tipDvorane) {
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.getDvorane(tipDvorane);
        HandleResponseFromCall("dohvacanje_dvorana");
    }
    public void CallWsForAddSeminar(int idProfesora, int idKolegija, int maxIzostanaka, String pocetak, String kraj, String danIzvodenja, int idDvorane, String tipAktivnosti) {
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.addAktivnost(idProfesora, maxIzostanaka,pocetak,kraj,danIzvodenja,idDvorane,idKolegija,tipAktivnosti);
        HandleResponseFromCall("dodavanje_aktivnosti");
    }
    public void CallWsForAktivnostiProfesoraForDay(String uloga, int idProfesora, String day) {
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.getAktivnostForProfesorForDay("profesor", idProfesora, day);
        HandleResponseFromCall("dohvacanje_aktivnosti_za_dan");
    }
    public void CallWsForAktivnostiStudentaForDay(String uloga, int idStudenta, String day) {
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.getAktivnostForStudentForDay("student", idStudenta, day);
        HandleResponseFromCall("dohvacanje_aktivnosti_za_dan");
    }

    public void CallWsForAddCourse(int idProfesora, String nazivKolegija, int semestarIzvodjenja, String nazivStudija) {
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.addCourse(idProfesora, nazivKolegija, semestarIzvodjenja, nazivStudija);
        HandleResponseFromCall("dodavanje_kolegija");
    }
    public void CallWsForSviKolegiji(int id){
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.getAllCourses("profesor", id);
        HandleResponseFromCall("dohvacanje_svih_kolegija");
    }
    public void CallWsForAddCourseToProfesora(int profesor, int idKolegija){
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.addCourseToProfessor( profesor, idKolegija);
        HandleResponseFromCall("upisivanje_kolegija_profesoru");
    }
    public void CallWsForAzurirajKolegij(int idProfesora, int idKolegija, String nazivKolegija, int semestarIzvodjenja, String nazivStudija) {
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.azurirajKolegij(idProfesora, idKolegija, nazivKolegija, semestarIzvodjenja, nazivStudija);
        HandleResponseFromCall("azuriranje_kolegija");
    }
    public void CallWsForKolegijiStudenta(Student student) {
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.getKolegijForStudent("student", student.getIdStudenta());
        HandleResponseFromCall("dohvacanje_kolegija_studenta");
    }
    public void CallWsForAddCourseToStudent(int student, int idKolegija){
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.addCourseToStudent( student, idKolegija);
        HandleResponseFromCall("upisivanje_kolegija_studentu");
    }
    public void CallWsForTipAktivnostiKolegij(int idKolegija){
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.getTipAktivnostiForKolegij(idKolegija);
        HandleResponseFromCall("dohvacanje_aktivnosti");
    }
    public void CallWsForStudentiKolegij(int idKolegija){
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.getStudentForKolegij(idKolegija);
        HandleResponseFromCall("dohvacanje_aktivnosti");
    }


    public void HandleResponseFromCall(final String method){
        if(call != null){
            call.enqueue(new Callback<SasWebServiceResponse>() {
                @Override
                public void onResponse(Response<SasWebServiceResponse> response, Retrofit retrofit) {
                    try{
                        if(response.isSuccess()){
                            if(webServiceHandler != null)
                                if(method=="prijava"){
                                    webServiceHandler.onDataArrived(response.body().getMessage(), response.body().getStatus(), response.body().getData());
                                    //Log.d("jebate patak: ", response.body().getStatus());
                                    //Log.d("jebate patak2: ", response.body().getMessage());

                                }else if(method=="dohvacanje_aktivnosti"){
                                    webServiceHandler.onDataArrived(response.body().getMessage(), response.body().getStatus(), response.body().getData());
                                    //Log.d("jebate patak: ", response.body().getStatus());
                                    //Log.d("jebate patak1: ", response.body().getMessage());
                                    //Log.d("jebate patak2: ", response.body().getData());
                                }else if(method=="dohvacanje_kolegija_profesora") {
                                    webServiceHandler.onDataArrived(response.body().getMessage(), response.body().getStatus(), response.body().getData());
                                    //Log.d("jebate patak: ", response.body().getStatus());
                                    //Log.d("jebate patak2: ", response.body().getData());
                                }else if(method=="dohvacanje_dvorana") {
                                    webServiceHandler.onDataArrived(response.body().getMessage(), response.body().getStatus(), response.body().getData());
                                    //Log.d("jebate patak: ", response.body().getStatus());
                                    //Log.d("jebate patak2: ", response.body().getData());
                                }else if(method=="dodavanje_aktivnosti") {
                                    webServiceHandler.onDataArrived(response.body().getMessage(), response.body().getStatus(), response.body().getData());
                                    //Log.d("jebate patak: ", response.body().getStatus());
                                    //Log.d("jebate patak2: ", response.body().getData());
                                }
                                else if(method=="dohvacanje_aktivnosti_za_dan") {
                                    webServiceHandler.onDataArrived(response.body().getMessage(), response.body().getStatus(), response.body().getData());
                                    //Log.d("jebate patak: ", response.body().getStatus());
                                    //Log.d("jebate patak2: ", response.body().getData());
                                }else if(method =="dodavanje_kolegija"){
                                    webServiceHandler.onDataArrived(response.body().getMessage(), response.body().getStatus(), response.body().getData());
                                }else if(method =="dohvacanje_svih_kolegija"){
                                    webServiceHandler.onDataArrived(response.body().getMessage(), response.body().getStatus(), response.body().getData());
                                }else if(method =="upisivanje_kolegija_profesoru"){
                                    webServiceHandler.onDataArrived(response.body().getMessage(), response.body().getStatus(), response.body().getData());
                                }else if(method =="azuriranje_kolegija"){
                                    webServiceHandler.onDataArrived(response.body().getMessage(), response.body().getStatus(), response.body().getData());
                                }else if(method =="dohvacanje_kolegija_studenta"){
                                    webServiceHandler.onDataArrived(response.body().getMessage(), response.body().getStatus(), response.body().getData());
                                }else if(method =="upisivanje_kolegija_studentu"){
                                    webServiceHandler.onDataArrived(response.body().getMessage(), response.body().getStatus(), response.body().getData());
                                }
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
}
