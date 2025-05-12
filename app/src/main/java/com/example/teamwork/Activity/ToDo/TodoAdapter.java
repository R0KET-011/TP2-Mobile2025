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

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private final List<Todo> todos;
    private final Context context;

    public TodoAdapter(Context context, List<Todo> todos) {
        this.context = context;
        this.todos = todos;
    }


    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_todo_layout, parent, false);
        return new TodoViewHolder(view);
    }

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

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, description;
        private final LinearLayout layout;

        public TodoViewHolder(View todo_item) {
            super(todo_item);
            name = todo_item.findViewById(R.id.name);
            description = todo_item.findViewById(R.id.description);
            layout = todo_item.findViewById(R.id.layout);
        }
    }
}

