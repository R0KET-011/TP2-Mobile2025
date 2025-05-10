package com.example.teamwork.Activity.Team;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.teamwork.Activity.Auth.Authentication;
import com.example.teamwork.Activity.Students.StudentListActivity;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Project;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.Database.Tables.TeamStudent;
import com.example.teamwork.R;

public class TeamShowActivity extends AppCompatActivity implements View.OnClickListener {

    private Team team;
    private Project project;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_show);

        Intent intent = this.getIntent();
        int teamId = intent.getIntExtra("teamId", -1);

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.students).setOnClickListener(this);

        db = AppDatabase.getDatabase(this);
        db.teamDao().getTeamById(teamId).observe(this, team -> {
            if (team == null) {
                finish();
                return;
            }
            this.team = team;

            db.projectDao().getProjectById(team.getProjectId()).observe(this, project -> {
                this.project = project;
                setInfos();
            });

            View deleteButton = findViewById(R.id.delete);
            View editButton = findViewById(R.id.edit);
            View joinButton = findViewById(R.id.join);
            View quitButton = findViewById(R.id.quit);

            if (Authentication.isStudent()) {
                deleteButton.setVisibility(View.GONE);
                editButton.setVisibility(View.GONE);

                db.teamStudentDao().getStudentInTeam(team.getId(), Authentication.getId())
                        .observe(this, teamStudent -> {
                            if (teamStudent != null) {
                                quitButton.setVisibility(View.VISIBLE);
                                joinButton.setVisibility(View.GONE);
                                quitButton.setOnClickListener(v1 -> {
                                    db.teamStudentDao().delete(teamStudent);
                                    db.teamStudentDao().getStudentCountForTeam(team.getId())
                                            .observeForever(count -> {
                                                if (count != null && count < 1) {
                                                    db.teamDao().delete(team);
                                                }
                                            });
                                    quitButton.setVisibility(View.GONE);
                                    joinButton.setVisibility(View.VISIBLE);
                                });
                            } else {
                                joinButton.setVisibility(View.VISIBLE);
                                quitButton.setVisibility(View.GONE);
                                joinButton.setOnClickListener(v1 -> {
                                    TeamStudent ts = new TeamStudent(team.getId(), Authentication.getId(), "");
                                    db.teamStudentDao().deleteStudentFromProject(Authentication.getId(), project.getId());
                                    db.teamStudentDao().insert(ts);
                                    joinButton.setVisibility(View.GONE);
                                    quitButton.setVisibility(View.VISIBLE);
                                });
                            }
                        });
            } else {
                joinButton.setVisibility(View.GONE);
                quitButton.setVisibility(View.GONE);
                editButton.setOnClickListener(this);
                deleteButton.setOnClickListener(this);
            }
        });
    }

    private void setInfos(){
        TextView name = findViewById(R.id.name);
        TextView state = findViewById(R.id.state);
        TextView size = findViewById(R.id.size);
        TextView description = findViewById(R.id.description);

        name.setText(team.getName());
        state.setText(team.getState());
        state.setTextColor(team.getStateColor(this));
        description.setText(team.getDescription());

        db.teamStudentDao().getStudentCountForTeam(team.getId()).observe(
                this, c -> {
                    size.setText(String.format("%d/%d", c, project.getMax_per_team()));
                }
        );
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        } else if (v.getId() == R.id.edit) {
            Intent intent = new Intent(this, TeamEditActivity.class);
            intent.putExtra("teamId", team.getId());
            startActivity(intent);
        } else if (v.getId() == R.id.students) {
            Intent intent = new Intent(this, StudentListActivity.class);
            intent.putExtra("teamId", team.getId());
            startActivity(intent);
        } else if (v.getId() == R.id.delete) {
            db.teamDao().delete(team);
        }
    }
}