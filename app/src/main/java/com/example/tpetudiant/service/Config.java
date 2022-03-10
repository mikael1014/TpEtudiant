package com.example.tpetudiant.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Config {
    public static final String BASE_URL = "http://10.0.2.2:8080/api/";
//    public static final String URL_ETUDIANT = BASE_URL + "etudiant";
    public static Retrofit retrofit = null;

    public static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
