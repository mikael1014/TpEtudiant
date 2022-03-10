package com.example.tpetudiant.service;

import static com.example.tpetudiant.service.Config.BASE_URL;

import com.example.tpetudiant.models.Specialite;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SpecialiteService {
    String URL_SPECIALITE = BASE_URL + "specialite";

    @GET(URL_SPECIALITE)
    Call<List<Specialite>> getListeSpecialite();

    @GET(URL_SPECIALITE + "/{id}")
    Call<Specialite> getSpecialiteById(@Path(value = "id") long idSpecialite);

    @POST(URL_SPECIALITE)
    Call<Specialite> saveSpecialite(@Body Specialite specialite);

    @PUT(URL_SPECIALITE + "/{id}")
    Call<Specialite> updateSpecialite(@Path(value = "id") Long idSpecialite, @Body Specialite specialite);

    @DELETE(URL_SPECIALITE + "/{id}")
    Call<Void> deleteSpecialite(@Path("id") Long id);

}
