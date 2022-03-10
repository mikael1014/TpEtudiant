package com.example.tpetudiant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tpetudiant.MainActivity;
import com.example.tpetudiant.R;
import com.example.tpetudiant.models.Specialite;
import com.example.tpetudiant.service.Config;
import com.example.tpetudiant.service.SpecialiteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSpecialiteActivity extends AppCompatActivity {
    private EditText edtTitre, edtDescription, edtEtudiants, edtNombreHabitants;
    private SpecialiteService specialiteService;
    private Long id;
    private int editMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_etudiant);

        specialiteService = Config.getApiClient().create(SpecialiteService.class);

        edtTitre = findViewById(R.id.edtNom);
        edtDescription = findViewById(R.id.edtPrenom);
        edtNombreHabitants = findViewById(R.id.edtSpecialite);
        edtEtudiants = findViewById(R.id.edtMatricule);

        id = getIntent().getLongExtra("selectedSpecialiteId", 0);
        editMode = getIntent().getIntExtra("editMode", 0);
        if (editMode == 1) {
            initDetails("", "", "");
            initForm();
        }
    }

    private void initForm() {
        specialiteService.getSpecialiteById(id).enqueue(new Callback<Specialite>() {
            @Override
            public void onResponse(Call<Specialite> call, Response<Specialite> response) {
                if (response.isSuccessful()) {
                    Specialite specialite = response.body();
                    initDetails(specialite.getTitre(), specialite.getDescription(), specialite.getEtudiants() +"");
                }
            }

            @Override
            public void onFailure(Call<Specialite> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initDetails(String titre, String description, String etudiants) {
        this.edtTitre.setText(titre);
        this.edtDescription.setText(description);
        this.edtEtudiants.setText(etudiants);
    }



    public void enregisterSpecialite(View view) {
        Specialite specialite = new Specialite();
        specialite.setDescription(edtDescription.getText().toString());
        specialite.setTitre(edtTitre.getText().toString());
//        specialite.setEtudiants(edtEtudiants.getText().toString());

        if (editMode == 1) {
            //edit
            specialiteService.updateSpecialite(id, specialite).enqueue(new Callback<Specialite>() {
                @Override
                public void onResponse(Call<Specialite> call, Response<Specialite> response) {
                    if(response.isSuccessful()) {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("updatedSpecialite", response.body());
                        setResult(response.code(), intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Specialite> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        } else {
            specialiteService.saveSpecialite(specialite).enqueue(new Callback<Specialite>() {
                @Override
                public void onResponse(Call<Specialite> call, Response<Specialite> response) {
                    if (response.isSuccessful()) {
//                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
//                    startActivity(intent);
                        Specialite newSpecialite = response.body();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("newSpecialite", newSpecialite);
                        setResult(response.code(), intent);
                        finish();
                    } else {
                        Log.e("Error code", String.valueOf(response.code()));
                        Toast.makeText(getBaseContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Specialite> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

}