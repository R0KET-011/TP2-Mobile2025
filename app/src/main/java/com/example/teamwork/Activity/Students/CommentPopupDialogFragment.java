package com.example.teamwork.Activity.Students;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.R;
/****************************************
 Fichier : CommentPopupDialogFragment
 Auteur : Émeric Leclerc
 Fonctionnalité : 35.1 à 35.4 (gestion des commentaire d'élèves)
 Date : 2025-05-07

 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date Nom Description
 =========================================================
 ****************************************/


/**
 * Classe DialogFragment pour les commentaires. utilise DialogFragment pour pouvoir utiliser
 * liveData (ce qui n'est pas possible dans un dialog simple)
 */
public class CommentPopupDialogFragment extends DialogFragment {
    /**
     * contexte dans le quel le le DialogFragment est utiliser.
     */
    private final Context context;
    /**
     * L'id du student au quel le commentaire est set.
     */
    private final int studentId;
    /**
     * L'id de l'équipe au quel l'élève fait parti.
     */
    private final int teamId;
    /**
     * Field text du commentaire.
     */
    private EditText et_comment;

    /**
     * Constructeur du dialogFragment.
     * @param context contexte dans le quel le le DialogFragment est utiliser.
     * @param teamId L'id de l'équipe au quel l'élève fait parti.
     * @param studentId L'id du student au quel le commentaire est set.
     */
    public CommentPopupDialogFragment(Context context, int teamId, int studentId){
        this.context = context;
        this .teamId = teamId;
        this.studentId = studentId;
    }

    /**
     * Override onCreateView pour set la vu et définir les élément du dialogue.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_comment_popup, container, false);

        et_comment = view.findViewById(R.id.et_comment);

        //check pour get le commentaire
        AppDatabase appDatabase = AppDatabase.getDatabase(context);
        appDatabase.teamStudentDao().getCommentFromStudentTeam(studentId, teamId).observe(this, (String s)-> {
            // if s is set, set it in EditText
            if (s != null) {
                et_comment.setText(s);
            }
            //else leave blank (default)
        });

        // set X on clock
        ImageView x = view.findViewById(R.id.back);
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    /**
     * Override onStart, set la taille et position du dialogue.
     */
    @Override
    public void onStart() {
        super.onStart();

        //assert dialog exists
        Dialog dialog = getDialog();
        assert dialog != null;

        //get width and height of screen
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        // set window params
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setLayout((int)(screenWidth*0.9), (int)(screenHeight*0.35));
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        }
    }

    /**
     * Override onDismiss, save le commentaire lorsque le dialogue est fermé.
     * @param dialog the dialog that was dismissed will be passed into the
     *               method
     */
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        AppDatabase appDatabase = AppDatabase.getDatabase(context);
        String update = et_comment.getText().toString();

        //si string vide, met null en bd
        if (update.isEmpty()) {
            appDatabase.teamStudentDao().setCommentWhereStudentTeam(null, studentId, teamId);
        }
        //sinon met ce le nouveau string
        else {
            appDatabase.teamStudentDao().setCommentWhereStudentTeam(update, studentId, teamId);
        }

    }
}