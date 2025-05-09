package com.example.teamwork.Activity.Project;

import com.example.teamwork.API.ApiInterface;
import com.example.teamwork.API.Repository.ProjectRepository;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.R;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.teamwork.API.ApiClient;

import com.example.teamwork.Database.Tables.Project;

public class ProjectActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    String authToken = "17|h3VKexmk1JjcUQGMalCr6mv7weguQcnL4jyayNgYb2a61f99";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        AppDatabase db = AppDatabase.getDatabase(this);
        updateDatabase(db);

        db.projectDao().getAllProjects().observe(
                this, (List<Project> projects) -> {
                    try {
                        Log.d("test de projet: ", projects.get(0).getName());
                        RecyclerView recyclerView = findViewById(R.id.recyclerView);
                        ProjectAdapter projectAdapter = new ProjectAdapter(this, projects);
                        recyclerView.setAdapter(projectAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    }
                    catch(Exception e) {
                        Log.d("RecycleView ERROR", "onCreate() returned: " + e);
                    }
                });

    }

    public void updateDatabase(AppDatabase db) {
        ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
        ProjectRepository repository = new ProjectRepository(this);
        repository.fetchInsertProjects(api, 1);

        new Handler().postDelayed(() ->{
            repository.getAllProjects().observe(this, projects -> {
                for (Project p: projects) {
                    Log.d("MainActivity", "Project: " + p.getName());
                }
            });
        }, 5000);
    }
}