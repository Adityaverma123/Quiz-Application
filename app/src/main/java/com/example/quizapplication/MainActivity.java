package com.example.quizapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText id,password,name;
    FirebaseAuth mAuth;
    ProgressDialog loadingbar;
    public  void signup(View view)
    {
        loadingbar.setTitle("Signing up");
        loadingbar.setMessage("Please wait...");
        loadingbar.setCanceledOnTouchOutside(false);
        loadingbar.show();
     final String userid=id.getText().toString();
     String userpass=password.getText().toString();
     final String username=name.getText().toString();
     if(userid.equals("")||userpass.equals("")||username.equals(""))
     {
         loadingbar.dismiss();
         Toast.makeText(MainActivity.this,"Please fill all details...",Toast.LENGTH_SHORT).show();
     }
     else
     {
         mAuth=FirebaseAuth.getInstance();
         mAuth.createUserWithEmailAndPassword(userid,userpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {

                 if(task.isSuccessful())
                 {
                     Map userdata=new HashMap();
                     userdata.put("name",username);
                     userdata.put("id",FirebaseAuth.getInstance().getCurrentUser().getUid());
                     userdata.put("email",userid);
                     FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).updateChildren(userdata);
                     loadingbar.dismiss();
                     Toast.makeText(MainActivity.this,"Signup successfull!",Toast.LENGTH_SHORT).show();
                     login();

                 }
                 else
                 {
                     loadingbar.dismiss();
                     Toast.makeText(MainActivity.this,"Signup failed :(",Toast.LENGTH_SHORT).show();
                 }
             }
         });
     }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        id=findViewById(R.id.register_email);
        password=findViewById(R.id.register_password);
        name=findViewById(R.id.register_username);
        TextView logintextview=findViewById(R.id.logintextview);
        Button button=findViewById(R.id.signup);
        loadingbar=new ProgressDialog(this,R.style.MyDialogTheme);
        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null)
        {
            login();
        }
        logintextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    private void login() {
        Intent intent=new Intent(MainActivity.this,HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
