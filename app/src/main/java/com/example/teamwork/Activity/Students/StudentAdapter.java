package com.example.teamwork.Activity.Students;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamwork.Activity.Auth.Authentication;
import com.example.teamwork.Database.AppDatabase;
import com.example.teamwork.Database.Tables.Student;
import com.example.teamwork.Database.Tables.TeamStudent;
import com.example.teamwork.R;

import java.util.List;
/****************************************
 Fichier : StudentAdapter
 Auteur : Émeric Leclerc
 Fonctionnalité :
 Date : 2025-05-05

 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date Nom Description
 =========================================================
 ****************************************/

/**
 * L'adapteur pour le RecyclerView de la liste des élèves.
 */
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    /**
     * La liste des élèves à afficher.
     */
    private List<Student> students;
    /**
     * Le Context de l'Adapter.
     */
    private Context context;
    /**
     * L'id au quel la liste d'étudiant appartien.
     */
    private int team_id;

    /**
     * Constructeur de l'adapter qui requière la liste des élèves et le context.
     * @param context context of the adapter
     * @param students list of the students to put in the adapter
     */
    public StudentAdapter(Context context, List<Student> students, int team_id) {
        this.context = context;
        this.students = students;
        this.team_id = team_id;
    }

    /**
     * Override de onCreateViewHolder, créer le viewHolder pour les élèves.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_students_layout, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Override onBindViewHolder, set la vue dans le ViewHolder.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = students.get(position);
        holder.tv_name.setText(student.getFullName());
        holder.setCommentListener(context, team_id, student.getId());

        if (!Authentication.isStudent())
            holder.setDeleteListener(student.getId());
    }

    /**
     * Get le nombre d'élèves dans la liste donner lors de l'initialisation de l'adapter.
     * @return
     */
    @Override
    public int getItemCount() {
        return students.size();
    }

    /**
     * ViewHolder pour le layout du RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * TextView qui contien le nom de l'élève
         */
        TextView tv_name;
        /**
         * ImageView qui contien l'icone de commentaire
         */
        ImageView iv_comment;

        /**
         * ImageView qui contien l'icone de delete
         */
        ImageView iv_delete;

        /**
         * Constructeur du ViewHolder.
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_nom);
            iv_comment = itemView.findViewById(R.id.comment_icon);
            iv_delete = itemView.findViewById(R.id.delete);

            if (Authentication.isStudent())
                iv_delete.setVisibility(View.GONE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

        /**
         * Set le delete Listener pour supprimer le lien entre l'étudiant et l'équipe dans la bd.
         * @param studentId
         */
        public void setDeleteListener(int studentId){
            iv_delete.setOnClickListener(v -> {
                AppDatabase db = AppDatabase.getDatabase(context);
                db.teamStudentDao().deleteStudentFromTeam(team_id, studentId);
            });
        }

        /**
         * Set le comment listemenr pour afficher la vue de commentaire.
         * @param context L'activité de l'apadteur
         * @param teamId L'id de team au quel le student appartien.
         * @param studentId L'id de l'étudiant.
         */
        public void setCommentListener(Context context, int teamId, int studentId) {
            iv_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context instanceof AppCompatActivity) {
                        CommentPopupDialogFragment popup = new CommentPopupDialogFragment(context, teamId, studentId);
                        popup.show(((AppCompatActivity) context).getSupportFragmentManager(), "CommentDialog");
                    }
                    else {
                        Toast.makeText(context, "Context is not an activity", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
