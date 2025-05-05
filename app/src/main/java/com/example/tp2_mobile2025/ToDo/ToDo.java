/****************************************
 Fichier : ToDo.java
 Auteur : Samy Larochelle
 Fonctionnalité : Fonctionalités 36, Gestion des tâches
 Date : 05/05/2025
 Vérification :
 Date           Nom                 Approuvé
 =========================================================
 Historique de modifications :
 Date           Nom                 Description
 05/05/2025     Samy Larochelle     Création
 =========================================================
 ****************************************/

package com.example.tp2_mobile2025.ToDo;

public class ToDo {
    private String nom;
    private String description;
    private boolean bCompleted;
    private String audio_file_path;


    public ToDo(String nom, String description, boolean bCompleted, String audio_file_path) {
        this.nom = nom;
        this.description = description;
        this.bCompleted = bCompleted;
        this.audio_file_path = audio_file_path;
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

    public boolean isbCompleted() {
        return bCompleted;
    }

    public void setbCompleted(boolean bCompleted) {
        this.bCompleted = bCompleted;
    }

    public String getAudio_file_path() {
        return audio_file_path;
    }

    public void setAudio_file_path(String audio_file_path) {
        this.audio_file_path = audio_file_path;
    }
}
