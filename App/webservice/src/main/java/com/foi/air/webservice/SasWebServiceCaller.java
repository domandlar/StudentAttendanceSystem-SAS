package com.foi.air.webservice;

import com.foi.air.core.entities.Kolegij;
import com.foi.air.core.entities.Profesor;
import com.foi.air.webservice.responses.SasWebServiceResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.lang.reflect.Type;
import java.util.Arrays;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class SasWebServiceCaller {
    SasWebServiceHandler mSasWebServiceHandler;
    // retrofit object
    Retrofit retrofit;
    Call<SasWebServiceResponse> call;

    private final String baseUrl = "https://studentattendancesystem-sas.000webhostapp.com/webservice/"; //nije napravljen jos web servis
    public SasWebServiceCaller(SasWebServiceHandler fdWebServiceHandler){
        this.mSasWebServiceHandler = fdWebServiceHandler;
        OkHttpClient okHttpClient  = new OkHttpClient();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
    public void getAll(String method, final Type entityType){
        Call<SasWebServiceResponse> call = null;

        SasWebService serviceCaller = retrofit.create(SasWebService.class);
        call = serviceCaller.getKolegijZaProfesora(method);

        if(call != null){
            call.enqueue(new Callback<SasWebServiceResponse>() {
                @Override
                public void onResponse(Response<SasWebServiceResponse> response, Retrofit retrofit) {
                    try {
                        if(response.isSuccess()){
                            handleCourses(response);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
        }
        private void handleCourses(Response<SasWebServiceResponse> response) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd") // response JSON format
                    .create();
            Kolegij[] discountItems = gson.fromJson(response.body().getItems(), Kolegij[].class);
            if(mSasWebServiceHandler != null){
                mSasWebServiceHandler.onDataArrived(Arrays.asList(discountItems), true, response.body().getTimeStamp());
            }
        }


    }
}
