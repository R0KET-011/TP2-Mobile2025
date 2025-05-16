/****************************************
 Fichier : GroupRepository.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 Manager entre Api et Database pour projets.

 Date : 05/12/2025

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
import com.example.teamwork.Database.Dao.GroupDao;
import com.example.teamwork.Database.Tables.Group;
import com.google.gson.Gson;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupRepository {

    private GroupDao groupDao;

    /** Constructeur pour le répositoire de groupe
     * @param context est Context de l'activité*/
    public GroupRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        groupDao = db.groupDao();
    }

    /** Fait appel api pour importer la base de donnée de groupes
     * @param api est ApiInterface*/
    public void fetchInsertGroups(ApiInterface api) {
        Call<List<Group>> call = api.getGroups();
        Log.v("Group API Call", "Call done.");

        call.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.v("Group API Response Body", "Contains something");
                    try {
                        new Thread(() -> {
                            groupDao.insertGroups(response.body());
                            Log.v("Insertion", "Group API Insertion successful");
                        }).start();
                    }
                    catch(Exception e) {
                        Log.e("ERROR FROM FETCH", "" +e);
                    }
                }
                else {
                    Log.v("Group API Response Body", "Issues detected");
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                t.printStackTrace();
                Log.v("Group API Call Fail", "Failure", t);
            }
        });
    }
}
