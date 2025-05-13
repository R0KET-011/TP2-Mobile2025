/****************************************
 Fichier : CourseRepository.java
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
import android.util.Log;

import com.example.teamwork.API.ApiInterface;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Dao.CourseDao;
import com.example.teamwork.Database.Tables.Course;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseRepository {

    private CourseDao courseDao;

    public CourseRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        courseDao = db.courseDao();
    }

    public void fetchInsertCourses(ApiInterface api) {
        Call<List<Course>> call = api.getCourses();
        Log.v("Course API Call", "Call done.");

        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.v("Course API Response Body", "Contains something");
                    try {
                       new Thread(() -> {
                           courseDao.insertCourses((response.body()));
                           Log.v("Insertion", "Course API insertions successful");
                       }).start();
                    }
                    catch(Exception e) {
                        Log.e("ERROR FROM FETCH", "" + e);
                    }
                }
                else {
                    Log.v("Course API Response Body", "Issues detected");
                }
            }
            public void onFailure(Call<List<Course>> call, Throwable t) {
                t.printStackTrace();
                Log.v("Course API Call Fail", "Failure", t);
            }
        });
    }
}
