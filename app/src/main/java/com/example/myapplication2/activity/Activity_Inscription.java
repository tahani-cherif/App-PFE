package com.example.myapplication2.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.R;
import com.example.myapplication2.utilisateur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
public class Activity_Inscription extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText nom,prenom,mail,motsdepasse,vmotsdepasse;
    private Button inscription;
    private RadioButton malvoyant,nonvoyant;
    private TextView Rt,mConnect;
    private String etat="";
    ProgressDialog progressDialog;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        initView();
        initClickListener();
    }

    private void initClickListener() {
        inscription.setOnClickListener(this);
        mConnect.setOnClickListener(this);
    }

    private void initView() {
        mAuth = FirebaseAuth.getInstance();
        mConnect=findViewById(R.id.conx);
        nom=findViewById(R.id.nom);
        prenom=findViewById(R.id.prenom);
        mail=findViewById(R.id.mail);
        motsdepasse=findViewById(R.id.motsdepasse);
        vmotsdepasse=findViewById(R.id.vmotsdepasse);
        inscription=findViewById(R.id.inscription);
        malvoyant=findViewById(R.id.malvoyant);
        nonvoyant=findViewById(R.id.nonvoyant);
        Rt=findViewById(R.id.radiobutton);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.conx:
                startActivity(new Intent(this, Activity_connexion.class));
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
        String etatv="malvoyant";
        String etatn="non voyant";
        etat="";
        if (nomenrg.isEmpty())
        {
            Toast.makeText(this, "champ obligatoire", Toast.LENGTH_SHORT).show();
            nom.requestFocus();

          return;
        }
        if (prenomenrg.isEmpty())
        {   Toast.makeText(this, "champ obligatoire", Toast.LENGTH_SHORT).show();
            prenom.requestFocus();
            return;
        }
        if (mailenrg.isEmpty())
        {   Toast.makeText(this, "champ obligatoire", Toast.LENGTH_SHORT).show();
            mail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mailenrg).matches())
        {    Toast.makeText(this, "adresse non valide", Toast.LENGTH_SHORT).show();
            mail.requestFocus();
            return;
        }

            if(malvoyant.isChecked())
            {
                etat=etatv;
                Log.d("CurrentState",etatv);
            }
            else if (nonvoyant.isChecked())
            {
                etat=etatn;
                Log.d("CurrentState",etatn);
            }
            else {
                Toast.makeText(this, "etat obligatoire", Toast.LENGTH_SHORT).show();
            }
        if (motsdepasseenrg.isEmpty())
        {  Toast.makeText(this, "champ obligatoire", Toast.LENGTH_SHORT).show();
           motsdepasse.requestFocus();
            return;
        }
        if (motsdepasseenrg.length()<8)
        {   Toast.makeText(this, "mot de passe doit contenir au minimum 8 caractère ", Toast.LENGTH_SHORT).show();
            motsdepasse.requestFocus();
            return;
        }
        if (vmotsdepasseenrg.isEmpty())
        {   Toast.makeText(this, "champ obligatoire", Toast.LENGTH_SHORT).show();
            vmotsdepasse.requestFocus();
            return;
        }
        if (!(motsdepasseenrg.equals(vmotsdepasseenrg)))
        {
            Toast.makeText(this, "verifier mot de passe", Toast.LENGTH_SHORT).show();
            vmotsdepasse.requestFocus();
            return;
        }
        progressDialog=new ProgressDialog(Activity_Inscription.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mAuth.createUserWithEmailAndPassword(mailenrg,motsdepasseenrg)
             .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful())
                     {  utilisateur personne=new utilisateur(nomenrg,prenomenrg,mailenrg,motsdepasseenrg
                             ,etat,1,1,1,1,1,1,1,1,1,1);
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
                                     Toast.makeText(Activity_Inscription.this, "inscription effectuer", Toast.LENGTH_SHORT).show();
                                     progressDialog.dismiss();
                                 } else{
                                     Toast.makeText(Activity_Inscription.this, "Inscription non réussite", Toast.LENGTH_SHORT).show();
                                     progressDialog.dismiss();
                                 } }}); }
                     else{
                         Toast.makeText(Activity_Inscription.this, "réessayer", Toast.LENGTH_SHORT).show();
                     } }});
    }
}