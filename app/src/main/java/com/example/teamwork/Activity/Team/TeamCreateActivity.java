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
            String name = String.valueOf(((EditText) findViewById(R.id.name)).getText());
            String description = String.valueOf(((EditText) findViewById(R.id.description)).getText());

            // TODO : Faire l'ajout par API et ensuite prendre les donne retourner afin de les mettre
            Team team = new Team(4, name, "Non conforme", description , projectId);
            AppDatabase db = AppDatabase.getDatabase(this);
            db.teamDao().insert(team);
            finish();
        }
    }
}