package com.example.teamwork.Activity.Team;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.Activity.Auth.Authentication;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.Database.Tables.TeamStudent;
import com.example.teamwork.R;

/****************************************
 Fichier : TeamCreateActivity
 Auteur : Antoine Blouin
 Fonctionnalité : 32.1
 Date : 2025-05-05

 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date Nom Description
 =========================================================
 ****************************************/

public class TeamCreateActivity extends AppCompatActivity implements View.OnClickListener {

    private int projectId;
    private EditText nameEditText, descriptionEditText;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_create);

        projectId = getIntent().getIntExtra("projectId",-1);
        db = AppDatabase.getDatabase(this);

        setViews();
    }

    private void setViews(){
        nameEditText = findViewById(R.id.name);
        descriptionEditText = findViewById(R.id.description);

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.create).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        } else if (v.getId() == R.id.create) {
            submitCreate();
        }
    }

    private void submitCreate(){
        String name = nameEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (!validateInputs(name, description)) return;

        // TODO : Validation par API et Pour l'Id
        Team team = new Team(4, name, "Non conforme", description , projectId);
        db.teamDao().insert(team);

        if (Authentication.isStudent())
            addStudentToTeam(team);

        finish();
    }

    private void addStudentToTeam(Team team){
        TeamStudent teamStudent = new TeamStudent(team.getId(), Authentication.getId(), "");
        db.teamStudentDao().deleteStudentFromProject(Authentication.getId(), projectId);
        db.teamStudentDao().insert(teamStudent);
    }

    private boolean validateInputs(String name, String description) {
        if (name.isEmpty()) {
            nameEditText.setError("Le nom est requis");
            nameEditText.requestFocus();
            return false;
        }

        if (db.teamDao().isNameTaken(name, projectId) > 0){
            nameEditText.setError("Le nom est déjà pris");
            nameEditText.requestFocus();
            return false;
        }

        if (description.isEmpty()) {
            descriptionEditText.setError("Une description est requise");
            descriptionEditText.requestFocus();
            return false;
        }

        return true;
    }
}