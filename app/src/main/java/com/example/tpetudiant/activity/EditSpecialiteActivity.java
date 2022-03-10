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
import com.example.tpetudiant.models.Etudiant;
import com.example.tpetudiant.service.Config;
import com.example.tpetudiant.service.EtudiantService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSpecialiteActivity extends AppCompatActivity {
    private EditText edtNom, edtContinent, edtSuperficie, edtNombreHabitants;
    private EtudiantService paysService;
    private Long id;
    private int editMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_etudiant);

        paysService = Config.getApiClient().create(EtudiantService.class);

        edtNom = findViewById(R.id.edtNom);
        edtContinent = findViewById(R.id.edtPrenom);
        edtNombreHabitants = findViewById(R.id.edtSpecialite);
        edtSuperficie = findViewById(R.id.edtMatricule);

        id = getIntent().getLongExtra("selectedPaysId", 0);
        editMode = getIntent().getIntExtra("editMode", 0);
        if (editMode == 1) {
            initDetails("", "", "","");
            initForm();
        }
    }

    private void initForm() {
        paysService.getPaysById(id).enqueue(new Callback<Etudiant>() {
            @Override
            public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {
                if (response.isSuccessful()) {
                    Etudiant pays = response.body();
                    initDetails(pays.getNom(), pays.getContinent(), pays.getSuperficie() +"",
                            pays.getNombreHabitants() + "");
                }
            }

            @Override
            public void onFailure(Call<Etudiant> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initDetails(String nom, String continent, String superficie, String nbreHabitant) {
        this.edtNom.setText(nom);
        this.edtContinent.setText(continent);
        this.edtSuperficie.setText(superficie);
        this.edtNombreHabitants.setText(nbreHabitant);
    }



    public void enregisterPays(View view) {
        Etudiant pays = new Etudiant();
        pays.setContinent(edtContinent.getText().toString());
        pays.setNom(edtNom.getText().toString());
        pays.setNombreHabitants(Long.parseLong(edtNombreHabitants.getText().toString()));
        pays.setSuperficie(Long.parseLong(edtSuperficie.getText().toString()));

        if (editMode == 1) {
            //edit
            paysService.updatePays(id, pays).enqueue(new Callback<Etudiant>() {
                @Override
                public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {
                    if(response.isSuccessful()) {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("updatedPays", response.body());
                        setResult(response.code(), intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Etudiant> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        } else {
            paysService.savePays(pays).enqueue(new Callback<Etudiant>() {
                @Override
                public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {
                    if (response.isSuccessful()) {
//                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
//                    startActivity(intent);
                        Etudiant newPays = response.body();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("newPays", newPays);
                        setResult(response.code(), intent);
                        finish();
                    } else {
                        Log.e("Error code", String.valueOf(response.code()));
                        Toast.makeText(getBaseContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Etudiant> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

}