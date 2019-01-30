package com.foi.air.passwordrecord;

import com.foi.air.passwordrecord.responses.SasWebServiceResponse;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface SasWebService {

    @FormUrlEncoded
    @POST("modularno/lozinke/generiraj/")
    Call<SasWebServiceResponse> generirajLozinku(@Field("aktivnost") int idAktivnosti, @Field("tjedanNastave") int tjedanNastave);


    @FormUrlEncoded
    @POST("modularno/lozinke/provijeri/")
    Call<SasWebServiceResponse> provjeraLozinke(@Field("student") int idStudenta, @Field("lozinka") String lozinka, @Field("tjedanNastave") int tjedanNastave);

}
