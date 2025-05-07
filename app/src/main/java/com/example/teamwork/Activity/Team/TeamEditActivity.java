package com.example.teamwork.Activity.Team;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.R;

import java.util.List;

public class TeamEditActivity extends AppCompatActivity implements View.OnClickListener{

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
        appDatabase.teamDao().getTeamById(teamId).observe(
                this, (Team team) -> {
                    this.team = team;
                    setInfos();
                });;

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.confirm).setOnClickListener(this);
    }

    private void setInfos(){
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);

        name.setText(team.getName());
        description.setText(team.getDescription());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        } else if (v.getId() == R.id.confirm) {
            team.setName(name.getText().toString());
            team.setDescription(description.getText().toString());
            appDatabase.teamDao().update(team);
            finish();
        }
    }
}