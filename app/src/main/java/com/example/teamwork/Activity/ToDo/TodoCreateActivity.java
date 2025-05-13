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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamwork.Activity.ToDo.Audio.AudioPermissionManager;
import com.example.teamwork.Activity.ToDo.Audio.AudioPlayer;
import com.example.teamwork.Activity.ToDo.Audio.AudioRecorder;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Todo;
import com.example.teamwork.R;

/**
 * L'activité a afficher pour créer un élément d'une to do liste.
 */
public class TodoCreateActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Field pour le nom et description de l'élément de la to do list.
     */
    EditText nameEditText, descriptionEditText;
    /**
     * Layout principale de l'activité.
     */
    LinearLayout layout;
    /**
     * L'id du projet au quel l'élément sera attacher.
     */
    private int projectId;
    /**
     * Instance de la database.
     */
    private AppDatabase db;

    /**
     * image view pour représenter les boutons play, enregistrer et delete.
     */
    private ImageView audio_play, audio_record, audio_trash;
    /**
     * Audio recorder pour enregistrer les messages vocaux.
     */
    private AudioRecorder audioRecorder;
    /**
     * Audio player pour jouer les messages enregistrés.
     */
    private AudioPlayer audioPlayer;
    /**
     * state de si le record est on ou off.
     */
    private boolean record_status = false;
    /**
     * Path de l'audio qui vas etre enregistrer dans le to do. de base est null.
     */
    @Nullable
    private String audioPath = null;

    /**
     * Override de la méthode onCreate. Initialise les différentes variables et les écouteurs d'événements.
     * @param savedInstanceState Si l'activité est recréée après avoir été arrêtée précédemment,
     *     ce Bundle contient les données fournies précédemment par {@link #onSaveInstanceState}.
     *     <b><i>Remarque : Sinon, cette valeur est nulle.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_create);

        Intent intent = getIntent();
        projectId = getIntent().getIntExtra("projectId", -1);
        db = AppDatabase.getDatabase(this);

        nameEditText = findViewById(R.id.name);
        descriptionEditText = findViewById(R.id.description);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        layout = findViewById(R.id.layout);

        setAudio();

        buttonAdd.setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
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
        if (v.getId() == R.id.buttonAdd) {
            if (nameEditText.getText().toString().isEmpty()) {
                nameEditText.setError("Veuillez entrer un nom de tâche");
                nameEditText.requestFocus();
            } else {
                Todo todo = new Todo(this.projectId, nameEditText.getText().toString(), descriptionEditText.getText().toString(), audioPath, false);
                todo.setNom(nameEditText.getText().toString());
                todo.setDescription(descriptionEditText.getText().toString());
                db.todoDao().insert(todo);
                finish();
            }
        }
        //audio
        else if (v.getId() == R.id.iv_audio_mic) {
            mic_btn();
        }
        else if (v.getId() == R.id.iv_audio_play) {
            play_btn();
        }
        else if (v.getId() == R.id.iv_audio_trash) {
            //delete l'audio file, et enleve la ref dans la bd.
            audioPlayer.trash(audioPath);
            audioPath = null;
            audio_trash.setVisibility(View.GONE);
            audio_play.setVisibility(View.GONE);
            Toast.makeText(this, "Audio deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * S'assure que tout les components pour l'audio est bien set.
     */
    private void setAudio() {
        audio_play = findViewById(R.id.iv_audio_play);
        audio_record = findViewById(R.id.iv_audio_mic);
        audio_trash = findViewById(R.id.iv_audio_trash);
        audio_play.setOnClickListener(this);
        audio_record.setOnClickListener(this);
        audio_trash.setOnClickListener(this);
        audioRecorder = new AudioRecorder(this);
        audioPlayer = new AudioPlayer(this);
    }

    /**
     * Fait la logique du bouton d'enregistrement. enregistre ou stop dépendament de si le micro enregistre ou non.
     */
    private void mic_btn() {
        //is recording, need to stop
        if (record_status) {
            audioRecorder.stop();
            audioPath = audioRecorder.getLastOutputPath();
            //change icone pour mic
            audio_record.setImageResource(R.drawable.microphone);
            record_status = false;
            //check pour show le delete / play
            if (audio_play != null) {
                audio_trash.setVisibility(View.VISIBLE);
                audio_play.setVisibility(View.VISIBLE);
            }

        }
        //is not recording, need to start
        else {
            //check permission avant de record
            if (!AudioPermissionManager.hasRecordAudioPermission(this)) {
                AudioPermissionManager.requestRecordAudioPermission(this);
                return;
            }
            //si il y avais un autre record, delete.
            audioRecorder.start();
            //change icon pour stop
            audio_record.setImageResource(R.drawable.player_stop);
            record_status = true;
        }
    }

    /**
     * Fait la logique pour le boutton play. joue ou stop dépendament de si un enregistrement joue.
     */
    private void play_btn(){
        //is playing, need to pause
        if (audioPlayer.isPlaying()) {
            audioPlayer.stop();
            //set texture to play
            audio_play.setImageResource(R.drawable.player_play);

        }
        //not playing, need to pause
        else {
            audioPlayer.start(audioPath);
            //set texture to pause if playing
            if (audioPlayer.isPlaying()) {
                audio_play.setImageResource(R.drawable.player_stop);
            }
        }
    }

    /**
     * Vérifie si la permission d'utiliser l'audio a été accordée.
     * @param requestCode Le code de requête passé dans {@link #requestPermissions}.
     * @param permissions Les permissions demandées. Jamais null.
     * @param grantResults Les résultats d'autorisation correspondants, qui sont soit
     *                     {@link android.content.pm.PackageManager#PERMISSION_GRANTED},
     *                     soit {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Jamais null.
     * @param deviceId L'identifiant de l'appareil pour lequel les permissions ont été demandées.
     *                 L'appareil principal/physique est associé à {@link Context#DEVICE_ID_DEFAULT},
     *                 et les appareils virtuels reçoivent des identifiants uniques.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
        if (AudioPermissionManager.wasRecordAudioPermissionGranted(requestCode, grantResults)) {
            mic_btn();
        }
        else {
            Toast.makeText(this, "Permission wasn't granted", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);
    }
}