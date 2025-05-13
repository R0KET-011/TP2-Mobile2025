/****************************************
 Fichier : TodoIndexActivity.java
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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Todo;
import com.example.teamwork.R;

import java.util.List;

/**
 * Activité qui affiche une to do liste pour un projet. Affiche tout les élément présent pour la to do liste du projet en question.
 */
public class TodoIndexActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * L'id du projet pour le quel la to do list sera afficher.
      */
    private int projectId;
    /**
     * Override de la méthode onCreate. Initialise les différentes variables et les écouteurs d'événements.
     * @param savedInstanceState Si l'activité est recréée après avoir été arrêtée précédemment,
     *     ce Bundle contient les données fournies précédemment par {@link #onSaveInstanceState}.
     *     <b><i>Remarque : Sinon, cette valeur est nulle.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_index);

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.add).setOnClickListener(this);

        Intent intent = getIntent();
        projectId = intent.getIntExtra("projectId", -1);


        AppDatabase db = AppDatabase.getDatabase(this);
        db.todoDao().getTodosByProjectId(projectId).observe(
                this, (List<Todo> todos) -> {
                    try {
                        RecyclerView recyclerView = findViewById(R.id.recyclerView);
                        TodoAdapter todoAdapter = new TodoAdapter(this, todos);
                        recyclerView.setAdapter(todoAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    } catch (Exception e) {
                        Log.d("RecycleView ERROR", "onCreate() returned: " + e);
                    }
                });
    }
    /**
     * Set ce que font les différent buttons lorsque l'on clique dessus.
     * @param v La vue qui a été cliquer.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        }
        if (v.getId() == R.id.add) {
            Intent intent = new Intent(this, TodoCreateActivity.class);
            intent.putExtra("projectId", this.projectId);
            startActivity(intent);
        }
    }
}