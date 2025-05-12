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

    /**
     * @param project_id  the project_id the to-do is associated with
     * @param nom         the name of the to-do
     * @param description the description of the to-do
     * @param lien_audio  the file path to the audio file associated with the to-do
     * @param completed   the completion state of the to-do
     */
    public Todo(int project_id, String nom, String description, String lien_audio, boolean completed) {
        this.project_id = project_id;
        this.nom = nom;
        this.description = description;
        this.lien_audio = lien_audio;
        this.completed = completed;
    }

    /**
     * @return the db id of the todo
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the db id of the todo
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the project_id the to-do is associated with
     */
    public int getProject_id() {
        return project_id;
    }

    /**
     * @param project_id the project_id the to-do is associated with
     */
    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    /**
     * @return the name of the to-do
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the name of the to-do
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the description of the to-do
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description of the to-do
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the file path to the audio file associated with the to-do
     */
    public String getLien_audio() {
        return lien_audio;
    }

    /**
     * @param lien_audio the file path to the audio file associated with the to-do
     */
    public void setLien_audio(String lien_audio) {
        this.lien_audio = lien_audio;
    }

    /**
     * @return the completion state of the to-do
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * @param completed the completion state of the to-do
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
