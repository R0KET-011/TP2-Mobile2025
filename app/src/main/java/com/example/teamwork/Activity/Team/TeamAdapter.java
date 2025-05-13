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

/****************************************
 Fichier : TeamCreateActivity
 Auteur : Antoine Blouin
 Fonctionnalité : 32.1
 Date : 2025-05-05

 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date Nom Description
 =========================================================
 ****************************************/

/**
 * Cet adapter permet d'afficher la liste des équipes du projet avec
 * des informations générales. Lorsqu'on clique dessus, une équipe nous
 * amène vers les détails de celle-ci.
 *
 * @author Antoine Blouin
 * @version 1.0
 * @since 2025-05-05
 */
public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    /**
     * Contexte de l'activité qui appelle l'adapter.
     */
    private final Context context;

    /**
     * La liste des équipes à afficher.
     */
    private final List<Team> teams;

    /**
     * Le projet qui est en lien avec les équipes.
     */
    private final Project project;

    /**
     * L'instance de la base de données.
     */
    private final AppDatabase db;

    /**
     * Conserve le nombre d'étudiants par équipe (cache le résultat pour éviter les requêtes répétées.
     */
    private final Map<Integer, Integer> studentCounts = new HashMap<>();

    /**
     * Constructeur pour l'initialisation.
     *
     * @param context le contexte de l'activité qui appelle l'adapter
     * @param teams la liste des équipes à afficher
     * @param project le projet qui est en lien avec les équipes
     * @param db l'instance de la base de données
     */
    public TeamAdapter(Context context, List<Team> teams, Project project, AppDatabase db) {
        this.context = context;
        this.teams = teams;
        this.project = project;
        this.db = db;
    }

    /**
     * Fait le lien entre le team_item et l'inflater.
     *
     * @param parent Le ViewGroup dans lequel la nouvelle vue sera ajoutée.
     * @param viewType Le type de vue.
     * @return Un nouveau TeamViewHolder
     */
    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.team_item, parent, false);
        return new TeamViewHolder(view);
    }

    /**
     * Appelle le holder à la position donnée afin qu'il s'initialise.
     *
     * @param holder Le ViewHolder à mettre à jour
     * @param position La position dans la liste
     */
    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        holder.bind(teams.get(position));
    }

    /**
     * Permet d'obtenir le nombre d'équipes affichées dans l'adapter.
     *
     * @return Le nombre d'équipes du projet
     */
    @Override
    public int getItemCount() {
        return teams.size();
    }

    /**
     * Le holder utilisé dans le RecyclerView pour représenter une équipe.
     */
    class TeamViewHolder extends RecyclerView.ViewHolder {

        /**
         * Les TextView qui contiennent les informations de l'équipe.
         */
        private final TextView nameTextView, stateTextView, sizeTextView;

        /**
         * Le constructeur du holder qui recherche les TextView dans la vue.
         *
         * @param team_item La vue qui représente l'item d'une équipe
         */
        public TeamViewHolder(View team_item) {
            super(team_item);
            nameTextView = team_item.findViewById(R.id.name);
            stateTextView = team_item.findViewById(R.id.state);
            sizeTextView = team_item.findViewById(R.id.size);
        }

        /**
         * La méthode appelée pour associer les informations de l'équipe à la vue.
         *
         * @param team L'équipe à afficher
         */
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

        /**
         * Permet de surveiller le nombre d'étudiants (mise à jour automatique avec LiveData).
         *
         * @param team L'équipe à observer
         */
        private void observeStudentCounts(Team team){
            db.teamStudentDao().getStudentCountForTeam(team.getId())
                    .observe((LifecycleOwner) context, count -> {
                        studentCounts.put(team.getId(), count);
                        setSizeText(count);
                    });
        }

        /**
         * Affiche la taille de l'équipe (ex : 1/5).
         *
         * @param count Le nombre d'élèves dans l'équipe
         */
        private void setSizeText(int count) {
            sizeTextView.setText(String.format("%d/%d", count, project.getMax_per_team()));
        }

        /**
         * Permet d'ouvrir la vue détaillée de l'équipe lorsqu'on clique dessus.
         *
         * @param team L'équipe concernée
         */
        private void setClickListener(Team team) {
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, TeamShowActivity.class);
                intent.putExtra("teamId", team.getId());
                context.startActivity(intent);
            });
        }
    }
}

