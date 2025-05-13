package com.example.teamwork.Activity.Team;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamwork.Activity.Auth.Authentication;
import com.example.teamwork.Activity.Project.ProjectEditActivity;
import com.example.teamwork.Activity.ToDo.TodoIndexActivity;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Project;
import com.example.teamwork.R;

/****************************************
 Fichier : TeamIndexActivity
 Auteur : Antoine Blouin
 Fonctionnalité : 32.5
 Date : 2025-05-05

 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date Nom Description
 =========================================================
 ****************************************/

public class TeamIndexActivity extends AppCompatActivity implements View.OnClickListener {

    private Project project;
    private AppDatabase db;
    private TextView descriptionTextView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_index);

        int projectId = getIntent().getIntExtra("projectId",-1);

        db = AppDatabase.getDatabase(this);

        setupView();
        setupButtons();
        observeProject(projectId);
    }

    private void setupView(){
        descriptionTextView = findViewById(R.id.description);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setupButtons() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.create).setOnClickListener(this);
        findViewById(R.id.todo).setOnClickListener(this);

        if (Authentication.isStudent()){
            findViewById(R.id.project_delete).setVisibility(View.GONE);
            findViewById(R.id.project_edit).setVisibility(View.GONE);
        }else{
            findViewById(R.id.project_delete).setOnClickListener(this);
            findViewById(R.id.project_edit).setOnClickListener(this);
        }

    }

    public void observeProject(int projectId){
        db.projectDao().getProjectById(projectId).observe(this, project -> {
            if (project == null){
                finish();
                return;
            }

            if (!project.getCreatable())
                findViewById(R.id.create).setVisibility(View.GONE);

            this.project = project;
            descriptionTextView.setText(project.getDescription());
            setupRecyclerView(project.getId());
        });
    }

    private void setupRecyclerView(int projectId){
        db.teamDao().getTeamsByProjectId(projectId).observe(this, teams -> {
            TeamAdapter adapter = new TeamAdapter(this, teams, project, db);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        });
    }

    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        } else if (v.getId() == R.id.create) {
            startTeamCreateActivity();
        } else if (v.getId() == R.id.project_delete){
            deleteProject();
        } else if (v.getId() == R.id.project_edit){
            startProjectEditActivity();
        }
        else if (v.getId() == R.id.todo) {
            startActivity(new Intent(TeamIndexActivity.this, TodoIndexActivity.class));
        }
    }

    private void deleteProject(){

    }
    private void startProjectEditActivity(){
        Intent intent = new Intent(this, ProjectEditActivity.class);
        intent.putExtra("projectId", project.getId());
        startActivity(intent);
    }
    private void startTeamCreateActivity(){
        Intent intent = new Intent(this, TeamCreateActivity.class);
        intent.putExtra("projectId", project.getId());
        startActivity(intent);
    }
}