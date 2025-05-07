package com.example.teamwork.Activity.Team;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.R;

import java.util.List;

public class TeamIndexActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_index);

        Intent intent = getIntent();
        int projectId = intent.getIntExtra("projectId",-1);

        AppDatabase appDatabase = AppDatabase.getDatabase(this);
        appDatabase.teamDao().getTeamsByProjectId(projectId).observe(
                this, (List<Team> teams) -> {
                    TeamAdapter adapter = new TeamAdapter(this, teams);
                    RecyclerView recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                });

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.create).setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        } else if (v.getId() == R.id.create) {
            Intent intent = new Intent(this, TeamCreateActivity.class);
            startActivity(intent);
        }
    }
}