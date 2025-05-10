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

/**
 * Classe DialogFragment pour les commentaires. utilise DialogFragment pour pouvoir utiliser
 * liveData (ce qui n'est pas possible dans un dialog simple)
 */
public class CommentPopupDialogFragment extends DialogFragment {
    private final Context context;
    private final int studentId;
    private final int teamId;
    private EditText et_comment;

    public CommentPopupDialogFragment(Context context, int teamId, int studentId){
        this.context = context;
        this .teamId = teamId;
        this.studentId = studentId;
    }

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