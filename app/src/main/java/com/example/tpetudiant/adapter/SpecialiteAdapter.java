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

public class SpecialiteAdapter extends RecyclerView.Adapter<SpecialiteAdapter.PaysViewHolder>{
    private final List<Etudiant> paysList;
    private int selectedItemPosition;

    public SpecialiteAdapter(List<Etudiant> paysList) {
        this.paysList = paysList;
    }

    @NonNull
    @Override
    public SpecialiteAdapter.PaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_etudiant, parent, false);
        return new PaysViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialiteAdapter.PaysViewHolder holder, int position) {
        Etudiant pays = paysList.get(position);
        holder.tvNom.setText(pays.getNom());
        holder.tvContinent.setText(pays.getContinent());
        holder.itemView.setOnClickListener(view -> {
            Context context = view.getContext();
            Intent intent = new Intent(context, DetailsEtudiantActivity.class);
//            intent.putExtra("selectedPays", pays);
            intent.putExtra("idPays", pays.getId());
            context.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(view -> {
            setSelectedItemPosition(position);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return paysList.size();
    }

    public List<Etudiant> getPaysList() {
        return paysList;
    }

    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }

    private void setSelectedItemPosition(int selectedItemPosition) {
        this.selectedItemPosition = selectedItemPosition;
    }

    public static class PaysViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        public TextView tvNom;
        public TextView tvContinent;

        public PaysViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNom = itemView.findViewById(R.id.tvLNom);
            tvContinent = itemView.findViewById(R.id.tvLPrenom);
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
