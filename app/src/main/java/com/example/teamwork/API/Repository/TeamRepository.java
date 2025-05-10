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

    public TeamRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        teamDao = db.teamDao();
    }

    public void fetchInsertTeams(ApiInterface api) {
        Call<List<Team>> call = api.getTeams();
        Log.v("Team API Call", "Call done.");

        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                Log.v("TeamResponse", "Response received", new Throwable(String.valueOf(response)));

                if (response.isSuccessful() && response.body() != null) {
                    Log.v("Team Response Body", "Contains something");
                    try {
                        new Thread (() -> {
                            Gson gson = new Gson();
                            String json = gson.toJson(response.body());
                            Log.d("DEBUG TEAMS", json);
                            Type typeList = new TypeToken<List<Team>>() {}.getType();
                            List<Team> teams = gson.fromJson(json, typeList);
                            for (Team team: teams) {
                                Gson gsonTeam = new Gson();
                                String jsonTeam = gsonTeam.toJson(team);
                                Log.d("DEBUG TEAM", jsonTeam);

                                Team teamInsert = new Team();
                                teamInsert.setId(team.getId());
                                teamInsert.setName(team.getName());
                                teamInsert.setDescription(team.getDescription());
                                teamInsert.setProjectId(team.getProjectId());
                                Log.d("DEBUG MADE TEAM", teamInsert.toString());
                                teamDao.insert(teamInsert);
                            }
                            Log.v("Team Insertion", "Team insertions successful");
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
                Log.v("Team Call Fail", "Failure", t);
            }
        });
    }

    public LiveData<List<Team>> getAllTeams() {
        return teamDao.getAllTeams();
    }
}
