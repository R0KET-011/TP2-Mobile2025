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

import java.util.ArrayList;


public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyViewHolder> {
    private ArrayList<Project> projectArrayList = new ArrayList<Project>();
    private Context context;

    public ProjectAdapter(Context context, ArrayList<Project> projectArrayList) {
        this.context = context;
        this.projectArrayList = projectArrayList;
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
        holder.projectName.setText(projectArrayList.get(position).getName());
        holder.projectCourse.setText(projectArrayList.get(position).getCourse());
        String groupText = context.getResources().getString(R.string.project_group);
        groupText += String.valueOf(projectArrayList.get(position).getGroup());
        holder.projectGroup.setText(groupText);
        String memberCount = context.getResources().getString(R.string.project_participant);
        memberCount += String.valueOf(projectArrayList.get(position).getMinPerTeam());
        memberCount += " - ";
        memberCount += String.valueOf(projectArrayList.get(position).getMaxPerTeam());
        holder.projectMembersCount.setText(memberCount);
    }

    @Override
    public int getItemCount() {
        return projectArrayList.size();
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
