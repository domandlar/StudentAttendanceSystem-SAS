package com.foi.air.passwordrecord;

import android.util.Log;

import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.Kolegij;
import com.foi.air.core.entities.Profesor;
import com.foi.air.core.entities.Student;
import com.foi.air.passwordrecord.responses.SasWebServiceResponse;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;

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

    public void CallWsForGeneratePassword(int idAktivnosti, int tjedanNastave) {
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.generirajLozinku(idAktivnosti,tjedanNastave);
        HandleResponseFromCall("generiranje_lozinke");
    }
    public void CallWsForProvjeriLozinku(Student student, String lozinka, int tjedanNastave) {
        SasWebService webService = retrofit.create(SasWebService.class);
        call = webService.provjeraLozinke(student.getIdStudenta(), lozinka, tjedanNastave);
        HandleResponseFromCall("zabiljezi_prisustvo_lozinkom");
    }

    public void HandleResponseFromCall(final String method){
        if(call != null){
            call.enqueue(new Callback<SasWebServiceResponse>() {
                @Override
                public void onResponse(Response<SasWebServiceResponse> response, Retrofit retrofit) {
                    try{
                        if(response.isSuccess()){
                            if(webServiceHandler != null)
                                if(method=="generiranje_lozinke") {
                                    webServiceHandler.onDataArrived(response.body().getMessage(), response.body().getStatus(), response.body().getData());
                                    Log.d("jebate patak: ", response.body().getStatus());
                                    Log.d("jebate patak2: ", response.body().getData());
                                }
                                else if(method=="zabiljezi_prisustvo_lozinkom") {
                                    webServiceHandler.onDataArrived(response.body().getMessage(), response.body().getStatus(), response.body().getData());
                                    Log.d("jebate patak1: ", response.body().getStatus());
                                    Log.d("jebate patak2: ", response.body().getMessage());
                                    Log.d("jebate patak3: ", response.body().getData());
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
