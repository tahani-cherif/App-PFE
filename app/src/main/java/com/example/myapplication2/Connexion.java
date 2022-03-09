package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Connexion extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText mail,motsdepasse;
    private TextView insc,mdpo;
    private Button conxbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_connexion);
        mail=findViewById(R.id.rr);
        motsdepasse=findViewById(R.id.motsdepasse1);
        conxbutton=findViewById(R.id.buttonconx);
        insc=findViewById(R.id.insc);
        insc.setOnClickListener(this);
        conxbutton.setOnClickListener(this);
        mdpo=findViewById(R.id.mdpo);
        mdpo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.insc:
                startActivity(new Intent(this, Inscription.class));
                finish();
                break;
            case R.id.buttonconx:
                connexion();
                break;
            case  R.id.mdpo:
                startActivity(new Intent(this, MailmdpoublieActivity.class));

        }

    }

    private void connexion() {
        String mailconx=mail.getText().toString().trim();
        String motdepasseconx=motsdepasse.getText().toString().trim();
        if(mailconx.isEmpty())
        {  mail.setError("mail obliatoire");
            mail.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mailconx).matches())
        { mail.setError("adresse ne sont pas valide");
            mail.requestFocus();
            return;
        }

        if (motdepasseconx.isEmpty())
        { motsdepasse.setError("mot de passe  obligatoire");
            motsdepasse.requestFocus();
            return;
        }

        if (motdepasseconx.length()<8)
        { motsdepasse.setError("max 8");
            motsdepasse.requestFocus();
            return;
        }
         mAuth.signInWithEmailAndPassword(mailconx,motdepasseconx)
                 .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful())
                         {
                             startActivity(new Intent(Connexion.this, MainActivity.class));
                             finish();
                         }
                         else {
                             Toast.makeText(Connexion.this, "err conx", Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
    }
}