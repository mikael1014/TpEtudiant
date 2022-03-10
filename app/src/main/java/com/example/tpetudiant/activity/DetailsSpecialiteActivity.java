package com.example.tpetudiant.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tpetudiant.R;
import com.example.tpetudiant.models.Specialite;
import com.example.tpetudiant.service.Config;
import com.example.tpetudiant.service.SpecialiteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsSpecialiteActivity extends AppCompatActivity {

    private SpecialiteService specialiteService;
    private TextView id, titre, description, etudiants, nbreHabitant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_specialite);

//        Specialite specialite = (Specialite) getIntent().getSerializableExtra("selectedSpecialite");
        specialiteService = Config.getApiClient().create(SpecialiteService.class);
        long idSpecialite = getIntent().getLongExtra("idSpecialite", 0);
        id = findViewById(R.id.tvDId);
        titre = findViewById(R.id.tvDNom);
        description = findViewById(R.id.tvDPrenom);
        etudiants = findViewById(R.id.tvDMatricule);
        nbreHabitant = findViewById(R.id.tvDSpecialite);
        initDetails("", "", "", "");

        specialiteService.getSpecialiteById(idSpecialite).enqueue(new Callback<Specialite>() {
            @Override
            public void onResponse(Call<Specialite> call, Response<Specialite> response) {
                if (response.isSuccessful()) {
                    Specialite specialite = response.body();
                    initDetails(String.valueOf(specialite.getId()), specialite.getTitre(), specialite.getDescription(),
                            String.valueOf(specialite.getEtudiants()));
                }
            }

            @Override
            public void onFailure(Call<Specialite> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    private void initDetails(String id, String titre, String description, String etudiants) {
        this.id.setText(id);
        this.titre.setText(titre);
        this.description.setText(description);
        this.etudiants.setText(etudiants);
    }
}