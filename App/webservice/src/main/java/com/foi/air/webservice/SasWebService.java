package com.foi.air.webservice;

import com.foi.air.webservice.responses.SasWebServiceResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface SasWebService {
    @GET("{uloga}/{idUloge}/{tipAktivnosti}/")
    Call<SasWebServiceResponse> getKolegijZaProfesora (@Path("uloga") String uloga, @Path("idUloge") int idUloge, @Path("lozinka") int tipAktivnosti);

    @GET("prijava/student/{email}/{lozinka}/")
    Call<SasWebServiceResponse> prijavaStudent(@Path("email") String email, @Path("lozinka") String loznika);


}
