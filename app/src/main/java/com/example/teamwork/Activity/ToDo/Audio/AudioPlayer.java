package com.example.teamwork.Activity.ToDo.Audio;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
/****************************************
 Fichier : AudioPlayer
 Auteur : Émeric Leclerc
 Fonctionnalité : 36.6 et 36.7
 Date : 2025-05-13

 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date Nom Description
 =========================================================
 ****************************************/

/**
 * Classe pour gérer un MediaPlayer. play des audio file provided avec un path
 */
public class AudioPlayer {

    private MediaPlayer mediaPlayer = null;
    private final Context context;

    public AudioPlayer(Context context) {
        this.context = context;
    }

    /**
     * crer un MediaPlayer et joue un audio file avec un path.
     * @param path path qui sera jouer si possible.
     */
    public void start(String path) {

        if (path == null || path.trim().isEmpty()) {
            Toast.makeText(context, "path is null", Toast.LENGTH_SHORT).show();
            return;
        }

        File audioFile = new File(path);
        if (!audioFile.exists()) {
            Toast.makeText(context, "File doesn't exists", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mediaPlayer != null) {
            stop();
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stop();
            }
        });
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            Toast.makeText(context, "Error while loading or playing file.", Toast.LENGTH_SHORT).show();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * pause le media player si unpause
     */
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    /**
     * unpause le media player si paused
     */
    public void play() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    /**
     * stop le media player et le reset.
     */
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        else {
            Toast.makeText(context, "MediaPlayer isn't playing.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Retourne l'état du mediaPlayer, s'il est en train de jouer quelque chose ou non.
     * @return si le lecteur joue ou non
     */
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    /**
     * Delete un enregistrement au path désigné.
     * @param path chemain du fichier a supprimer.
     * @return si le ficher a été supprimer ou non.
     */
    public boolean trash(String path) {
        if (path == null || path.trim().isEmpty()) {
            Toast.makeText(context, "path is null", Toast.LENGTH_SHORT).show();
            return false;
        }

        File audioFile = new File(path);
        if (!audioFile.exists()) {
            Toast.makeText(context, "File doesn't exists", Toast.LENGTH_SHORT).show();
            return false;
        }
        return audioFile.delete();
    }

}
