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

import com.example.teamwork.R;
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
    private Context context;

    public ProjectAdapter(Context context, List<Project> projectArrayList) {
        this.context = context;
        this.projectList = projectArrayList;
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
        holder.projectName.setText(projectList.get(position).getName());
        holder.projectCourse.setText(projectList.get(position).getCourse());
        String groupText = context.getResources().getString(R.string.project_group);
        groupText += String.valueOf(projectList.get(position).getGroup());
        holder.projectGroup.setText(groupText);
        String memberCount = context.getResources().getString(R.string.project_participant);
        memberCount += String.valueOf(projectList.get(position).getMin_per_team());
        memberCount += " - ";
        memberCount += String.valueOf(projectList.get(position).getMax_per_team());
        holder.projectMembersCount.setText(memberCount);
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
