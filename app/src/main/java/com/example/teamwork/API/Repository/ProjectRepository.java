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

    /** Constructeur du répositoire de projet
     * @param context est Context de l'activité*/
    public ProjectRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        projectDao = db.projectDao();
    }

    /** Fait appel api pour importer la base de donnée de projets
     * @param api est ApiInterface */
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

    /** Fait appel api pour exporter un nouveau projet
     * @param api est ApiInterface
     * @param project est l'objet du projet
     * @param groupID est le id du group auquel le projet appartient. */
    public void sendCreateProject(ApiInterface api, Project project, int groupID) {
        Call<Void> call = api.createProject(groupID, project);
        Log.v("Project Create API", "Call done");

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(response.isSuccessful()) {
                    Log.v("Project API Creation", "Inserted.");
                }
                else {
                    Log.v("Project API Creation", "Failed.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("Project API Call Fail", "Failure", t);
            }
        });

    }

    /** Fait appel api pour supprimer un projet spécifique
     * @param api est ApiInterface
     * @param id est int */
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

    /** Fait appel api pour la modification d'un projet.
     * @param api est ApiInterface
     * @param project est l'objet du projet*/
    public void sendUpdateProject(ApiInterface api, Project project){
        Call<Void> call = api.updateProject(project);
        Log.v("Project PUT Api", "Call done");

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.v("Project PUT API", "Update success.");
                }
                else{
                    Log.v("Project PUT API", "Update success.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("Project API Call Fail", "Failure.", t);
            }
        });
    }
}
