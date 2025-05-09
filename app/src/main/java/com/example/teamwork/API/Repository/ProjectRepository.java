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

    public void fetchInsertProjects(ApiInterface api, int userId) {
        Call<List<Project>> call = api.getProjects(userId);
        Log.v("API Call", "Call done.");

        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                Log.v("Response", "Response received");
                if (response.isSuccessful() && response.body() != null) {
                    Log.v("Response Body", "Contains something");
                    try {
                        new Thread (() -> {
                            projectDao.insertProjects(response.body());
                            Log.v("Insertion", "Project insertions successful");
                        }).start();
                    }
                    catch (Exception e) {
                        Log.e("ERROR FROM FETCH", "" + e);
                    }
                }
                else {
                    Log.v("Response Body", "Issues detected");
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                t.printStackTrace();
                Log.v("Call Fail", "Failure", t);
            }
        });
    }

    private void insertProjects(List<Project> projects) {
        AsyncTask.execute(()-> projectDao.insertProjects(projects));
    }

    public LiveData<List<Project>> getAllProjects() {
        return projectDao.getAllProjects();
    }
}
