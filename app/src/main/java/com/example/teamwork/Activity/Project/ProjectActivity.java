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
import com.example.teamwork.API.Repository.CourseRepository;
import com.example.teamwork.API.Repository.GroupProjectRepository;
import com.example.teamwork.API.Repository.GroupRepository;
import com.example.teamwork.API.Repository.ProjectRepository;
import com.example.teamwork.API.Repository.StudentRepository;
import com.example.teamwork.API.Repository.TeamRepository;
import com.example.teamwork.API.Repository.TeamStudentRepository;
import com.example.teamwork.Activity.Auth.Authentication;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.Database.Tables.TeamStudent;
import com.example.teamwork.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.teamwork.API.ApiClient;

import com.example.teamwork.Database.Tables.Project;

public class ProjectActivity extends AppCompatActivity implements View.OnClickListener{

    private AppDatabase db;
    private RecyclerView recyclerView;
    String authToken = "1|o3jMkGcucX5pzzSybbhWZaXy2P0axZzYGPqlzxIf434977a3";
    int userId;
    TextView titleView;
    ImageView createView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        // Prepare views
        titleView = findViewById(R.id.projectTitle);
        createView = findViewById(R.id.add);
        findViewById(R.id.add).setOnClickListener(this);

        // Hides or Show certain buttons
        //checkRole();
        userId = Authentication.getId();

        // Prepare for database manipulation
        // To be moved to activity between login and project with loading bar.
        // Also when sync button pressed.
        // Need to wipe database everytime somehow.
        AppDatabase db = AppDatabase.getDatabase(this);

        // Au cas où les inserts sont async somehow.. aucune idée c'est quoi le problème tbh..
        // Commenter si dessous pour prévenir l'appel API
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(()-> {
            updateProjectDatabase();
            updateStudentDatabase();
            updateTeamDatabase();
            updateCourseDatabase();
            }
        );

        executor.execute(() -> {
            updateTeamStudentDatabase();
            updateGroupDatabase();
            updateGroupProjectDatabase();
        });


        // Voulait mettre cela dans une fonction en bas mais ça marche pas. Ça fait que crasher.
        db.projectDao().getProjectByUser(userId).observe(
                this, projects -> {
                    try {
                        if (projects.isEmpty()) {
                            Log.d("projects", "Null");
                            titleView.setText(R.string.project_notitle);
                        }
                        else {
                            titleView.setText(R.string.project_title);
                        }
                        RecyclerView recyclerView = findViewById(R.id.recyclerView);
                        ProjectAdapter projectAdapter = new ProjectAdapter(this, projects);
                        recyclerView.setAdapter(projectAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    }
                    catch(Exception e) {
                        Log.d("RecycleView ERROR", " returned: " + e);
                    }
                });

    }

    public void checkRole() {
        if (Authentication.isStudent()) {
            createView.setVisibility(ImageView.GONE);
        } else {
            //
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add) {
            Intent intent = new Intent(ProjectActivity.this, ProjectCreate.class);
            startActivity(intent);
        }
    }

    public void updateProjectDatabase() {
        ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
        ProjectRepository repository = new ProjectRepository(this);
        repository.fetchInsertProjects(api);
    }

    public void updateStudentDatabase() {
        ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
        StudentRepository repository = new StudentRepository(this);
        repository.fetchInsertStudents(api);
    }

    public void updateTeamDatabase() {
        ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
        TeamRepository repository = new TeamRepository(this);
        repository.fetchInsertTeams(api);
    }

    public void updateCourseDatabase() {
      ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
        CourseRepository repository = new CourseRepository(this);
        repository.fetchInsertCourses(api);
    }

    public void updateTeamStudentDatabase() {
        ApiInterface api = ApiClient.getClient(authToken).create((ApiInterface.class));
        TeamStudentRepository repository = new TeamStudentRepository(this);
        repository.fetchInsertTeamStudent(api);
    }

    public void updateGroupDatabase() {
        ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
        GroupRepository repository = new GroupRepository(this);
        repository.fetchInsertGroups(api);
    }

    public void updateGroupProjectDatabase() {
        ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
        GroupProjectRepository repository = new GroupProjectRepository(this);
        repository.fetchInsertGroupProject(api);
    }

}