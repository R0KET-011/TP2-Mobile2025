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

import android.util.Log;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

public class ApiClient {
    private static Retrofit retrofit = null;

    /** Constructeur de retrofit // Singleton
     * @param token est token authentification */
    public static Retrofit getClient(String token) {
        if (retrofit == null) {

            HttpLoggingInterceptor logs = new HttpLoggingInterceptor();
            logs.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client =
                    new OkHttpClient.Builder().addInterceptor(new AuthInterceptor(token))
                            .addInterceptor(logs).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit;
    }

    public static void retrofitReset() {
        Log.v("API", "Retrofit Reset.");
        retrofit = null;
    }

}