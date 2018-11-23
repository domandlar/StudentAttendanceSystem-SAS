package com.foi.air.webservice;

import com.foi.air.webservice.responses.SasWebServiceResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface SasWebService {
    @GET("{uloga}/{idUloge}/{tipAktivnosti}/")
    Call<SasWebServiceResponse> getKolegijZaProfesora (@Path("uloga") String uloga, @Path("idUloge") int idUloge, @Path("lozinka") int tipAktivnosti);


}
