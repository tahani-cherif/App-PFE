package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MailmdpoublieActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button btnEnv;
    EditText mailmodef;
    ImageView retourmdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.mailmdpoublie);
        mailmodef=findViewById(R.id.mailmodef);
        btnEnv =findViewById(R.id.alert);
        retourmdp=findViewById(R.id.retourmdp);
        retourmdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MailmdpoublieActivity.this,Connexion.class));
                finish();
            }
        });
        btnEnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder myDialog = new AlertDialog.Builder(MailmdpoublieActivity.this);
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
                mAuth.sendPasswordResetEmail(mailmodeff)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    myDialog.setTitle("Entrer le code!");
                                    final EditText code = new EditText(MailmdpoublieActivity.this);
                                    code.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    myDialog.setView(code);
                                    myDialog.setPositiveButton("valider", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(MailmdpoublieActivity.this, MdpoublieActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    myDialog.setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                                    myDialog.show();
                                }
                                else
                                {
                                    Toast.makeText(MailmdpoublieActivity.this,"try again",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });
    }
}