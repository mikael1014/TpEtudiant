package com.example.tpetudiant;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tpetudiant.activity.EditEtudiantActivity;
import com.example.tpetudiant.adapter.EtudiantAdapter;
import com.example.tpetudiant.models.Etudiant;
import com.example.tpetudiant.service.Config;
import com.example.tpetudiant.service.EtudiantService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EtudiantService etudiantService;
    private List<Etudiant> etudiantList = new ArrayList<>();
    private EtudiantAdapter etudiantAdapter;
    private RecyclerView recyclerView;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewEtudiant);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chargementListe();
        etudiantService = Config.getApiClient().create(EtudiantService.class);
        etudiantService.getListeEtudiant().enqueue(new Callback<List<Etudiant>>() {
            @Override
            public void onResponse(Call<List<Etudiant>> call, Response<List<Etudiant>> response) {
                if (response.isSuccessful()) {
                    etudiantList = response.body();
                    chargementListe();

                }
            }

            @Override
            public void onFailure(Call<List<Etudiant>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == 201) {
                            Intent intent = result.getData();
                            Etudiant etudiant = (Etudiant) intent.getSerializableExtra("newEtudiant");
                            etudiantList.add(etudiant);
                            chargementListe();
                        }

                        if (result.getResultCode() == 200) {
                            Intent intent = result.getData();
                            Etudiant etudiant = (Etudiant) intent.getSerializableExtra("updatedEtudiant");

                            for (Etudiant p : etudiantList) {
                                if (p.getId().equals(etudiant.getId())) {

                                    etudiantList.set(etudiantList.indexOf(p), etudiant );
                                    break;
                                }
                            }
                            chargementListe();
                        }
                    }
                }
        );
    }

    private void chargementListe() {
        etudiantAdapter = new EtudiantAdapter(etudiantList);
        recyclerView.setAdapter(etudiantAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mon_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, EditEtudiantActivity.class);
//        startActivity(intent);
        activityResultLauncher.launch(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
//        etudiantService.getListeEtudiant().enqueue(new Callback<List<Etudiant>>() {
//            @Override
//            public void onResponse(Call<List<Etudiant>> call, Response<List<Etudiant>> response) {
//                if (response.isSuccessful()) {
//                    etudiantList = response.body();
//                    chargementListe();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Etudiant>> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
        super.onResume();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = etudiantAdapter.getSelectedItemPosition();
        Etudiant etudiant = etudiantList.get(position);
        switch (item.getItemId()) {
            case R.string.menu_item_editer:
                Intent intent = new Intent(this, EditEtudiantActivity.class);
                intent.putExtra("editMode", 1);
                intent.putExtra("selectedEtudiantId", etudiant.getId());
                activityResultLauncher.launch(intent);
                break;
            case R.string.menu_item_delete:
                new AlertDialog.Builder(this)
                        .setTitle("Suppression")
                        .setMessage("Voulez vraiment supprimer?")
                        .setPositiveButton(android.R.string.yes, ((dialogInterface, i) -> {
                            etudiantService.deleteEtudiant(etudiant.getId()).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        etudiantList.remove(position);
                                        chargementListe();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }))
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}