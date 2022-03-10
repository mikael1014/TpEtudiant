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

public interface EtudiantService {
    String URL_ETUDIANT = BASE_URL + "etudiant";

    @GET(URL_ETUDIANT)
    Call<List<Etudiant>> getListeEtudiant();

    @GET(URL_ETUDIANT + "/{id}")
    Call<Etudiant> getEtudiantById(@Path(value = "id") long idEtudiant);

    @POST(URL_ETUDIANT)
    Call<Etudiant> saveEtudiant(@Body Etudiant etudiant);

    @PUT(URL_ETUDIANT + "/{id}")
    Call<Etudiant> updateEtudiant(@Path(value = "id") Long idEtudiant, @Body Etudiant etudiant);

    @DELETE(URL_ETUDIANT + "/{id}")
    Call<Void> deleteEtudiant(@Path("id") Long id);

}
