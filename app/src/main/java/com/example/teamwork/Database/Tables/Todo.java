package com.example.teamwork.Database.Tables;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todos")
public class Todo {
    @PrimaryKey
    private int id;
    private String nom;
    private String description;
    private Bitmap lien_audio;

    public Todo(int id, String nom, String description, Bitmap lien_audio) {
        this.id = id;
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

    public Bitmap getLien_audio() {
        return lien_audio;
    }

    public void setLien_audio(Bitmap lien_audio) {
        this.lien_audio = lien_audio;
    }
}
