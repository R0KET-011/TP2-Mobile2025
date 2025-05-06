/****************************************
 Fichier : Account.java
 Auteur : Samy Larochelle
 Fonctionnalité : Fonctionalités 33, Gestion d'authentification
 Date : 05/05/2025
 Vérification :
 Date           Nom                 Approuvé
 =========================================================
 Historique de modifications :
 Date           Nom                 Description
 05/05/2025     Samy Larochelle     Création
 =========================================================
 ****************************************/

package com.example.tp2_mobile2025.Auth;

import android.graphics.Bitmap;

public class Account {
    private String email;
    private String password;
    private String token;
    private Bitmap image;

    public Account(String email, String password, String token, Bitmap image) {
        this.email = email;
        this.password = password;
        this.token = token;
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
