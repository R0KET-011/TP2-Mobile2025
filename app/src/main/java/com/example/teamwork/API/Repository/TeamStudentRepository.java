/****************************************
 Fichier : TeamStudentInterface.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 Pour les liens vers l'Api.

 Date : 05/09/2025

 Vérification :
 Date Nom Approuvé

 =========================================================
 Historique de modifications :
 Date Nom Description

 =========================================================
 ****************************************/

package com.example.teamwork.API.Repository;

import android.content.Context;
import android.util.Log;

import com.example.teamwork.API.ApiInterface;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Dao.TeamStudentDao;
import com.example.teamwork.Database.Tables.TeamStudent;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamStudentRepository {

    private TeamStudentDao teamStudentDao;

    public TeamStudentRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        teamStudentDao = db.teamStudentDao();
    }

    public void fetchInsertTeamStudent(ApiInterface api) {
        Call<List<TeamStudent>> call = api.getTeamStudent();
        Log.v("TeamStudent API Call", "Call done.");

        call.enqueue(new Callback<List<TeamStudent>>() {
            @Override
            public void onResponse(Call<List<TeamStudent>> call, Response<List<TeamStudent>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.v("TeamStudent API Response Body", "Contains something");
                    try {
                        new Thread(() -> {
                            teamStudentDao.insertTeamStudent(response.body());
                            Log.v("Insertion", "TeamStudent API insertion successful");
                        }).start();
                    }
                    catch(Exception e){
                        Log.v("TeamStudent API Response Body", "Issues detected");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TeamStudent>> call, Throwable t) {
                t.printStackTrace();
                Log.v("TeamStudent API Call Fail", "Failure", t);
            }
        });
    }

    public void sendCreateRelation(ApiInterface api, int team_id, JsonObject json) {
        Call<Void> call = api.createTeamStudent(team_id, json);
        Log.v("TEAMSTUDENT API CREATE CALL", "Call done");

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.v("TEAMSTUDENT", response.toString());
                if (response.isSuccessful()){
                    Log.v("TEAMSTUDENT API CREATE", "SUCCESS");
                }
                else {
                    Log.v("TEAMSTUDENT API CREATE", "FAIL");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("TEAMSTUDENT API CREATE CALL", "Failure", t);
            }
        });
    }

    public void sendDeleteRelation(ApiInterface api, int project_id, int user_id) {
        Call<Void> call = api.deleteTeamStudent(project_id, user_id);
        Log.v("TEAMSTUDENT API DELETE CALL", "Call done");

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.v("TEAMSTUDENT", response.toString());
                if(response.isSuccessful()) {
                    Log.v("TEAMSTUDENT API DELETE", "SUCCESS");
                }
                else {
                    Log.v("TEAMSTUDENT API DELETE", "FAIL");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("TEAMSTUDENT API DELETE CALL", "Failure", t);
            }
        });
    }
}
