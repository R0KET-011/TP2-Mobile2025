package com.example.teamwork.Activity.Team;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.R;

public class TeamEditActivity extends AppCompatActivity {

    private Team team;
    TextView name, description;
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_edit);

        Intent intent = this.getIntent();
        int teamId = intent.getIntExtra("teamId", -1);

        appDatabase = AppDatabase.getDatabase(this);
        team = appDatabase.teamDao().getTeamById(teamId);

        setInfos();
        setButtons();
    }

    private void setInfos(){
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);

        name.setText(team.getName());
        description.setText(team.getDescription());
    }

    private void setButtons(){
        findViewById(R.id.back).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.confirm).setOnClickListener(v -> {
            team.setName(name.getText().toString());
            team.setDescription(description.getText().toString());
            appDatabase.teamDao().update(team);
            finish();
        });
    }
}