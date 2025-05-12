/****************************************
 Fichier : TodoShowActivity.java
 Auteur : Samy Larochelle
 Fonctionnalité : Fonctionalités 36.2 et 36.3 : Modification/Suppression d'une tâche
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

public class TodoShowActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nameEditText, descriptionEditText;
    LinearLayout layout;
    private Todo todo;
    private AppDatabase db;
    private Button buttonDelete, buttonEdit, buttonComplete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_show);

        Intent intent = getIntent();
        int todoId = intent.getIntExtra("todoId", -1);

        db = AppDatabase.getDatabase(this);
        db.todoDao().getTodoById(todoId).observe(this, todo -> {
            this.todo = todo;
            this.setViews();
        });
    }

    public void setViews() {
        nameEditText = findViewById(R.id.name);
        descriptionEditText = findViewById(R.id.description);
        buttonComplete = findViewById(R.id.buttonComplete);
        layout = findViewById(R.id.layout);

        nameEditText.setText(this.todo.getNom());
        descriptionEditText.setText(this.todo.getDescription());
        if (this.todo.isCompleted())
            buttonComplete.setText("Marquer comme\nincomplété");
        else
            buttonComplete.setText("Marquer comme\ncomplété");

        buttonComplete.setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.buttonDelete).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            if (nameEditText.getText().toString().isEmpty()) {
                nameEditText.setError("Veuillez entrer un nom de tâche");
                nameEditText.requestFocus();
            } else {
                todo.setNom(nameEditText.getText().toString());
                todo.setDescription(descriptionEditText.getText().toString());
                db.todoDao().update(todo);
                finish();
            }
        }
        if (v.getId() == R.id.buttonDelete) {
            db.todoDao().delete(todo);
            finish();
        }
        if (v.getId() == R.id.buttonComplete) {
            todo.setCompleted(!todo.isCompleted());
            if (todo.isCompleted())
                buttonComplete.setText("Marquer comme\nincomplété");
            else
                buttonComplete.setText("Marquer comme\ncomplété");
        }
    }
}