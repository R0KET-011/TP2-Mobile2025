package com.example.teamwork.Database.Tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todos")
public class Todo {
    @PrimaryKey
    private int id;
    private int project_id;
    private String nom;
    private String description;
    private String lien_audio;

    public Todo(int id, int project_id, String nom, String description, String lien_audio) {
        this.id = id;
        this.project_id = project_id;
        this.nom = nom;
        this.description = description;
        this.lien_audio = lien_audio;
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
}
