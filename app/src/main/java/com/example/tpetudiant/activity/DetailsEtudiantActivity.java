package com.example.tpetudiant.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tpetudiant.R;
import com.example.tpetudiant.models.Etudiant;
import com.example.tpetudiant.service.Config;
import com.example.tpetudiant.service.EtudiantService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsEtudiantActivity extends AppCompatActivity {

    private EtudiantService etudiantService;
    private TextView id, nom, continent, superficie, nbreHabitant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_etudiant);

//        Etudiant etudiant = (Etudiant) getIntent().getSerializableExtra("selectedEtudiant");
        etudiantService = Config.getApiClient().create(EtudiantService.class);
        long idEtudiant = getIntent().getLongExtra("idEtudiant", 0);
        id = findViewById(R.id.tvDId);
        nom = findViewById(R.id.tvDNom);
        continent = findViewById(R.id.tvDPrenom);
        superficie = findViewById(R.id.tvDMatricule);
        nbreHabitant = findViewById(R.id.tvDSpecialite);
        initDetails("", "", "", "","");

        etudiantService.getEtudiantById(idEtudiant).enqueue(new Callback<Etudiant>() {
            @Override
            public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {
                if (response.isSuccessful()) {
                    Etudiant etudiant = response.body();
                    initDetails(String.valueOf(etudiant.getId()), etudiant.getNom(), etudiant.getContinent(),
                            String.valueOf(etudiant.getSuperficie()), String.valueOf(etudiant.getNombreHabitants()));
                }
            }

            @Override
            public void onFailure(Call<Etudiant> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    private void initDetails(String id, String nom, String continent, String superficie, String nbreHabitant) {
        this.id.setText(id);
        this.nom.setText(nom);
        this.continent.setText(continent);
        this.superficie.setText(superficie);
        this.nbreHabitant.setText(nbreHabitant);
    }
}