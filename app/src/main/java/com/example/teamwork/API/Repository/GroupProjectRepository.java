/****************************************
 Fichier : GroupPrjectRepository.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 Manager entre Api et Database pour les liens de projets et groupes.

 Date : 05/13/2025

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
import com.example.teamwork.Database.Dao.GroupProjectDao;
import com.example.teamwork.Database.Tables.Group;
import com.example.teamwork.Database.Tables.GroupProject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupProjectRepository {

    private GroupProjectDao groupProjectDao;

    public GroupProjectRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        groupProjectDao = db.groupProjectDao();
    }

    public void fetchInsertGroupProject(ApiInterface api) {
        Call<List<GroupProject>> call = api.getGroupProject();
        Log.v("GroupProject API Call", "Call done.");

        call.enqueue(new Callback<List<GroupProject>>() {
            @Override
            public void onResponse(Call<List<GroupProject>> call, Response<List<GroupProject>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.v("GroupProject Api Response Body", "Contains something");
                    try {
                        new Thread(()-> {
                            groupProjectDao.insertGroupProject(response.body());
                            Log.v("Insertion", "Group Project API inspertions successful");
                        }).start();
                    }
                    catch(Exception e) {
                        Log.e("ERROR ROM FETCH", "" + e);
                    }
                }
                else {
                    Log.v("GroupProject API Response Body", "Issues detected");
                }
            }

            @Override
            public void onFailure(Call<List<GroupProject>> call, Throwable t) {
                t.printStackTrace();
                Log.v("GroupProject API Call Fail", "Failure", t);
            }
        });
    }
}
