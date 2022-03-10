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

public class EditEtudiantActivity extends AppCompatActivity {
    private EditText edtNom, edtPrenom, edtMatricule, edtSpecialite;
    private EtudiantService etudiantService;
    private Long id;
    private int editMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_etudiant);

        etudiantService = Config.getApiClient().create(EtudiantService.class);

        edtNom = findViewById(R.id.edtNom);
        edtPrenom = findViewById(R.id.edtPrenom);
        edtSpecialite = findViewById(R.id.edtSpecialite);
        edtMatricule = findViewById(R.id.edtMatricule);

        id = getIntent().getLongExtra("selectedEtudiantId", 0);
        editMode = getIntent().getIntExtra("editMode", 0);
        if (editMode == 1) {
            initDetails("", "", "","");
            initForm();
        }
    }

    private void initForm() {
        etudiantService.getEtudiantById(id).enqueue(new Callback<Etudiant>() {
            @Override
            public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {
                if (response.isSuccessful()) {
                    Etudiant etudiant = response.body();
                    initDetails(etudiant.getNom(), etudiant.getPrenom(), etudiant.getMatricule() +"",
                            etudiant.getSpecialite() + "");
                }
            }

            @Override
            public void onFailure(Call<Etudiant> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initDetails(String nom, String prenom, String matricule, String specialite) {
        this.edtNom.setText(nom);
        this.edtPrenom.setText(prenom);
        this.edtMatricule.setText(matricule);
        this.edtSpecialite.setText(specialite);
    }



    public void enregisterEtudiant(View view) {
        Etudiant etudiant = new Etudiant();
        etudiant.setPrenom(edtPrenom.getText().toString());
        etudiant.setNom(edtNom.getText().toString());
        etudiant.setMatricule(edtMatricule.getText().toString());
 //      etudiant.setSpecialite(edtSpecialite.getText().toString());

        if (editMode == 1) {
            //edit
            etudiantService.updateEtudiant(id, etudiant).enqueue(new Callback<Etudiant>() {
                @Override
                public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {
                    if(response.isSuccessful()) {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("updatedEtudiant", response.body());
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
            etudiantService.saveEtudiant(etudiant).enqueue(new Callback<Etudiant>() {
                @Override
                public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {
                    if (response.isSuccessful()) {
//                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
//                    startActivity(intent);
                        Etudiant newEtudiant = response.body();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("newEtudiant", newEtudiant);
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