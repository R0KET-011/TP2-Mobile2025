/****************************************
 Fichier : ApiClient.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 Connextion de base pour l'api.

 Date : 05/09/2025

 Vérification :
 Date Nom Approuvé

 =========================================================
 Historique de modifications :
 Date Nom Description

 =========================================================
 ****************************************/

package com.example.teamwork.API;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String token) {
        if (retrofit == null) {
            OkHttpClient client =
                    new OkHttpClient.Builder().addInterceptor(new AuthInterceptor(token)).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit;
    }

}