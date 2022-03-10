package com.example.tpetudiant.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Specialite  extends BaseModel implements Serializable {
    private String titre;
    private String description;


    private List<Etudiant> etudiants = new ArrayList<>();

    public Specialite(String titre, String description) {
        this.titre = titre;
        this.description = description;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public Specialite() {
    }



}
