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

import com.example.teamwork.Database.Tables.Student;
import com.example.teamwork.R;

import java.util.List;

/**
 * L'adapteur pour le RecyclerView de la liste des élèves.
 */
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    /**
     * La liste des élèves à afficher.
     */
    private List<Student> students;
    /**
     * Le Context ou le recyclerview est.
     */
    private Context context;
    private int team_id;

    /**
     * Constructeur de l'adapter qui requière la liste des élèves et le context
     * @param context context of the adapter
     * @param students list of the students to put in the adapter
     */
    public StudentAdapter(Context context, List<Student> students, int team_id) {
        this.context = context;
        this.students = students;
        this.team_id = team_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_students_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_name.setText(students.get(position).getFullName());
        holder.setCommentListener(context, team_id, students.get(position).getId());

    }

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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_nom);
            iv_comment = itemView.findViewById(R.id.comment_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

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
