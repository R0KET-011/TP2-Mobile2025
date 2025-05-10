/****************************************
 Fichier : ProjectActivity.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 View de la liste des projets.

 Date : 05/05/2025

 Vérification :
 Date Nom Approuvé

 =========================================================
 Historique de modifications :
 Date Nom Description

 =========================================================
 ****************************************/

package com.example.teamwork.Activity.Project;

import com.example.teamwork.API.ApiInterface;
import com.example.teamwork.API.Repository.ProjectRepository;
import com.example.teamwork.API.Repository.TeamRepository;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.R;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.teamwork.API.ApiClient;

import com.example.teamwork.Database.Tables.Project;

public class ProjectActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    String authToken = "17|h3VKexmk1JjcUQGMalCr6mv7weguQcnL4jyayNgYb2a61f99";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_project);

        AppDatabase db = AppDatabase.getDatabase(this);

        // Au cas où les inserts sont async somehow.. aucune idée c'est quoi le problème tbh..
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()-> {
            updateProjectDatabase(db);
            updateTeamDatabase(db);
            }
        );

        db.projectDao().getAllProjects().observe(
                this, (List<Project> projects) -> {
                try {
                    RecyclerView recyclerView = findViewById(R.id.recyclerView);
                    ProjectAdapter projectAdapter = new ProjectAdapter(this, projects);
                    recyclerView.setAdapter(projectAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                }
                catch(Exception e) {
                    Log.d("RecycleView ERROR", "onCreate() returned: " + e);
                }
            });

        db.teamDao().getAllTeams().observe(this, (List<Team> teams) -> {
            for (Team team: teams) {
                try {
                    Log.d("test d'équipe: ", team.getName());
                }
                catch(Exception e) {
                    Log.d("test bad", "no team");
                }

            }

        });

    }

    public void updateProjectDatabase(AppDatabase db) {
        ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
        ProjectRepository repository = new ProjectRepository(this);
        repository.fetchInsertProjects(api);
    }

    public void updateTeamDatabase(AppDatabase db) {
        ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
        TeamRepository repository = new TeamRepository(this);
        repository.fetchInsertTeams(api);
    }
}