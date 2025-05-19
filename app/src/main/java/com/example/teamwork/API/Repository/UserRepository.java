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
import com.example.teamwork.Activity.Auth.Authentication;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Dao.UserDao;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.Database.Tables.User;
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

    public void login(ApiInterface api, JsonObject json){
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
                        }).start();
                    } catch (Exception e) {
                        Log.e("ERROR FROM FETCH", "" + e);
                    }
                } else {
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
