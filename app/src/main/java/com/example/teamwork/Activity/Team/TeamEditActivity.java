package com.example.teamwork.Activity.Team;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.API.ApiClient;
import com.example.teamwork.API.ApiInterface;
import com.example.teamwork.API.Repository.TeamRepository;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Team;
import com.example.teamwork.R;

/****************************************
 Fichier : TeamCreateActivity
 Auteur : Antoine Blouin
 Fonctionnalité : 32.3
 Date : 2025-05-05

 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date Nom Description
 =========================================================
 ****************************************/

/**
 * L'activité qui permet de modifier une équipe.
 *
 * @author Antoine Blouin
 * @version 1.0
 * @since 2025-05-05
 */
public class TeamEditActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * L'équipe qui se fait modifier
     */
    private Team team;

    /**
     * Les champs liés à la modification de l'équipe
     */
    private EditText nameEditText, descriptionEditText;

    /**
     * Instance de la base de données
     */
    private AppDatabase db;
    private String authToken;

    /**
     * Constructeur de TeamEdit qui initialise les variables de base.
     *
     * @param savedInstanceState Si l'activité est recréée après une fermeture,
     * ce Bundle contient les données précédemment enregistrées.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_edit);

        int teamId = getIntent().getIntExtra("teamId", -1);
        authToken = getIntent().getStringExtra("authToken");
        db = AppDatabase.getDatabase(this);

        observeTeam(teamId);
    }

    /**
     * Observe l'équipe et redéfinit l'UI et les variables lorsqu'il y a un changement dans la BD
     *
     * @param teamId l'équipe à observer
     */
    private void observeTeam(int teamId){
        db.teamDao().getTeamById(teamId).observe(this, team -> {
            this.team = team;
            setViews();
        });
    }

    /**
     * Permet de définir les vues comme les champs et les boutons,
     * de relier leurs actions et d'associer les variables aux IDs du fichier XML.
     */
    private void setViews(){
        nameEditText = findViewById(R.id.name);
        descriptionEditText = findViewById(R.id.description);

        nameEditText.setText(team.getName());
        descriptionEditText.setText(team.getDescription());

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.confirm).setOnClickListener(this);
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
        } else if (v.getId() == R.id.confirm) {
            submitEdit();
        }
    }

    /**
     * Permet de soumettre le formulaire une fois le bouton appuyé,
     * vérifie les champs et modifie l'équipe dans la base de données.
     */
    private void submitEdit(){
        String name = nameEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (!validateInputs(name, description)) return;

        team.setName(name);
        team.setDescription(description);
        db.teamDao().update(team);

        ApiInterface api = ApiClient.getClient(authToken).create(ApiInterface.class);
        TeamRepository repository = new TeamRepository(this);
        repository.sendUpdateTeam(api, team);

        finish();
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

        if (db.teamDao().isNameTakenEdit(name, team.getProjectId(), team.getId()) > 0) {
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