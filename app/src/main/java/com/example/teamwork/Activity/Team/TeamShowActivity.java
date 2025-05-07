package com.example.teamwork.Activity.Team;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Project;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.R;

public class TeamShowActivity extends AppCompatActivity {

    private Team team;
    private int teamId;
    private Project project;
    private int studentCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_show);

        Intent intent = this.getIntent();
        teamId = intent.getIntExtra("teamId", -1);

        setInfos();
        setButtons();
    }

    private void setInfos(){
        AppDatabase appDatabase = AppDatabase.getDatabase(this);
        team = appDatabase.teamDao().getTeamById(teamId);
        project = appDatabase.projectDao().getProjectById(team.getProjectId());
        studentCount = appDatabase.teamStudentDao().getStudentCountForTeam(team.getId());

        TextView name = findViewById(R.id.name);
        TextView state = findViewById(R.id.state);
        TextView size = findViewById(R.id.size);
        TextView description = findViewById(R.id.description);

        name.setText(team.getName());
        state.setText(team.getState());
        state.setTextColor(team.getStateColor(this));
        size.setText(String.format("%d/%d", studentCount, project.getMaxPerTeam()));
        description.setText(team.getDescription());
    }

    private void setButtons(){
        findViewById(R.id.back).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.join).setOnClickListener(v -> {
        });

        findViewById(R.id.edit).setOnClickListener(v -> {
            Intent intent = new Intent(this, TeamEditActivity.class);
            intent.putExtra("teamId", team.getId());
            teamEditLauncher.launch(intent);
        });
    }

    private final ActivityResultLauncher<Intent> teamEditLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        setInfos();
                        setButtons();
                    });
}