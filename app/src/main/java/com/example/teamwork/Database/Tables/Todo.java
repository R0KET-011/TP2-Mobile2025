/****************************************
 Fichier : Todo.java
 Auteur : Samy Larochelle
 Fonctionnalité : Fonctionalités 36, Gestion des tâches
 Date : 05/05/2025
 Vérification :
 Date           Nom                 Approuvé
 =========================================================
 Historique de modifications :
 Date           Nom                 Description
 05/09/2025     Samy Larochelle     Création
 05/10/2025     Samy Larochelle     Ajout de completed
 =========================================================
 ****************************************/

package com.example.teamwork.Database.Tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todos")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int project_id;
    private String nom;
    private String description;
    private String lien_audio;
    private boolean completed;

    public Todo(int project_id, String nom, String description, String lien_audio, boolean completed) {
        this.project_id = project_id;
        this.nom = nom;
        this.description = description;
        this.lien_audio = lien_audio;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLien_audio() {
        return lien_audio;
    }

    public void setLien_audio(String lien_audio) {
        this.lien_audio = lien_audio;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
