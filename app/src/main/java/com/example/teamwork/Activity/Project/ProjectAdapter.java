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

/**
 * L'adapter pour lister des projets dans un recycler view.
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyViewHolder> {
    /**
     * La liste des projets qui sera afficher.
     */
    private List<Project> projectList;
    /**
     * Token d'autentification pour l'API web.
     */
    private String authToken;
    /**
     * Contexte de l'Adapter
     */
    private Context context;

    /**
     * Constructeur de l'Adapter.
     * @param context contexte dans le quelle l'Adapter est utilisé.
     * @param projectArrayList Liste des projets à afficher.
     * @param authToken Token d'authentification pour l'API web.
     */
    public ProjectAdapter(Context context, List<Project> projectArrayList, String authToken) {
        this.context = context;
        this.projectList = projectArrayList;
        this.authToken = authToken;
    }

    /**
     * Override on CreateViewHolder pour créer les viewHolder de chaque projet dans la liste.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return la ViewHolder.
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.project_recycle_view, parent, false);

        return new MyViewHolder(view);
    }

    /**
     * Override onBindViewHolder pour définir les vue dans chaque viewHolder
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
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

    /**
     * get le nombre de projet listé.
     * @return nb de projet dans la liste.
     */
    @Override
    public int getItemCount() {
        return projectList.size();
    }

    /**
     * Classe ViewHolder pour cet Adapter.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        /**
         * Textview pour le nom du projet.
         */
        TextView projectName;
        /**
         * Textview pour le cours du projet.
         */
        TextView projectCourse;
        /**
         * Textview pour le groups du projet.
         */
        TextView projectGroup;
        /**
         * Textview pour le nombre de participant du projet.
         */
        TextView projectMembersCount;

        /**
         * Constructeur du viewHolder pour définir chaque textView.
         * @param itemView
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            projectName = (TextView) itemView.findViewById(R.id.projectName);
            projectCourse = (TextView) itemView.findViewById(R.id.projectCourse);
            projectGroup = (TextView) itemView.findViewById(R.id.projectGroup);
            projectMembersCount = (TextView) itemView.findViewById(R.id.projectMembersCount);
        }
    }

}
