package com.example.teamwork.Activity.Team;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.R;

public class TeamCreateActivity extends AppCompatActivity implements View.OnClickListener {

    private int projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_create);

        Intent intent = getIntent();
        projectId = intent.getIntExtra("projectId",-1);

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.create).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        } else if (v.getId() == R.id.create) {
            EditText nameEditText = findViewById(R.id.name);
            EditText descriptionEditText = findViewById(R.id.description);

            String name = nameEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();

            if (name.isEmpty()) {
                nameEditText.setError("Le nom est requis");
                nameEditText.requestFocus();
                return;
            }
            AppDatabase db = AppDatabase.getDatabase(this);
            if (db.teamDao().isNameTaken(name, projectId) > 0){
                nameEditText.setError("Le nom est déjà pris");
                nameEditText.requestFocus();
                return;
            }

            if (description.isEmpty()) {
                descriptionEditText.setError("Une description est requise");
                descriptionEditText.requestFocus();
                return;
            }
            // TODO : Validation par API et Pour l'Id
            Team team = new Team(4, name, "Non conforme", description , projectId);
            db.teamDao().insert(team);
            finish();
        }
    }
}