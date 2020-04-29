package com.example.quizapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SolutionAdapter extends RecyclerView.Adapter<SolutionAdapter.solutionHolder> {
    List<SolutionModel>mSolutions;
    public SolutionAdapter(List<SolutionModel> mSolutions)
    {
        this.mSolutions=mSolutions;
    }

    @NonNull
    @Override
    public solutionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.solutions_layout,parent,false);
        return new solutionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull solutionHolder holder, int position) {
        SolutionModel model=mSolutions.get(position);
        holder.ques.setText("Questions: "+model.question);
        holder.ans.setText("Ans "+model.correctAns);
    }

    @Override
    public int getItemCount() {
        return mSolutions.size();
    }

    public class solutionHolder extends RecyclerView.ViewHolder
    {
        TextView ques,ans;

        public solutionHolder(@NonNull View itemView) {
            super(itemView);
            ques=itemView.findViewById(R.id.sol_ques);
            ans=itemView.findViewById(R.id.sol_ans);
        }
    }
}
