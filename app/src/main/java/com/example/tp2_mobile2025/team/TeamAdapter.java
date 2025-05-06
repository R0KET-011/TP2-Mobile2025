package com.example.tp2_mobile2025.team;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp2_mobile2025.R;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private final List<Team> teams;
    private final Context context;

    public TeamAdapter(Context context, List<Team> teams) {
        this.context = context;
        this.teams = teams;
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
        holder.size.setText(String.valueOf(team.getStudentsCount()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TeamShowActivity.class);
            intent.putExtra("team", team);
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

