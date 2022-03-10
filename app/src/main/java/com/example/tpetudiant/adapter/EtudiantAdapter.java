package com.example.tpetudiant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tpetudiant.R;
import com.example.tpetudiant.activity.DetailsEtudiantActivity;
import com.example.tpetudiant.models.Etudiant;

import java.util.List;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.EtudiantViewHolder>{
    private final List<Etudiant> etudiantList;
    private int selectedItemPosition;

    public EtudiantAdapter(List<Etudiant> etudiantList) {
        this.etudiantList = etudiantList;
    }

    @NonNull
    @Override
    public EtudiantAdapter.EtudiantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_etudiant, parent, false);
        return new EtudiantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EtudiantAdapter.EtudiantViewHolder holder, int position) {
        Etudiant etudiant = etudiantList.get(position);
        holder.tvNom.setText(etudiant.getNom());
        holder.tvPrenom.setText(etudiant.getPrenom());
        holder.itemView.setOnClickListener(view -> {
            Context context = view.getContext();
            Intent intent = new Intent(context, DetailsEtudiantActivity.class);
//            intent.putExtra("selectedEtudiant", etudiant);
            intent.putExtra("idEtudiant", etudiant.getId());
            context.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(view -> {
            setSelectedItemPosition(position);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return etudiantList.size();
    }

    public List<Etudiant> getEtudiantList() {
        return etudiantList;
    }

    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }

    private void setSelectedItemPosition(int selectedItemPosition) {
        this.selectedItemPosition = selectedItemPosition;
    }

    public static class EtudiantViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        public TextView tvNom;
        public TextView tvPrenom;

        public EtudiantViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNom = itemView.findViewById(R.id.tvLNom);
            tvPrenom = itemView.findViewById(R.id.tvLPrenom);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Options");
            contextMenu.add(Menu.NONE, R.string.menu_item_editer, Menu.NONE, "Editer");
            contextMenu.add(Menu.NONE, R.string.menu_item_delete, Menu.NONE, "Supprimer");
        }
    }
}
