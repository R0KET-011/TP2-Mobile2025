package com.example.teamwork.Activity.Team;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.R;

import java.util.List;

public class TeamIndexActivity extends AppCompatActivity {

    private int projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_index);

        Intent intent = getIntent();
        projectId = intent.getIntExtra("projectId",1);

        setInfos();
        setButtons();
    }

    private void setInfos(){
        AppDatabase appDatabase = AppDatabase.getDatabase(this);
        List<Team> teams = appDatabase.teamDao().getTeamsByProjectId(projectId);
        int maxPerTeam = appDatabase.projectDao().getProjectById(projectId).getMaxPerTeam();

        TeamAdapter adapter = new TeamAdapter(this, teams, maxPerTeam, teamShowLauncher);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setButtons(){
        findViewById(R.id.back).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.create).setOnClickListener(v -> {
            Intent intent = new Intent(this, TeamCreateActivity.class);
            teamCreateLauncher.launch(intent);
        });
    }

    private final ActivityResultLauncher<Intent> teamShowLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                            setInfos();
                    });

    private final ActivityResultLauncher<Intent> teamCreateLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        setInfos();
                    });
}