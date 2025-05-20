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

    /** Constructeur du répositoire TeamStudent
     * @param context est Context de l'activité*/
    public TeamStudentRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        teamStudentDao = db.teamStudentDao();
    }

    /** Fait appel api pour importer les pairs étudiant-équipe de la base de donnée
     * @param api est ApiInterface*/
    public void fetchInsertTeamStudent(ApiInterface api) {
        Call<List<TeamStudent>> call = api.getTeamStudent();
        Log.v("TeamStudent API Call", "Call done.");

        call.enqueue(new Callback<List<TeamStudent>>() {
            @Override
            public void onResponse(Call<List<TeamStudent>> call, Response<List<TeamStudent>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.v("TeamStudent API Response Body", "Contains something");
                    String json = response.body().toString();
                    Log.v("TEAMSTUDENT STRING", json);
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

    /** Fsit appel api pour la création d'une pair étudiant-équipe
     * @param api ApiInterface
     * @param team_id est int
     * @param json est un objet JSON qui contient le id de l'étudiant */
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

    /** Fait appel api pour la suppression d'une relation étudiant-équipe
     * @param api est ApiInterface
     * @param project_id est int
     * @param user_id est int */
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
