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
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button loginButton;
    FirebaseAuth mAuth;
    ProgressDialog loadingbar;
    public void onClick(View view)
    {
        loadingbar.setTitle("Loggin in");
        loadingbar.setMessage("Please wait...");
        loadingbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingbar.setCanceledOnTouchOutside(false);
        loadingbar.show();
        String useremail=email.getText().toString();
        String userpassword=password.getText().toString();
        if(useremail.equals("") || userpassword.equals(""))
        {
            loadingbar.dismiss();
            Toast.makeText(LoginActivity.this,"Both fields are required",Toast.LENGTH_SHORT).show();
        }
        else
        {
            mAuth=FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(useremail,userpassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            loadingbar.dismiss();
                            if(task.isSuccessful())
                            {

                                login();
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this,"Login failed :(",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    loadingbar.dismiss();
                    Toast.makeText(LoginActivity.this,"User doesn't exists",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void login() {
        Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        loginButton=findViewById(R.id.loginButton);
        loadingbar=new ProgressDialog(this,R.style.MyDialogTheme);

    }
}
