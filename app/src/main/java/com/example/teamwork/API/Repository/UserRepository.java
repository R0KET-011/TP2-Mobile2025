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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.teamwork.API.ApiInterface;
import com.example.teamwork.Activity.Auth.Authentication;
import com.example.teamwork.Activity.Project.ProjectActivity;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Dao.UserDao;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.Database.Tables.User;
import com.google.android.material.snackbar.Snackbar;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private UserDao userDao;

    /** Constructeur du répertoire utilisateur
     * @param context est Context de l'activité */
    public UserRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        userDao = db.userDao();
    }

    /**
     * Envoit un requete à l'api pour envoyer un mail de mise à jour de mot de passe
     * à l'utilisateur
     * @param api interface api a appelé
     * @param json le contenu de la requete
     * @param context l'application a fermer à la fin de la requête
     * @param layout la layout où afficher le message d'erreur
     */
    public void sendMail(ApiInterface api, JsonObject json,Context context, LinearLayout layout) {
        Call<Void> call = api.registerUser(json);
        Log.v("User POST Api", "Call done");

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    ((Activity)context).finish();
                    Log.v("User POST API", "Email sent.");
                } else {
                    Snackbar.make(layout, "Courriel erronés", Snackbar.LENGTH_SHORT).show();
                    Log.v("User POST API", "Email Error.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("User API Call Fail", "Failure.", t);
            }
        });
    }

    /**
     * Envoit une requête à l'api pour obtenir les informations de l'utilisateur
     * auquel on veut se connecter
     * @param api interface api a appelé
     * @param json le contenu de la requete
     * @param context l'application a fermer à la fin de la requête
     * @param layout la layout où afficher le message d'erreur
     */
    public void login(ApiInterface api, JsonObject json, Context context, LinearLayout layout) {
        Call<User> call = api.login(json);
        Log.v("User GET Api", "Call done");

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.v("User", response.message());
                if (response.isSuccessful()) {
                    try {
                        new Thread(() -> {
                            User user = response.body();
                            String email = (user.getEmail());
                            int code = Integer.parseInt(email.substring(0, 9));
                            Authentication.setCode(code);
                            Authentication.setId(user.getId());
                            Authentication.setToken(user.getToken());
                            Authentication.setEmail(user.getEmail());
                            Authentication.setName(user.getFirst_name() + " " + user.getLast_name());
                            Authentication.setIsStudent(user.isStudent());
                            userDao.deleteAll();
                            userDao.insert(user);
                            Log.v("User GET API", "Login request sent.");
                            Intent intent = new Intent(context, ProjectActivity.class);
                            context.startActivity(intent);
                        }).start();
                    } catch (Exception e) {
                        Log.e("ERROR FROM FETCH", "" + e);
                    }
                } else {
                    Snackbar.make(layout, "Identifiants erronés", Snackbar.LENGTH_SHORT).show();
                    Log.v("User GET API", "Login request error.");
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.v("User API Call Fail", "Failure.", t);
            }
        });
    }
}
