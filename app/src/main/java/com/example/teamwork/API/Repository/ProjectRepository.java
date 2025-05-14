/****************************************
 Fichier : ProjectRepository.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 Manager entre Api et Database pour projets.

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
import android.os.AsyncTask;
import android.util.Log;
import androidx.lifecycle.LiveData;

import com.example.teamwork.API.ApiInterface;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Dao.ProjectDao;
import com.example.teamwork.Database.Tables.Project;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectRepository {

    private ProjectDao projectDao;

    public ProjectRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        projectDao = db.projectDao();
    }

    public void fetchInsertProjects(ApiInterface api) {
        Call<List<Project>> call = api.getProjects();
        Log.v("Project API Call", "Call done.");

        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.v("Project API Response Body", "Contains something");
                    try {
                        new Thread (() -> {
                            projectDao.insertProjects(response.body());
                            Log.v("Insertion", "Project API insertions successful");
                        }).start();
                    }
                    catch (Exception e) {
                        Log.e("ERROR FROM FETCH", "" + e);
                    }
                }
                else {
                    Log.v("Project API Response Body", "Issues detected");
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                t.printStackTrace();
                Log.v("Project API Call Fail", "Failure", t);
            }
        });
    }

    public void sendDeleteProject(ApiInterface api, int id) {
        Call<Void> call = api.deleteProject(id);
        Log.v("Project Delete API", "Call done.");

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    Log.v("Project Delete API", "Delete success.");
                }
                else {
                    Log.v("Project Delete API", "Delete failed.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("Project Delete API", "Response failed.");
            }
        });
    }
}
