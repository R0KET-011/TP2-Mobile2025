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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamwork.API.ApiClient;
import com.example.teamwork.API.ApiInterface;
import com.example.teamwork.API.Repository.ProjectRepository;
import com.example.teamwork.Activity.Auth.Authentication;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Course;
import com.example.teamwork.Database.Tables.Group;
import com.example.teamwork.Database.Tables.GroupProject;
import com.example.teamwork.Database.Tables.Project;
import com.example.teamwork.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProjectCreate extends AppCompatActivity implements View.OnClickListener{

    private AppDatabase db;
    EditText nameEdit, descriptionEdit, minEdit, maxEdit;
    CheckBox joinableBox, creatableBox, commonClassesBox;
    List<Group> groups;
    List<Course> courses;
    Spinner groupSelection;
    String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_create);

        setVariables();
        setDropdownMenus();


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

            int groupID = groupSelection.getSelectedItemPosition()+1;
            GroupProject groupProject = new GroupProject(groupID
                    , id);
            Log.v("LOG", String.valueOf(groupProject.getId_group()));
            Log.v("LOG", String.valueOf(groupProject.getId_project()));
            db.groupProjectDao().insertEntry(groupProject);

            ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
            ProjectRepository repositoryP = new ProjectRepository(this);
            repositoryP.sendCreateProject(api, project, groupID);

            finish();
        }
    }

    public void setVariables(){
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
        authToken = getIntent().getStringExtra("authToken");
    }

    public void setDropdownMenus() {
        setLists();
        groupSelection = findViewById(R.id.groupSelector);
        ArrayAdapter<Group> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
        adapter.setDropDownViewResource(R.layout.project_spinner);
        groupSelection.setAdapter(adapter);

    }

    public void setLists() {
        String jsonGroups = getIntent().getStringExtra("groups");
        String jsonCourses = getIntent().getStringExtra("courses");

        Gson gson = new Gson();
        Type typeGroup = new TypeToken<List<Group>>(){}.getType();
        Type typeCourse = new TypeToken<List<Course>>(){}.getType();

        groups = gson.fromJson(jsonGroups, typeGroup);
        courses = gson.fromJson(jsonCourses, typeCourse);
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