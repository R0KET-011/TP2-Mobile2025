/****************************************
 Fichier : ProjectCreate.java
 Auteur : Kevin Larochelle
 Fonctionnalité :
 Fait afficher un formulaire pour créer un projet.

 Date : 05/11/2025

 Vérification :
 Date Nom Approuvé

 =========================================================
 Historique de modifications :
 Date Nom Description

 =========================================================
 ****************************************/

package com.example.teamwork.Activity.Project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamwork.Activity.Auth.Authentication;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Project;
import com.example.teamwork.R;

import java.util.concurrent.atomic.AtomicInteger;

public class ProjectCreate extends AppCompatActivity implements View.OnClickListener{

    private AppDatabase db;
    EditText nameEdit;
    EditText descriptionEdit;
    EditText minEdit;
    EditText maxEdit;
    CheckBox joinableBox;
    CheckBox creatableBox;
    CheckBox commonClassesBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_project_create);

        db = AppDatabase.getDatabase(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.create).setOnClickListener(this);
        nameEdit = findViewById(R.id.name);
        descriptionEdit = findViewById(R.id.description);
        minEdit = findViewById(R.id.min_per_team);
        maxEdit = findViewById(R.id.max_per_team);
        joinableBox = findViewById(R.id.joinable_checkbox);
        creatableBox = findViewById(R.id.creatable_checkbox);
        commonClassesBox = findViewById(R.id.common_classes_checkbox);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        }
        else if(v.getId() == R.id.create){

            int id = AppDatabase.getDatabase(this).projectDao().getProjectTableSize() + 1;
            String name = nameEdit.getText().toString();
            String description = descriptionEdit.getText().toString();
            String min = minEdit.getText().toString();
            String max = maxEdit.getText().toString();
            boolean joinable = joinableBox.isChecked();
            boolean creatable = creatableBox.isChecked();
            boolean common_classes = commonClassesBox.isChecked();

            if (!validateForm(name, min, max)){ return; }

            Project project = new Project(id, name, description, Integer.parseInt(min),
                    Integer.parseInt(max),
                    joinable,
                    creatable,
                    common_classes);

            db.projectDao().insert(project);
            finish();
        }
    }

    public boolean validateForm(String name, String min, String max) {
        if (name.isEmpty()){
            nameEdit.setError(getString(R.string.error_name));
            nameEdit.requestFocus();
            return false;
        }

        if (min.isEmpty()) {
            minEdit.setError(getString(R.string.error_min));
            minEdit.requestFocus();
            return false;
        }

        if (Integer.parseInt(min) < 1) {
            minEdit.setError(getString(R.string.error_minLow));
            minEdit.requestFocus();
            return false;
        }

        if (max.isEmpty()) {
            maxEdit.setError(getString(R.string.error_max));
            maxEdit.requestFocus();
            return false;
        }

        if (Integer.parseInt(max) < Integer.parseInt(min)){
            maxEdit.setError(getString(R.string.error_maxLow));
            maxEdit.requestFocus();
            return false;
        }

        return true;
    }
}