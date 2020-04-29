package com.example.quizapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SolutionsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference reference;
    SolutionAdapter adapter;
    List<SolutionModel>mSolutions;
    Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solutions);
        loadingDialog=new Dialog(this);
        loadingDialog.setContentView(R.layout.loadingbar);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCanceledOnTouchOutside(false);
        recyclerView=findViewById(R.id.recycler_View);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        mSolutions=new ArrayList<>();
        adapter=new SolutionAdapter(mSolutions);
        recyclerView.setAdapter(adapter);
        Intent intent=getIntent();
        String sub=intent.getStringExtra("title");
        loadingDialog.show();
        reference= FirebaseDatabase.getInstance().getReference().child("Tests").child(sub).child("questions");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                 SolutionModel model=snapshot.getValue(SolutionModel.class);
                 mSolutions.add(model);
                }
                adapter.notifyDataSetChanged();
                loadingDialog.dismiss();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SolutionsActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                finish();
            }
        });
    }
}
