package com.example.myapplication2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class changepassword extends AppCompatActivity {
    Button valid2;
    EditText smdp,cvmdp2;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changemdp);
            mAuth = FirebaseAuth.getInstance();
            smdp=findViewById(R.id.smdp2);
            cvmdp2=findViewById(R.id.cvmdp2);
            reference= FirebaseDatabase.getInstance().getReference("Users");
            valid2=findViewById(R.id.mdpv22);
            valid2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String a=smdp.getText().toString().trim();
                    String b=cvmdp2.getText().toString().trim();
                    if (a.isEmpty())
                    { smdp.setError("mot de passe  obligatoire");
                        smdp.requestFocus();
                        return;
                    }

                    if (a.length()<8)
                    { smdp.setError("min 8");
                        smdp.requestFocus();
                        return;
                    }
                    if (b.isEmpty())
                    {  cvmdp2.setError("mot de passe  obligatoire");
                        cvmdp2.requestFocus();
                        return;
                    }
                    if (!(b.equals(a)))
                    {  cvmdp2.setError(" verifier mot de passe ");
                        cvmdp2.requestFocus();
                        return;
                    }
               HashMap<String,Object> hashmap=new HashMap<>();
                hashmap.put("motsdepasse",smdp);
              /*  reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                {
                    Toast.makeText(changepassword.this,"yes",Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er->
                {
                    Toast.makeText(changepassword.this,"non",Toast.LENGTH_SHORT).show();
                });*/

                    startActivity(new Intent(changepassword.this, Activity_connexion.class));
                    finish();
                }
            });
    }
}