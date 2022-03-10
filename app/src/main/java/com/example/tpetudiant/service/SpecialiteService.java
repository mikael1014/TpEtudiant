package com.example.tpetudiant.service;

import static com.example.tpetudiant.service.Config.BASE_URL;

import com.example.tpetudiant.models.Etudiant;

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
    Call<List<Etudiant>> getListeEtudiant();

    @GET(URL_SPECIALITE + "/{id}")
    Call<Etudiant> getEtudiantById(@Path(value = "id") long idEtudiant);

    @POST(URL_SPECIALITE)
    Call<Etudiant> saveEtudiant(@Body Etudiant specialite);

    @PUT(URL_SPECIALITE + "/{id}")
    Call<Etudiant> updateEtudiant(@Path(value = "id") Long idEtudiant, @Body Etudiant specialite);

    @DELETE(URL_SPECIALITE + "/{id}")
    Call<Void> deleteEtudiant(@Path("id") Long id);

}
