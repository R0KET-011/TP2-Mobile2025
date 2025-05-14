/****************************************
 Fichier : ProjectAdapter.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 Pour recycle view de Index

 Date : 05/05/2025

 Vérification :
 Date Nom Approuvé

 =========================================================
 Historique de modifications :
 Date Nom Description

 =========================================================
 ****************************************/

package com.example.teamwork.Activity.Project;

import com.example.teamwork.Activity.Team.TeamIndexActivity;
import com.example.teamwork.Activity.Team.TeamShowActivity;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Group;
import com.example.teamwork.R;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.teamwork.Database.Tables.Project;


public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyViewHolder> {
    private List<Project> projectList;
    private String authToken;
    private Context context;

    public ProjectAdapter(Context context, List<Project> projectArrayList, String authToken) {
        this.context = context;
        this.projectList = projectArrayList;
        this.authToken = authToken;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.project_recycle_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Project project = projectList.get(position);

        holder.projectName.setText(project.getName());

//        Group group =
//                AppDatabase.getDatabase(context.getApplicationContext()).groupDao().getGroupByIdProject(project.getId());
//        holder.projectGroup.setText(String.valueOf(group.getCode()));

//        String courseName =
//                AppDatabase.getDatabase(context.getApplicationContext()).courseDao().getCourseName(groupCode);
//        holder.projectCourse.setText(courseName);

        String memberCount = String.valueOf(project.getMin_per_team());
        memberCount += " - ";
        memberCount += String.valueOf(project.getMax_per_team());
        holder.projectMembersCount.setText(memberCount);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TeamIndexActivity.class);
            intent.putExtra("projectId", project.getId());
            intent.putExtra("authToken", authToken);
            String projectId = String.valueOf(project.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView projectName;
        TextView projectCourse;
        TextView projectGroup;
        TextView projectMembersCount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            projectName = (TextView) itemView.findViewById(R.id.projectName);
            projectCourse = (TextView) itemView.findViewById(R.id.projectCourse);
            projectGroup = (TextView) itemView.findViewById(R.id.projectGroup);
            projectMembersCount = (TextView) itemView.findViewById(R.id.projectMembersCount);
        }
    }

}
