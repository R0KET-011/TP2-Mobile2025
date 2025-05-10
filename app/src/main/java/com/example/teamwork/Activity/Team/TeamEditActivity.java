package com.example.teamwork.Activity.Team;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.R;

/****************************************
 Fichier : TeamCreateActivity
 Auteur : Antoine Blouin
 Fonctionnalité : 32.3
 Date : 2025-05-05

 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date Nom Description
 =========================================================
 ****************************************/

public class TeamEditActivity extends AppCompatActivity implements View.OnClickListener{

    private Team team;
    private EditText nameEditText, descriptionEditText;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_edit);

        int teamId = getIntent().getIntExtra("teamId", -1);
        db = AppDatabase.getDatabase(this);

        observeTeam(teamId);
    }

    private void observeTeam(int teamId){
        db.teamDao().getTeamById(teamId).observe(this, team -> {
            this.team = team;
            setViews();
        });
    }

    private void setViews(){
        nameEditText = findViewById(R.id.name);
        descriptionEditText = findViewById(R.id.description);

        nameEditText.setText(team.getName());
        descriptionEditText.setText(team.getDescription());

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.confirm).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        } else if (v.getId() == R.id.confirm) {
            submitEdit();
        }
    }

    private void submitEdit(){
        String name = nameEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (!validateInputs(name, description)) return;

        team.setName(name);
        team.setDescription(description);
        db.teamDao().update(team);
        finish();
    }

    private boolean validateInputs(String name, String description) {
        if (name.isEmpty()) {
            nameEditText.setError("Le nom est requis");
            nameEditText.requestFocus();
            return false;
        }

        if (db.teamDao().isNameTakenEdit(name, team.getProjectId(), team.getId()) > 0) {
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