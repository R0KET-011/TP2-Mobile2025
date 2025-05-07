package com.example.teamwork.Activity.Team;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.AppDatabase_Impl;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.R;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private final List<Team> teams;
    private final int maxPerTeam;
    private final AppDatabase appDatabase;
    private final Context context;
    ActivityResultLauncher<Intent> launcher;

    public TeamAdapter(Context context, List<Team> teams, int maxPerTeam, ActivityResultLauncher<Intent> launcher) {
        this.context = context;
        this.launcher = launcher;
        this.appDatabase = AppDatabase.getDatabase(context);
        this.teams = teams;
        this.maxPerTeam = maxPerTeam;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.team_item, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        Team team = teams.get(position);
        int studentCount = appDatabase.teamStudentDao().getStudentCountForTeam(team.getId());

        holder.name.setText(team.getName());
        holder.state.setText(team.getState());
        holder.state.setTextColor(team.getStateColor(context));
        holder.size.setText(String.format("%d/%d", studentCount, maxPerTeam));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TeamShowActivity.class);
            intent.putExtra("teamId", team.getId());
            launcher.launch(intent);
        });
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, state, size;

        public TeamViewHolder(View team_item) {
            super(team_item);
            name = team_item.findViewById(R.id.name);
            state = team_item.findViewById(R.id.state);
            size = team_item.findViewById(R.id.size);
        }
    }
}

