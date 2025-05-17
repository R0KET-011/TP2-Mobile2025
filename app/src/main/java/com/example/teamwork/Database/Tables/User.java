/****************************************
 Fichier : User.java
 Auteur : Samy Larochelle
 Fonctionnalité : Fonctionalité 33, Gestion d'utilisateur
 Date : 05/05/2025
 Vérification :
 Date           Nom                 Approuvé
 =========================================================
 Historique de modifications :
 Date           Nom                 Description
 05/05/2025     Samy Larochelle     Création
 05/17/2025     Samy Larochelle     Ajout du first_name/last_name
 =========================================================
 ****************************************/
package com.example.teamwork.Database.Tables;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String token;
    private String lien_image;
    private boolean isStudent;

    /**
     * @param id         the db id of the user
     * @param email      the email of the user
     * @param token      the api token of the user
     */
    public User(int id, String first_name, String last_name, String email, String token, boolean isStudent) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.token = token;
        this.isStudent = isStudent;
    }

    /**
     * @return the db id of the user
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the db id of the user
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the api token of the user
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the api token of the user
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the image file path of the user's profile picture
     */
    public String getLien_image() {
        return lien_image;
    }

    /**
     * @param lien_image the image file path of the user's profile picture
     */
    public void setLien_image(String lien_image) {
        this.lien_image = lien_image;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }
}
