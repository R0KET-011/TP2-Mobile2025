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

    private final Context context;
    private final List<Team> teams;
    private final Project project;
    private final AppDatabase db;
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
        holder.bind(teams.get(position));
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    class TeamViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView, stateTextView, sizeTextView;

        public TeamViewHolder(View team_item) {
            super(team_item);
            nameTextView = team_item.findViewById(R.id.name);
            stateTextView = team_item.findViewById(R.id.state);
            sizeTextView = team_item.findViewById(R.id.size);
        }

        public void bind(Team team){
            nameTextView.setText(team.getName());
            stateTextView.setText(team.getState());
            stateTextView.setTextColor(team.getStateColor(context));

            Integer count = studentCounts.get(team.getId());
            if (count != null)
                setSizeText(count);
            else
                observeStudentCounts(team);

            setClickListener(team);
        }

        private void observeStudentCounts(Team team){
            db.teamStudentDao().getStudentCountForTeam(team.getId())
                    .observe((LifecycleOwner) context, count -> {
                        studentCounts.put(team.getId(), count);
                        setSizeText(count);
                    });
        }

        private void setSizeText(int count) {
            sizeTextView.setText(String.format("%d/%d", count, project.getMax_per_team()));
        }

        private void setClickListener(Team team) {
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, TeamShowActivity.class);
                intent.putExtra("teamId", team.getId());
                context.startActivity(intent);
            });
        }
    }
}

