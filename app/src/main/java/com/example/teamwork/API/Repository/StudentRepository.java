/****************************************
 Fichier : StudentRepository.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 Manager entre Api et Database pour les étudiants.

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
import com.example.teamwork.Database.Dao.StudentDao;
import com.example.teamwork.Database.Tables.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentRepository {

    private StudentDao studentDao;

    /** Constructeur du répositoire étudiant
     * @param context est Context de l'activité */
    public StudentRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        studentDao = db.studentDao();
    }

    /** Fait appel api pour importer les étudiants de la base de donnée utilisateur
     * @param api est ApiInterface*/
    public void fetchInsertStudents(ApiInterface api) {
        Call<List<Student>> call = api.getStudents();
        Log.v("Group API Call", "Call done.");

        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.v("Student API Response Body", "Contains something");
                    try {
                        new Thread(() -> {
                            studentDao.insertStudents(response.body());
                            Log.v("Insertion", "Student API Insertion successful");
                        }).start();
                    }
                    catch(Exception e) {
                        Log.e("ERROR ROM FETCH", "" +e);
                    }
                }
                else {
                    Log.v("Student API Response Body", "Issues detected");
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                t.printStackTrace();
                Log.v("Student API Call Fail", "Failure", t);
            }
        });
    }
}
