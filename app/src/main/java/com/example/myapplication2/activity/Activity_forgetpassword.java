package com.example.myapplication2.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_forgetpassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button btnEnv;
    EditText mailmodef;
    ImageView retourmdp;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mailmdpoublie);
        mAuth = FirebaseAuth.getInstance();
        mailmodef=findViewById(R.id.mailmodef);
        btnEnv=findViewById(R.id.alert2);
        retourmdp=findViewById(R.id.retourmdp);
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users");
        retourmdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity_forgetpassword.this, Activity_connexion.class));
                finish();
            }
        });
          btnEnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mailmodeff=mailmodef.getText().toString().trim();

                if(mailmodeff.isEmpty())
                {  mailmodef.setError("mail obliatoire");
                    mailmodef.requestFocus();
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(mailmodeff).matches())
                { mailmodef.setError("adresse ne sont pas valide");
                    mailmodef.requestFocus();
                    return;
                }
                progressDialog=new ProgressDialog(Activity_forgetpassword.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                          mAuth.sendPasswordResetEmail(mailmodeff)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_EMAIL));
                                                    }
                                            else
                                            {
                                                Toast.makeText(Activity_forgetpassword.this,"try again",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                });
            }
    }