package com.example.teamwork.Activity.Team;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.R;


public class TeamEditActivity extends AppCompatActivity implements View.OnClickListener{

    private Team team;
    TextView nameEditText, descriptionEditText;
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_edit);

        Intent intent = this.getIntent();
        int teamId = intent.getIntExtra("teamId", -1);

        appDatabase = AppDatabase.getDatabase(this);
        appDatabase.teamDao().getTeamById(teamId).observe(
                this, (Team team) -> {
                    this.team = team;
                    setInfos();
                });

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.confirm).setOnClickListener(this);
    }

    private void setInfos(){
        nameEditText = findViewById(R.id.name);
        descriptionEditText = findViewById(R.id.description);

        nameEditText.setText(team.getName());
        descriptionEditText.setText(team.getDescription());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        } else if (v.getId() == R.id.confirm) {
            String name = nameEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();

            if (name.isEmpty()) {
                nameEditText.setError("Le nom est requis");
                nameEditText.requestFocus();
                return;
            }
            AppDatabase db = AppDatabase.getDatabase(this);
            if (db.teamDao().isNameTakenEdit(name, team.getProjectId(), team.getId()) > 0) {
                nameEditText.setError("Le nom est déjà pris");
                nameEditText.requestFocus();
                return;
            }

            if (description.isEmpty()) {
                descriptionEditText.setError("Une description est requise");
                descriptionEditText.requestFocus();
                return;
            }

            team.setName(name);
            team.setDescription(description);
            appDatabase.teamDao().update(team);
            finish();
        }
    }
}