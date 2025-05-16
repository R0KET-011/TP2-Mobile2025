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
import com.example.teamwork.Database.Tables.Course;
import com.example.teamwork.Database.Tables.Group;
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
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.teamwork.API.ApiClient;

import com.example.teamwork.Database.Tables.Project;
import com.google.gson.Gson;

/**
 * Project Activity qui liste les projets.
 */
public class ProjectActivity extends AppCompatActivity implements View.OnClickListener{
    /**
     * Instance de la base de donné.
     */
    private AppDatabase db;
    private RecyclerView recyclerView;
    String authToken = "1|G80mYnLHuB6b00i9SN9gjpRmWmhzbXiCu6zK5KxNfdffb5bc";
    int userId;
    /**
     * Texte view pour le titre de la view.
     */
    TextView titleView;
    /**
     * bouton + pour afficher la create view des projets.
     */
    ImageView createView;

    /**
     * Override onCreate, définit les différentes variables et prepare la vue.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        // Prepare views
        titleView = findViewById(R.id.projectTitle);
        createView = findViewById(R.id.add);
        findViewById(R.id.add).setOnClickListener(this);

        // Hides or Show certain buttons
        checkRole();
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
            updateGroupDatabase();
            updateGroupProjectDatabase();
            updateTeamStudentDatabase();
        });


        // Voulait mettre cela dans une fonction en bas mais ça marche pas. Ça fait que crasher.
        if (Authentication.isStudent()) {
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
                            ProjectAdapter projectAdapter = new ProjectAdapter(this, projects, authToken);
                            recyclerView.setAdapter(projectAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        }
                        catch(Exception e) {
                            Log.d("RecycleView ERROR", " returned: " + e);
                        }
                    });
        }
        else {
            db.projectDao().getAllProjects().observe(
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
                            ProjectAdapter projectAdapter = new ProjectAdapter(this, projects, authToken);
                            recyclerView.setAdapter(projectAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        }
                        catch(Exception e) {
                            Log.d("RecycleView ERROR", " returned: " + e);
                        }
                    });
        }

    }

    /**
     * Check si l'utilisateur est un professeur ou étudiant et afficher quelque chose en conséquence.
     */
    public void checkRole() {
        if (Authentication.isStudent()) {
            createView.setVisibility(ImageView.GONE);
        } else {
            //
        }
    }

    /**
     * Défini les onClickListener des boutons.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add) {
            Intent intent = new Intent(ProjectActivity.this, ProjectCreate.class);
            db = AppDatabase.getDatabase(this);

            // Send all groups to Create Project Activity
            List<Group> groups = db.groupDao().getAllGroups();
            Gson gson = new Gson();
            String toJson = gson.toJson(groups);
            Log.v("JSON TEST", toJson);
            intent.putExtra("groups", toJson);

            // Send all courses to Create Project Activity
            List<Course> courses = db.courseDao().getAllCourses();
            toJson = gson.toJson(courses);
            intent.putExtra("courses", toJson);

            // auith token
            intent.putExtra("authToken", authToken);

            startActivity(intent);
        }
    }

    /**
     * Update les projets de la base de donnée avec les info de la base de donné web.
     */
    public void updateProjectDatabase() {
        ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
        ProjectRepository repository = new ProjectRepository(this);
        repository.fetchInsertProjects(api);
    }

    /**
     * Update les élèves de la base de donné avec les info de la base de donné web.
     */
    public void updateStudentDatabase() {
        ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
        StudentRepository repository = new StudentRepository(this);
        repository.fetchInsertStudents(api);
    }

    /**
     * Update les équipes de la base de donné avec les info de la base de donné web.
     */
    public void updateTeamDatabase() {
        ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
        TeamRepository repository = new TeamRepository(this);
        repository.fetchInsertTeams(api);
    }

    /**
     * Update les cours de la base de donné avec les info de la base de donné web.
     */
    public void updateCourseDatabase() {
      ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
        CourseRepository repository = new CourseRepository(this);
        repository.fetchInsertCourses(api);
    }

    /**
     * Update la table pivot TeamStudent avec les info de la base de donné web.
     */
    public void updateTeamStudentDatabase() {
        ApiInterface api = ApiClient.getClient(authToken).create((ApiInterface.class));
        TeamStudentRepository repository = new TeamStudentRepository(this);
        repository.fetchInsertTeamStudent(api);
    }

    /**
     * Update les groups avec les info de la base de donné web.
     */
    public void updateGroupDatabase() {
        ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
        GroupRepository repository = new GroupRepository(this);
        repository.fetchInsertGroups(api);
    }

    /**
     * Update la table pivot GroupProject avec les info de la base de donné web.
     */
    public void updateGroupProjectDatabase() {
        ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
        GroupProjectRepository repository = new GroupProjectRepository(this);
        repository.fetchInsertGroupProject(api);
    }

}