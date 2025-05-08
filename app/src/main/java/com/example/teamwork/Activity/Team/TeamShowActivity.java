package com.example.teamwork.Activity.Team;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.Activity.Students.StudentListActivity;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.R;

public class TeamShowActivity extends AppCompatActivity implements View.OnClickListener {

    private Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_show);

        Intent intent = this.getIntent();
        int teamId = intent.getIntExtra("teamId", -1);

        AppDatabase appDatabase = AppDatabase.getDatabase(this);
        appDatabase.teamDao().getTeamById(teamId).observe(
                this, (Team team) -> {
                    this.team = team;
                    setInfos();
                });

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.join).setOnClickListener(this);
        findViewById(R.id.edit).setOnClickListener(this);
        findViewById(R.id.students).setOnClickListener(this);
    }

    private void setInfos(){
        TextView name = findViewById(R.id.name);
        TextView state = findViewById(R.id.state);
        TextView size = findViewById(R.id.size);
        TextView description = findViewById(R.id.description);

        name.setText(team.getName());
        state.setText(team.getState());
        state.setTextColor(team.getStateColor(this));
        size.setText(String.format("%d/%d", 3, 4));
        description.setText(team.getDescription());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        } else if (v.getId() == R.id.join) {
            finish();
        } else if (v.getId() == R.id.edit) {
            Intent intent = new Intent(this, TeamEditActivity.class);
            intent.putExtra("teamId", team.getId());
            startActivity(intent);
        }
        else if (v.getId() == R.id.students) {
            Intent intent = new Intent(this, StudentListActivity.class);
            intent.putExtra("teamId", team.getId());
            startActivity(intent);
        }
    }
}