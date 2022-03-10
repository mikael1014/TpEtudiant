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
import com.example.tpetudiant.activity.DetailsSpecialiteActivity;
import com.example.tpetudiant.models.Specialite;

import java.util.List;

public class SpecialiteAdapter extends RecyclerView.Adapter<SpecialiteAdapter.SpecialiteViewHolder>{
    private final List<Specialite> specialiteList;
    private int selectedItemPosition;

    public SpecialiteAdapter(List<Specialite> specialiteList) {
        this.specialiteList = specialiteList;
    }

    @NonNull
    @Override
    public SpecialiteAdapter.SpecialiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_specialite, parent, false);
        return new SpecialiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialiteAdapter.SpecialiteViewHolder holder, int position) {
        Specialite specialite = specialiteList.get(position);
        holder.tvTitre.setText(specialite.getTitre());
        holder.tvDescription.setText(specialite.getDescription());
        holder.itemView.setOnClickListener(view -> {
            Context context = view.getContext();
            Intent intent = new Intent(context, DetailsSpecialiteActivity.class);
//            intent.putExtra("selectedSpecialite", specialite);
            intent.putExtra("idSpecialite", specialite.getId());
            context.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(view -> {
            setSelectedItemPosition(position);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return specialiteList.size();
    }

    public List<Specialite> getSpecialiteList() {
        return specialiteList;
    }

    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }

    private void setSelectedItemPosition(int selectedItemPosition) {
        this.selectedItemPosition = selectedItemPosition;
    }

    public static class SpecialiteViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        public TextView tvTitre;
        public TextView tvDescription;

        public SpecialiteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitre = itemView.findViewById(R.id.tvLNom);
            tvDescription = itemView.findViewById(R.id.tvLPrenom);
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
