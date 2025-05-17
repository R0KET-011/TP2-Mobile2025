/****************************************
 Fichier : RegisterActivity.java
 Auteur : Samy Larochelle
 Fonctionnalité : Fonctionalités 33.3, Inscription
 Date : 05/16/2025
 Vérification :
 Date           Nom                 Approuvé
 =========================================================
 Historique de modifications :
 Date           Nom                 Description
 05/16/2025     Samy Larochelle     Création
 =========================================================
 ****************************************/
package com.example.teamwork.API.Repository;

import android.content.Context;
import android.util.Log;

import com.example.teamwork.API.ApiInterface;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Dao.UserDao;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private UserDao userDao;

    public UserRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        userDao = db.userDao();
    }

    public void sendMail(ApiInterface api, JsonObject json){
        Call<Void> call = api.registerUser(json);
        Log.v("User POST Api", "Call done");

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.v("User POST API", "Email sent.");
                } else {
                    Log.v("User POST API", "Email Sent.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("User API Call Fail", "Failure.", t);
            }
        });
    }
}
