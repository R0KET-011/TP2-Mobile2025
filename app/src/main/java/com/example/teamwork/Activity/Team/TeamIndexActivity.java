package com.example.teamwork.Activity.Team;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamwork.API.ApiClient;
import com.example.teamwork.API.ApiInterface;
import com.example.teamwork.API.Repository.ProjectRepository;
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

/**
 * L'activité qui permet d'afficher toutes les équipes et les informations du projet courant.
 *
 * @author Antoine Blouin
 * @version 1.0
 * @since 2025-05-05
 */
public class TeamIndexActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Le projet en lien avec les équipes.
     */
    private Project project;

    /**
     * Instance de la base de données.
     */
    private AppDatabase db;

    /**
     * La vue contenant la description du projet.
     */
    private TextView descriptionTextView;

    /**
     * Le RecyclerView qui contient toutes les équipes du projet.
     */
    private RecyclerView recyclerView;

    /** Authentification Token */
    private String authToken;

    /**
     * Constructeur de TeamIndex qui initialise les variables de base.
     *
     * @param savedInstanceState Si l'activité est recréée après une fermeture,
     *                           ce Bundle contient les données précédemment enregistrées.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_index);

        int projectId = getIntent().getIntExtra("projectId",-1);
        authToken = getIntent().getStringExtra("authToken");

        db = AppDatabase.getDatabase(this);

        setupView();
        setupButtons();
        observeProject(projectId);
    }

    /**
     * Permet de définir les vues en les associant aux variables via les IDs du fichier XML.
     */
    private void setupView(){
        descriptionTextView = findViewById(R.id.description);
        recyclerView = findViewById(R.id.recyclerView);
    }

    /**
     * Définit la visibilité des boutons selon le type d'utilisateur (élève ou non).
     * Ajoute également les listeners.
     */
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

    /**
     * Observe le projet et met à jour l'interface graphique lorsqu'il y a un changement dans la BD.
     *
     * @param projectId l'ID du projet à observer
     */
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

    /**
     * Configure le RecyclerView et observe la liste des équipes.
     * Lorsqu’un changement est détecté, le RecyclerView est mis à jour pour refléter les données.
     *
     * @param projectId l'ID du projet courant
     */
    private void setupRecyclerView(int projectId){
        db.teamDao().getTeamsByProjectId(projectId).observe(this, teams -> {
            TeamAdapter adapter = new TeamAdapter(this, teams, project, db, authToken);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        });
    }

    /**
     * Exécute les actions associées aux vues cliquées.
     *
     * @param v la vue cliquée
     */
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

    /**
     * La logique pour permettre la suppression du projet courant.
     */
    private void deleteProject(){
        db.projectDao().delete(project);

        ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
        ProjectRepository repository = new ProjectRepository(this);
        repository.sendDeleteProject(api, project.getId());
    }

    /**
     * Lance l'activité permettant de modifier le projet actuel.
     */
    private void startProjectEditActivity(){
        Intent intent = new Intent(this, ProjectEditActivity.class);
        intent.putExtra("projectId", project.getId());
        intent.putExtra("authToken", authToken);
        startActivity(intent);
    }

    /**
     * Lance l'activité permettant de créer une nouvelle équipe.
     */
    private void startTeamCreateActivity(){
        Intent intent = new Intent(this, TeamCreateActivity.class);
        intent.putExtra("projectId", project.getId());
        intent.putExtra("authToken", authToken);
        startActivity(intent);
    }
}