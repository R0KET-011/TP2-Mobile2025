package com.example.teamwork.Activity.Team;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.Activity.Auth.Authentication;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.Database.Tables.TeamStudent;
import com.example.teamwork.R;

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
 * L'activité qui permet de créer une équipe.
 *
 * @author Antoine Blouin
 * @version 1.0
 * @since 2025-05-05
 */
public class TeamCreateActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * L'id du projet depuis lequel on crée l'équipe.
     */
    private int projectId;

    /**
     * Les champs à remplir pour créer l'équipe.
     */
    private EditText nameEditText, descriptionEditText;

    /**
     * L'instance de la base de données.
     */
    private AppDatabase db;

    /**
     * Le constructeur qui initialise les variables de base.
     *
     * @param savedInstanceState Si l'activité est recréée après une fermeture,
     * ce Bundle contient les données précédemment enregistrées.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_create);

        projectId = getIntent().getIntExtra("projectId",-1);
        db = AppDatabase.getDatabase(this);

        setViews();
    }

    /**
     * Permet de définir les vues comme les champs et les boutons,
     * de relier leurs actions et d'associer les variables aux IDs du fichier XML.
     */
    private void setViews(){
        nameEditText = findViewById(R.id.name);
        descriptionEditText = findViewById(R.id.description);

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.create).setOnClickListener(this);
    }

    /**
     * Permet d'exécuter les actions associées à la vue cliquée.
     *
     * @param v la vue qui est cliquée
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        } else if (v.getId() == R.id.create) {
            submitCreate();
        }
    }

    /**
     * Permet de soumettre le formulaire une fois le bouton appuyé,
     * vérifie les champs et ajoute l'équipe dans la base de données.
     */
    private void submitCreate(){
        String name = nameEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (!validateInputs(name, description)) return;

        // TODO : Validation par API et Pour l'Id
        Team team = new Team(4, name, "Non conforme", description , projectId);
        db.teamDao().insert(team);

        if (Authentication.isStudent())
            addStudentToTeam(team);

        finish();
    }

    /**
     * Ajoute l'élève actuel à l'équipe qui vient d'être créée et
     * le retire de l'équipe précédente s'il y en avait une.
     *
     * @param team l'équipe qui vient d'être créée
     */
    private void addStudentToTeam(Team team){
        TeamStudent teamStudent = new TeamStudent(team.getId(), Authentication.getId(), "");
        db.teamStudentDao().deleteStudentFromProject(Authentication.getId(), projectId);
        db.teamStudentDao().insert(teamStudent);
    }

    /**
     * Permet de valider les champs. Si une erreur est détectée,
     * on retourne false et on affiche un message d'erreur sur le champ problématique.
     *
     * @param name le nom de l'équipe
     * @param description la description de l'équipe
     * @return true si la validation est correcte, false sinon
     */
    private boolean validateInputs(String name, String description) {
        if (name.isEmpty()) {
            nameEditText.setError("Le nom est requis");
            nameEditText.requestFocus();
            return false;
        }

        if (db.teamDao().isNameTaken(name, projectId) > 0){
            nameEditText.setError("Le nom est déjà pris");
            nameEditText.requestFocus();
            return false;
        }

        if (description.isEmpty()) {
            descriptionEditText.setError("Une description est requise");
            descriptionEditText.requestFocus();
            return false;
        }

        return true;
    }
}