/****************************************
 Fichier : TodoAdapter.java
 Auteur : Samy Larochelle
 Fonctionnalité : Fonctionalités 36.4, Affichage des tâches
 Date : 05/09/2025
 Vérification :
 Date           Nom                 Approuvé
 =========================================================
 Historique de modifications :
 Date           Nom                 Description
 05/09/2025     Samy Larochelle     Création
 05/10/2025     Samy Larochelle     Correction
 =========================================================
 ****************************************/

package com.example.teamwork.Activity.ToDo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamwork.Database.Tables.Todo;
import com.example.teamwork.R;

import java.util.List;

/**
 * L'adapteur pour le recycler view de la to do liste.
 */
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    /**
     * La liste des élément a mettre dans le recycler view.
     */
    private final List<Todo> todos;
    /**
     * Contexte de l'Adapter.
     */
    private final Context context;

    /**
     * Constructeur pour l'adapter
     * @param context du recyclerview
     * @param todos la liste des élément d'une to do list à afficher.
     */
    public TodoAdapter(Context context, List<Todo> todos) {
        this.context = context;
        this.todos = todos;
    }

    /**
     * Override onCreateViewHolder pour créer la ViewHolder de l'adapter.
     * @param parent Le groupe de la  View dans lequel la nouvelle vue sera ajoutée après qu'elle soit lié à
     *               une position d'adaptateur.
     * @param viewType le type de la nouvelle view
     *
     * @return return le ViewHolder qui a été créé.
     */
    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_todo_layout, parent, false);
        return new TodoViewHolder(view);
    }

    /**
     * Override onBindViewHolder pour set les vues dans le recycler view.
     * @param holder Le titulaire de la vue qui doit être mis à jour pour représenter le contenu de
     *                l'élément à la position donnée dans l'ensemble de données.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todos.get(position);

        holder.name.setText(todo.getNom());
        if (todo.getDescription().isEmpty() || todo.getDescription() == null)
            holder.description.setText("(Aucune description)");
        else
            holder.description.setText(todo.getDescription());

        if (todo.isCompleted()) {
            holder.layout.setBackground(context.getResources().getDrawable(R.drawable.rv_task_completed));
        } else {
            holder.layout.setBackground(context.getResources().getDrawable(R.drawable.rv_bottom_border));
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TodoShowActivity.class);
            intent.putExtra("todoId", todo.getId());
            context.startActivity(intent);
        });
    }

    /**
     *
     * @return le nombre d'item dans la liste des éléments.
     */
    @Override
    public int getItemCount() {
        return todos.size();
    }

    /**
     * Classe qui extends ViewHolder. contien la vue de chaque élément de la to do list.
     */
    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        /**
         * Text views du nom (titre) et descriptions de l'élément de la to do liste.
         */
        private final TextView name, description;
        /**
         * Layout principale de la vue.
         */
        private final LinearLayout layout;

        /**
         * Constructeur du ViewHolder
         * @param todo_item l'item de la to do liste qui sera mit dans le ViewHolder.
         */
        public TodoViewHolder(View todo_item) {
            super(todo_item);
            name = todo_item.findViewById(R.id.name);
            description = todo_item.findViewById(R.id.description);
            layout = todo_item.findViewById(R.id.layout);
        }
    }
}

