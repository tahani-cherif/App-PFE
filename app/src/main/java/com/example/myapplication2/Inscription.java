package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
public class Inscription extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText nom,prenom,mail,motsdepasse,vmotsdepasse;
    private Button inscription;
    private RadioButton malvoyant,nonvoyant;
    private TextView Rt;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        mAuth = FirebaseAuth.getInstance();
        TextView conx=findViewById(R.id.conx);
        nom=findViewById(R.id.nom);
        prenom=findViewById(R.id.prenom);
        mail=findViewById(R.id.mail);
        motsdepasse=findViewById(R.id.motsdepasse);
        vmotsdepasse=findViewById(R.id.vmotsdepasse);
        inscription=findViewById(R.id.inscription);
        malvoyant=findViewById(R.id.malvoyant);
        nonvoyant=findViewById(R.id.nonvoyant);
        Rt=findViewById(R.id.radiobutton);
        inscription.setOnClickListener(this);
        conx.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.conx:
                startActivity(new Intent(this, Connexion.class));
                finish();
                break;
            case R.id.inscription:
                inscription();
                break;
        }
    }

    private void inscription() {
        String nomenrg=nom.getText().toString().trim();
        String prenomenrg=prenom.getText().toString().trim();
        String mailenrg=mail.getText().toString().trim();
        String motsdepasseenrg=motsdepasse.getText().toString().trim();
        String vmotsdepasseenrg=vmotsdepasse.getText().toString().trim();
        String etatv=malvoyant.getText().toString().trim();
        String etatn=nonvoyant.getText().toString().trim();
        String etat="";
        if (nomenrg.isEmpty())
        { nom.setError("nom obligatoire");
          nom.requestFocus();

          return;
        }
        if (prenomenrg.isEmpty())
        { prenom.setError("prenom obligatoire");
            prenom.requestFocus();
            return;
        }
        if (mailenrg.isEmpty())
        { mail.setError("mail obligatoire");
            mail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mailenrg).matches())
        {   mail.setError("adresse ne sont pas valide");
            mail.requestFocus();
            return;
        }
        if (!malvoyant.isChecked() || !nonvoyant.isChecked()) {
                Rt.setError("etat obligatoire");
        }
        else{
            if(malvoyant.isChecked())
            {
                etat=etatv;
            }
            else
            {
                etat=etatn;
            }
        }

        if (motsdepasseenrg.isEmpty())
        { motsdepasse.setError("mot de passe  obligatoire");
           motsdepasse.requestFocus();
            return;
        }

        if (motsdepasseenrg.length()<8)
        { motsdepasse.setError("max 8");
            motsdepasse.requestFocus();
            return;
        }
        if (vmotsdepasseenrg.isEmpty())
        { vmotsdepasse.setError("mot de passe  obligatoire");
            vmotsdepasse.requestFocus();
            return;
        }
        if (!(motsdepasseenrg.equals(vmotsdepasseenrg)))
        { vmotsdepasse.setError(" verifier mot de passe ");
            vmotsdepasse.requestFocus();
            return;
        }
        String finalEtat = etat;
        mAuth.createUserWithEmailAndPassword(mailenrg,motsdepasseenrg)
             .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful())
                     {  utilisateur personne=new utilisateur(nomenrg,prenomenrg,mailenrg,motsdepasseenrg, finalEtat);
                         FirebaseDatabase.getInstance().getReference("Users")
                                 .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                 .setValue(personne).addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 if(task.isSuccessful())
                                 {   nom.setText("");
                                     prenom.setText("");
                                     mail.setText("");
                                     motsdepasse.setText("");
                                     vmotsdepasse.setText("");
                                     if(malvoyant.isChecked() || nonvoyant.isChecked())
                                     {   malvoyant.setChecked(false);
                                         nonvoyant.setChecked(false);
                                     }
                                     Toast.makeText(Inscription.this, "inscription effectuer", Toast.LENGTH_SHORT).show();
                                 }
                                 else{
                                     Toast.makeText(Inscription.this, "inscription ne sont pas enregistre", Toast.LENGTH_SHORT).show();
                                 }
                             }
                         });

                     }
                     else{
                         Toast.makeText(Inscription.this, "try again ", Toast.LENGTH_SHORT).show();
                     }
                 }
             });
    }
}