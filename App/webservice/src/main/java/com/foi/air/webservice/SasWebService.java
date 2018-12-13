package com.foi.air.webservice;

import com.foi.air.webservice.responses.SasWebServiceResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface SasWebService {
    @GET("aktivnost/dohvati/{uloga}/{idUloge}/{tipAktivnosti}/")
    Call<SasWebServiceResponse> getAktivnostForProfesor (@Path("uloga") String uloga, @Path("idUloge") int idUloge, @Path("tipAktivnosti") String tipAktivnosti);

    @GET("prijava/student/{email}/{lozinka}/")
    Call<SasWebServiceResponse> prijavaStudent(@Path("email") String email, @Path("lozinka") String loznika);

    @GET("prijava/profesor/{email}/{lozinka}/")
    Call<SasWebServiceResponse> prijavaProfesor(@Path("email") String email, @Path("lozinka") String loznika);

    @GET("kolegij/dohvati/{uloga}/{idUloge}/")
    Call<SasWebServiceResponse> getKolegijForProfesor (@Path("uloga") String uloga, @Path("idUloge") int idUloge);

    @GET("dvorane/dohvati/{tipDvorane}/")
    Call<SasWebServiceResponse> getDvorane (@Path("tipDvorane") String tipDvorane);


}
