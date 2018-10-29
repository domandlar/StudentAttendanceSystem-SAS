package com.foi.air.webservice;

import com.foi.air.webservice.responses.SasWebServiceResponse;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class SasWebServiceCaller {
    SasWebServiceHandler fdWebServiceHandler;
    Retrofit retrofit;
    Call<SasWebServiceResponse> call;

    private final String baseUrl = "https://studentattendancesystem-sas.000webhostapp.com/webservice/"; //nije napravljen jos web servis
    public SasWebServiceCaller(SasWebServiceHandler fdWebServiceHandler){
        this.fdWebServiceHandler = fdWebServiceHandler;
        OkHttpClient okHttpClient  = new OkHttpClient();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
