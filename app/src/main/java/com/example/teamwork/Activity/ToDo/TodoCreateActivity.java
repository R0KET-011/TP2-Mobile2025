/****************************************
 Fichier : TodoCreateActivity.java
 Auteur : Samy Larochelle
 Fonctionnalité : Fonctionalités 36.1: Ajout d'une tâche
 Date : 05/10/2025
 Vérification :
 Date           Nom                 Approuvé
 =========================================================
 Historique de modifications :
 Date           Nom                 Description
 05/10/2025     Samy Larochelle     Création
 =========================================================
 ****************************************/
package com.example.teamwork.Activity.ToDo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Todo;
import com.example.teamwork.R;

public class TodoCreateActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nameEditText, descriptionEditText;
    LinearLayout layout;
    private int projectId;
    private AppDatabase db;
    private Button buttonAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_create);

        Intent intent = getIntent();
        projectId = getIntent().getIntExtra("projectId", -1);
        db = AppDatabase.getDatabase(this);

        nameEditText = findViewById(R.id.name);
        descriptionEditText = findViewById(R.id.description);
        buttonAdd = findViewById(R.id.buttonAdd);
        layout = findViewById(R.id.layout);

        buttonAdd.setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        }
        if (v.getId() == R.id.buttonAdd) {
            if (nameEditText.getText().toString().isEmpty()) {
                nameEditText.setError("Veuillez entrer un nom de tâche");
                nameEditText.requestFocus();
            } else {
                Todo todo = new Todo(this.projectId, nameEditText.getText().toString(), descriptionEditText.getText().toString(), "", false);
                todo.setNom(nameEditText.getText().toString());
                todo.setDescription(descriptionEditText.getText().toString());
                db.todoDao().insert(todo);
                finish();
            }
        }
    }
}