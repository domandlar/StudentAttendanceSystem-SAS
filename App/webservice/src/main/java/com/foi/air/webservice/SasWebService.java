package com.foi.air.webservice;

import com.foi.air.webservice.responses.SasWebServiceResponse;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface SasWebService {
    @GET("aktivnost/dohvati/{uloga}/{idUloge}/{tipAktivnosti}/")
    Call<SasWebServiceResponse> getAktivnostForProfesor (@Path("uloga") String uloga, @Path("idUloge") int idUloge, @Path("tipAktivnosti") String tipAktivnosti);

    @GET("aktivnost/dohvati/{uloga}/{idUloge}/{tipAktivnosti}/")
    Call<SasWebServiceResponse> getAktivnostForStudent (@Path("uloga") String uloga, @Path("idUloge") int idUloge, @Path("tipAktivnosti") String tipAktivnosti);

    @GET("prijava/student/{email}/{lozinka}/")
    Call<SasWebServiceResponse> prijavaStudent(@Path("email") String email, @Path("lozinka") String loznika);

    @GET("prijava/profesor/{email}/{lozinka}/")
    Call<SasWebServiceResponse> prijavaProfesor(@Path("email") String email, @Path("lozinka") String loznika);

    @GET("kolegij/dohvati/{uloga}/{idUloge}/")
    Call<SasWebServiceResponse> getKolegijForProfesor (@Path("uloga") String uloga, @Path("idUloge") int idUloge);

    @GET("kolegij/dohvati/{uloga}/{idUloge}/")
    Call<SasWebServiceResponse> getKolegijForStudent (@Path("uloga") String uloga, @Path("idUloge") int idUloge);
    @GET("dvorane/dohvati/{tipDvorane}/")
    Call<SasWebServiceResponse> getDvorane (@Path("tipDvorane") String tipDvorane);

    @FormUrlEncoded
    @POST("aktivnost/nova/profesor/")
    Call<SasWebServiceResponse> addAktivnost (@Field("profesor") int idProfesora, @Field ("dozvoljenoIzostanaka") int maxIzostanaka, @Field ("pocetak") String pocetak,
                                            @Field ("kraj") String kraj,@Field ("danIzvodenja") String danIzvodenja, @Field ("dvorana") int idDvorane,
                                            @Field ("kolegij") int idKolegija, @Field ("tipAktivnosti") String tipAktivnosti );

    @GET("aktivnost/dohvatiPoDanu/{uloga}/{idUloge}/{danIzvodenja}/")
    Call<SasWebServiceResponse> getAktivnostForProfesorForDay (@Path("uloga") String uloga, @Path("idUloge") int idUloge, @Path("danIzvodenja") String danIzvodenja);

    @GET("aktivnost/dohvatiPoDanu/{uloga}/{idUloge}/{danIzvodenja}/")
    Call<SasWebServiceResponse> getAktivnostForStudentForDay (@Path("uloga") String uloga, @Path("idUloge") int idUloge, @Path("danIzvodenja") String danIzvodenja);

    @GET("labosi/dohvati/{kolegij}/{student}/")
    Call<SasWebServiceResponse> getLabosForKolegij (@Path("kolegij") int kolegij, @Path("student") int student);

    @GET("labosi/upisi/{student}/{aktivnost}/")
    Call<SasWebServiceResponse> upisLabosa (@Path("student") int student, @Path("aktivnost") int aktivnost);

}
