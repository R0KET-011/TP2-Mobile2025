package com.example.teamwork.Activity.Team;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class TeamShowActivity extends AppCompatActivity implements View.OnClickListener {

    private Team team;
    private Project project;
    private AppDatabase db;
    private View deleteButton, editButton, joinButton, quitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_show);

        int teamId = getIntent().getIntExtra("teamId", -1);

        db = AppDatabase.getDatabase(this);
        setupButtons();
        observeTeam(teamId);
    }

    private void setupButtons() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.students).setOnClickListener(this);

        deleteButton = findViewById(R.id.delete);
        editButton = findViewById(R.id.edit);
        joinButton = findViewById(R.id.join);
        quitButton = findViewById(R.id.quit);
    }

    private void observeTeam(int teamId) {
        db.teamDao().getTeamById(teamId).observe(this, team -> {
            if (team == null) {
                finish();
                return;
            }

            this.team = team;
            observeProject(team.getProjectId());
            roleBasedUI();
        });
    }

    private void observeProject(int projectId) {
        db.projectDao().getProjectById(projectId).observe(this, project -> {
            this.project = project;
            showTeamInfos();
        });
    }

    private void roleBasedUI() {
        if (Authentication.isStudent()) {
            hideTeacherButton();
            observeMembership();
        } else {
            hideStudentButton();
            teacherButtonListeners();
        }
    }

    private void hideTeacherButton(){
        deleteButton.setVisibility(View.GONE);
        editButton.setVisibility(View.GONE);
    }

    private void hideStudentButton(){
        joinButton.setVisibility(View.GONE);
        quitButton.setVisibility(View.GONE);
    }

    private void teacherButtonListeners(){
        deleteButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
    }

    private void observeMembership() {
        db.teamStudentDao().getStudentInTeam(team.getId(), Authentication.getId()).observe(this, membership -> {
            if (membership != null) {
                showQuitButton(membership);
            } else {
                showJoinButton();
            }
        });
    }

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

    private void joinButtonAction(){
        joinButton.setOnClickListener(v -> {
            TeamStudent teamStudent = new TeamStudent(team.getId(), Authentication.getId(), "");
            db.teamStudentDao().deleteStudentFromProject(Authentication.getId(), project.getId());
            db.teamStudentDao().insert(teamStudent);
        });
    }

    private void showQuitButton(TeamStudent membership) {
        joinButton.setVisibility(View.GONE);
        quitButton.setVisibility(View.VISIBLE);

        quitButton.setOnClickListener(v -> {
            db.teamStudentDao().delete(membership);
            deleteTeamOnLastStudent();
        });
    }

    private void deleteTeamOnLastStudent() {
        db.teamStudentDao().getStudentCountForTeam(team.getId()).observeForever(count -> {
            if (count != null && count < 1) {
                db.teamDao().delete(team);
            }
        });
    }

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
        }
    }

    private void startEditActivity() {
        Intent intent = new Intent(this, TeamEditActivity.class);
        intent.putExtra("teamId", team.getId());
        startActivity(intent);
    }

    private void startStudentListActivity() {
        Intent intent = new Intent(this, StudentListActivity.class);
        intent.putExtra("teamId", team.getId());
        startActivity(intent);
    }
}