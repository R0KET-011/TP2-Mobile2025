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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamwork.Activity.Auth.Authentication;
import com.example.teamwork.Activity.MenuHelper.BaseActivity;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Todo;
import com.example.teamwork.R;

import java.util.List;

/**
 * Activité qui affiche une to do liste pour un projet. Affiche tout les élément présent pour la to do liste du projet en question.
 */
public class TodoIndexActivity extends BaseActivity {
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

        //set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        projectId = intent.getIntExtra("projectId", -1);

        if (projectId == -1) {
            Toast.makeText(this, "Error with project id", Toast.LENGTH_SHORT).show();
        }

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
     * Add des options au menu existant.
     * @param item The menu item that was selected.
     *
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_create) {
            Intent intent = new Intent(this, TodoCreateActivity.class);
            intent.putExtra("projectId", this.projectId);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Override onCreateOptionMenu pour set le menu
     * @param menu The options menu in which you place your items.
     *
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header_menu, menu);
        menu.findItem(R.id.menu_create).setVisible(true);
        return true;
    }
}