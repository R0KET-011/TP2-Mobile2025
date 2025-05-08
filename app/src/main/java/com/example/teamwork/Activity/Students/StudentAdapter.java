package com.example.teamwork.Activity.Students;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

    /**
     * Constructeur de l'adapter qui requière la liste des élèves et le context
     * @param context
     * @param students
     */
    public StudentAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
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

            iv_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, CommentPopupActivity.class));
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }
}
