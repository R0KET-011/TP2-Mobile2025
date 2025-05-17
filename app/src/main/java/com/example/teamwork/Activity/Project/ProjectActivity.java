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
import com.example.teamwork.MenuHelper.BaseActivity;
import com.example.teamwork.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
public class ProjectActivity extends BaseActivity{
    /**
     * Instance de la base de donné.
     */
    private AppDatabase db;
    private RecyclerView recyclerView;
    String authToken = "1|G80mYnLHuB6b00i9SN9gjpRmWmhzbXiCu6zK5KxNfdffb5bc";
    int userId;
    /**
     * Toolbar qui fait le titre de la vue.
     */
    Toolbar toolbar;
    /**
     * bouton + pour afficher la create view des projets.
     */

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
        //set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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
                                toolbar.setTitle(R.string.project_notitle);
                            }
                            else {
                                toolbar.setTitle(R.string.project_title);
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
                                toolbar.setTitle(R.string.project_notitle);
                            }
                            else {
                                toolbar.setTitle(R.string.project_title);
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

    /**
     * Add des options au menu existant.
     * @param item The menu item that was selected.
     *
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_create) {
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
        return super.onOptionsItemSelected(item);
    }

    /**
     * Override onCreateOptionMenu pour set le menu
     * @param menu The options menu in which you place your items.
     *
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header_menu, menu);
        if (!Authentication.isStudent()) {
            menu.findItem(R.id.menu_create).setVisible(true);
        }
        return true;
    }
}