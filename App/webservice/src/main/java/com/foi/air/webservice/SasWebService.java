package com.foi.air.webservice;

import com.foi.air.webservice.responses.SasWebServiceResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface SasWebService {
    @GET("{metoda}/{email}/{lozinka}/")
    Call<SasWebServiceResponse> getKorisnik(@Path("metoda") String metoda, @Path("email") String email, @Path("lozinka") String loznika);

    @GET("registracija/{metoda}/{email}/{lozinka}/{oib}/{grad}/{adresa}/{kontakt}/{ime}/{prezime}/")
    Call<SasWebServiceResponse> setFizickaOsoba(
            @Path("metoda") String metoda,
            @Path("email") String email,
            @Path("lozinka") String lozinka,
            @Path("oib") String oib,
            @Path("grad") String grad,
            @Path("adresa") String adresa,
            @Path("kontakt") String kontakt,
            @Path("ime") String ime,
            @Path("prezime") String prezime);
}
