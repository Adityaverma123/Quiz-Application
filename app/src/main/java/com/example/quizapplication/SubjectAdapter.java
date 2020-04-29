package com.example.quizapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.viewHolder> {
    List<SubjectModel> subjects;
    Context context;
    public SubjectAdapter(List<SubjectModel>subjects,Context context)
    {
        this.subjects=subjects;
        this.context=context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subjects,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final SubjectModel model=subjects.get(position);
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.MyDialogTheme);
                builder.setTitle("Are you sure?");
                builder.setMessage("Make sure you have an active internet connection");
                // add the buttons
                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(context,QuestionsActivity.class);
                                intent.putExtra("title",model.getTitle());
                                context.startActivity(intent);
                                ((Activity)context).finish();

                            }
                        });
                        builder.setNegativeButton("Cancel", null);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
      private TextView description;
       private TextView title;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            description=itemView.findViewById(R.id.sub_description);
            title=itemView.findViewById(R.id.sub_title);
        }
    }
}
