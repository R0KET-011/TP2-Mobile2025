/****************************************
 Fichier : ProjectAdapter.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 Intercept la requête http pour ajouter un token d'autentification.

 Date : 05/09/2025

 Vérification :
 Date Nom Approuvé

 =========================================================
 Historique de modifications :
 Date Nom Description

 =========================================================
 ****************************************/

package com.example.teamwork.API;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor{

    private String token;

    /** Constructeur AuthInterceptor
     * @param token est String, token d'authentification*/
    public AuthInterceptor(String token) {
        this.token = token;
    }

    /** modifier l'inception d'une requête HTTP pour y insérer le token d'identification
     * @param chain est requête HTTP */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder().header("Authorization", "Bearer " + token);
        Request request = builder.build();
        return chain.proceed(request);
    }
}
