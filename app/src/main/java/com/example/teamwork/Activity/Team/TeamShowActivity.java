package com.example.teamwork.Activity.Team;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.API.ApiClient;
import com.example.teamwork.API.ApiInterface;
import com.example.teamwork.API.Repository.TeamRepository;
import com.example.teamwork.Activity.Auth.Authentication;
import com.example.teamwork.Activity.Students.StudentListActivity;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Project;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.Database.Tables.TeamStudent;
import com.example.teamwork.R;

/****************************************
 Fichier : TeamShowActivity
 Auteur : Antoine Blouin
 Fonctionnalité : 32.2 ; 32.4 ; 32.5
 Date : 2025-05-05

 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date Nom Description
 =========================================================
 ****************************************/

/**
 * L'activité qui permet d'afficher les détails d'une équipe. C'est aussi à partir de cette vue qu'on peut
 * accéder à la liste des élèves.
 *
 * @author Antoine Blouin
 * @version 1.0
 * @since 2025-05-05
 */
public class TeamShowActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * L'équipe actuellement inspectée.
     */
    private Team team;

    /**
     * Le projet associé à l'équipe inspectée.
     */
    private Project project;

    /**
     * Instance de la base de données.
     */
    private AppDatabase db;

    /**
     * Les boutons du UI.
     */
    private Button deleteButton, editButton, joinButton, quitButton;
    private String authToken;


    /**
     * Constructeur de TeamShow qui initialise les variables de base.
     *
     * @param savedInstanceState Si l'activité est recréée après une fermeture,
     *                           ce Bundle contient les données précédemment enregistrées.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_show);

        int teamId = getIntent().getIntExtra("teamId", -1);
        authToken = getIntent().getStringExtra("authToken");

        db = AppDatabase.getDatabase(this);
        setupButtons();
        observeTeam(teamId);
    }

    /**
     * Va chercher les boutons dans le XML, les associe aux variables
     * et ajoute les listeners pour ceux qui n'ont pas de logique complexe.
     */
    private void setupButtons() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.students).setOnClickListener(this);

        deleteButton = findViewById(R.id.delete);
        editButton = findViewById(R.id.edit);
        joinButton = findViewById(R.id.join);
        quitButton = findViewById(R.id.quit);
    }

    /**
     * Observe les changements dans l'équipe actuelle et ajuste l'UI
     * si nécessaire. Si l'équipe n'existe plus, on ferme l'activité.
     *
     * @param teamId L'équipe concernée.
     */
    private void observeTeam(int teamId) {
        db.teamDao().getTeamById(teamId).observe(this, team -> {
            if (team == null) {
                finish();
                return;
            }

            this.team = team;
            observeProject(team.getProjectId());
        });
    }

    /**
     * Observe les changements dans le projet lié à l'équipe actuelle et ajuste l'UI.
     *
     * @param projectId L'ID du projet concerné.
     */
    private void observeProject(int projectId) {
        db.projectDao().getProjectById(projectId).observe(this, project -> {
            this.project = project;

            if (!project.getJoinable()){
                joinButton.setVisibility(View.GONE);
                quitButton.setVisibility(View.GONE);
            }

            roleBasedUI();
            showTeamInfos();
        });
    }


    /**
     * Affiche les boutons et l'UI différemment selon si l'utilisateur est professeur ou élève.
     */
    private void roleBasedUI() {
        if (Authentication.isStudent()) {
            hideTeacherButton();
            if (project.getJoinable())
                observeMembership();
        } else {
            hideStudentButton();
            teacherButtonListeners();
        }
    }

    /**
     * Cache les boutons réservés aux professeurs.
     */
    private void hideTeacherButton(){
        deleteButton.setVisibility(View.GONE);
        editButton.setVisibility(View.GONE);
    }

    /**
     * Cache les boutons réservés aux élèves.
     */
    private void hideStudentButton(){
        joinButton.setVisibility(View.GONE);
        quitButton.setVisibility(View.GONE);
    }

    /**
     * Ajoute les listeners aux boutons des professeurs.
     */
    private void teacherButtonListeners(){
        deleteButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
    }

    /**
     * Observe si l'élève fait partie de l'équipe et execute le code en conséquence.
     */
    private void observeMembership() {
        db.teamStudentDao().getStudentInTeam(team.getId(), Authentication.getId()).observe(this, membership -> {
            if (membership != null) {
                showQuitButton(membership);
            } else {
                showJoinButton();
            }
        });
    }

    /**
     * Affiche le bouton "Rejoindre" avec la logique associée.
     */
    private void showJoinButton() {
        joinButton.setVisibility(View.VISIBLE); // TODO : Ptit bug de timing ici (quand tu quite et que sa delete, sa affiche)
        quitButton.setVisibility(View.GONE);

        db.teamStudentDao().getStudentCountForTeamExcluding(team.getId(), Authentication.getId()).observe(this, count ->{
            if (count < project.getMax_per_team()){
                joinButtonAction();
            }else{
                quitButton.setVisibility(View.GONE);
                joinButton.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Définit l'action du bouton "Rejoindre une équipe".
     */
    private void joinButtonAction(){
        joinButton.setOnClickListener(v -> {
            TeamStudent teamStudent = new TeamStudent(team.getId(), Authentication.getId(), "");
            db.teamStudentDao().deleteStudentFromProject(Authentication.getId(), project.getId());
            db.teamStudentDao().insert(teamStudent);
        });
    }

    /**
     * Affiche le bouton "Quitter" avec la logique associée.
     *
     * @param membership L'objet représentant le lien entre l'élève et l'équipe.
     */
    private void showQuitButton(TeamStudent membership) {
        joinButton.setVisibility(View.GONE);
        quitButton.setVisibility(View.VISIBLE);

        quitButton.setOnClickListener(v -> {
            db.teamStudentDao().delete(membership);
            deleteTeamOnLastStudent();
        });
    }

    /**
     * Supprime l'équipe lorsqu'il n'y a plus d'élèves dedans.
     */
    private void deleteTeamOnLastStudent() {
        db.teamStudentDao().getStudentCountForTeam(team.getId()).observeForever(count -> {
            if (count != null && count < 1) {
                db.teamDao().delete(team);
            }
        });
    }

    /**
     * Affiche les informations de l'équipe actuelle.
     */
    private void showTeamInfos() {
        TextView name = findViewById(R.id.name);
        TextView state = findViewById(R.id.state);
        TextView size = findViewById(R.id.size);
        TextView description = findViewById(R.id.description);

        name.setText(team.getName());
        state.setText(team.getState());
        state.setTextColor(team.getStateColor(this));
        description.setText(team.getDescription());

        db.teamStudentDao().getStudentCountForTeam(team.getId()).observe(this, count -> {
            size.setText(String.format("%d/%d", count, project.getMax_per_team()));
        });
    }

    /**
     * Exécute les actions associées aux vues cliquées.
     *
     * @param v la vue cliquée
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        } else if (v.getId() == R.id.edit) {
            startEditActivity();
        } else if (v.getId() == R.id.students) {
            startStudentListActivity();
        } else if (v.getId() == R.id.delete) {
            db.teamDao().delete(team);

            int teamId = getIntent().getIntExtra("teamId", -1);
            ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
            TeamRepository repository = new TeamRepository(this);
            repository.sendDeleteTeam(api, teamId);

        }
    }

    /**
     * Lance l'activité pour modifier l'équipe actuelle.
     */
    private void startEditActivity() {
        Intent intent = new Intent(this, TeamEditActivity.class);
        intent.putExtra("teamId", team.getId());
        intent.putExtra("authToken", authToken);
        startActivity(intent);
    }

    /**
     * Lance l'activité pour voir les élèves de l'équipe actuelle.
     */
    private void startStudentListActivity() {
        Intent intent = new Intent(this, StudentListActivity.class);
        intent.putExtra("teamId", team.getId());
        startActivity(intent);
    }
}