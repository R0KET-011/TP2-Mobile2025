package com.example.teamwork.Activity.Team;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Project;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private final List<Team> teams;
    private final Project project;
    private final Context context;
    private final  AppDatabase db;
    private final Map<Integer, Integer> studentCounts = new HashMap<>();

    public TeamAdapter(Context context, List<Team> teams, Project project, AppDatabase db) {
        this.context = context;
        this.teams = teams;
        this.project = project;
        this.db = db;
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

        holder.name.setText(team.getName());
        holder.state.setText(team.getState());
        holder.state.setTextColor(team.getStateColor(context));

        Integer count = studentCounts.get(team.getId());
        if (count != null) {
            holder.size.setText(String.format("%d/%d", count, project.getMax_per_team()));
        } else {
            db.teamStudentDao().getStudentCountForTeam(team.getId()).observe(
                    (LifecycleOwner) context, c -> {
                        studentCounts.put(team.getId(), c);
                        holder.size.setText(String.format("%d/%d", c, project.getMax_per_team()));
                    }
            );
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TeamShowActivity.class);
            intent.putExtra("teamId", team.getId());
            context.startActivity(intent);
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

