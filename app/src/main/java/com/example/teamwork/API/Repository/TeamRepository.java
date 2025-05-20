/****************************************
 Fichier : TeamRepository.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 Manager entre Api et database pour équipes.

 Date : 05/05/2025

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

import androidx.lifecycle.LiveData;

import com.example.teamwork.API.ApiInterface;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Dao.TeamDao;
import com.example.teamwork.Database.Tables.Project;
import com.example.teamwork.Database.Tables.Team;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamRepository {

    private TeamDao teamDao;

    /** Constructeur du répositoire équipe
     * @param context est Context de l'activité */
    public TeamRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        teamDao = db.teamDao();
    }

    /** Fait appel api pour importer les équipes de la base de données Teams
     * @param api est ApiInterface */
    public void fetchInsertTeams(ApiInterface api) {
        Call<List<Team>> call = api.getTeams();
        Log.v("Team API Call", "Call done.");

        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.v("Team API Response Body", "Contains something");
                    try {
                        new Thread (() -> {
                            Gson gson = new Gson();
                            String json = gson.toJson(response.body());
                            Type typeList = new TypeToken<List<Team>>() {}.getType();
                            List<Team> teams = gson.fromJson(json, typeList);
                            for (Team team: teams) {
                                Gson gsonTeam = new Gson();
                                String jsonTeam = gsonTeam.toJson(team);

                                Team teamInsert = new Team();
                                teamInsert.setId(team.getId());
                                teamInsert.setName(team.getName());
                                teamInsert.setDescription(team.getDescription());
                                teamInsert.setProjectId(team.getProjectId());
                                teamInsert.setState(team.getState());

                                Log.v("api", jsonTeam);
                                teamDao.insert(teamInsert);
                            }
                            Log.v("Team API Insertion", "Team insertions successful");
                        }).start();
                    }
                    catch(Exception e) {
                        Log.e("ERROR FROM FETCH", "" + e);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                t.printStackTrace();
                Log.v("Team Call API Fail", "Failure", t);
            }
        });
    }

    /** Fait appel api pour la suppression d'une équipe
     * @param api est ApiInterface
     * @param id est int */
    public void sendDeleteTeam(ApiInterface api, int id) {
        Call<Void> call = api.deleteTeam(id);
        Log.v("TEAM API DELETE", "Call done");

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.v("TEAM API DELETE", "SUCESS");
                }
                else {
                    Log.v("TEAM API DELETE", "FAIL");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("TEAM API CALL", "Failure", t);
            }
        });
    }

    /** Fait appel api pour la modification des détails d'une équipe
     * @param api est ApiInterface
     * @param team est objet de l'équipe */
    public void sendUpdateTeam(ApiInterface api, Team team) {
        Call<Void> call = api.updateTeam(team);
        Log.v("TEAM API PUT CALL", "Call done");

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Log.v("TEAM API PUT", "SUCCESS");
                }
                else {
                    Log.v("TEAM API PUT", "FAIL");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("TEAM API PUT CALL", "Call done");
            }
        });
    }

    /** Fait appel api pour la création d'une équipe
     * @param api est ApiInterface
     * @param team est objet de l'équipe */
    public void sendCreateTeam(ApiInterface api, Team team){
        Call<Void> call = api.createTeam(team);
        Log.v("TEAM API CREATE CALL", "Call done");

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.v("TEAM API CREATE", "SUCCESS");
                }
                else {
                    Log.v("TEAM API CREATE", "FAIL");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("TEAM API CREATE CALL", "Failure", t);
            }
        });
    }
}
