package com.example.tp2_mobile2025;

import java.lang.reflect.Array;

public class Project {
    int id = 0;
    String name = "";
    String description = "";
    int min_per_team = 0;
    int max_per_team = 0;
    boolean joinable = false;
    boolean creatable = false;
    boolean common_classes = false;

    public Project(int newId, String newName, String newDescription, int newMin, int newMax,
                   boolean newJoinable, boolean newCreatable, boolean newCommon ) {
        this.id = newId;
        this.name = newName;
        this.description = newDescription;
        this.min_per_team = newMin;
        this.max_per_team = newMax;
        this.joinable = newJoinable;
        this.creatable = newCreatable;
        this.common_classes = newCommon;
    }

    // Setter et Getter pour l'Id du projet.
    public void setId(int newId) {this.id = newId; }
    public int getId() {return this.id; }

    // Setter et Getter pour le nom du projet.
    public void setName(String newName) {this.name =newName; }
    public String getName() {return this.name; }

    // Setter et Getter pour la description d'un projet.
    public void setDescrption(String newDescription) {this.description = newDescription; }
    public String getDescription() {return this.description; }

    // Setter et Getter pour le minimum de membres dans une équipe dans le projet.
    public void setMinPetTeam(int newMin) {this.min_per_team = newMin; }
    public int getMinPerTeam() {return this.min_per_team; }

    // Setter et Getter pour le maximum de membres dans une équipe dans le projet
    public void setMax_per_team(int newMax) {this.max_per_team = newMax; }
    public int getMaxPerTeam() {return this.max_per_team; }

    // Setter et Getter pour le statut rejoignable des équipes du projet.
    public void setJoinable(boolean newJoinable) {this.joinable = newJoinable; }
    public boolean getJoinable() {return this.joinable; }

    // Setter et Getter pour le statut de création d'équipe du projet.
    public void setCreatable(boolean newCreatable) {this.creatable = newCreatable; }
    public boolean getCreatable() {return this.creatable; }

    // Setter et Getter pour le statut des cours commun requis pour les équipes du projet.
    public void setCommonClasses(boolean newCommon) {this.common_classes = newCommon; }
    public boolean getCommonClasses() {return this.common_classes; }

}
