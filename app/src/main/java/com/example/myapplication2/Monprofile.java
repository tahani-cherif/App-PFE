package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Monprofile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monprofil);
        mAuth = FirebaseAuth.getInstance();
        Toolbar my_toolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        ImageView routeur=findViewById(R.id.retour);
        ImageView deconx=findViewById(R.id.dec);
        final TextView emailtext=findViewById(R.id.mailprofil);
        final TextView etattext=findViewById(R.id.etatprofil);
        final TextView prenomnom=findViewById(R.id.nomprenom);
        user=mAuth.getCurrentUser();
        userID=user.getUid();
        reference=FirebaseDatabase.getInstance().getReference("Users");
        routeur.setOnClickListener(v ->
                { startActivity(new Intent(this,MainActivity.class));
                    finish();}

        );
       deconx.setOnClickListener(v ->
               {    mAuth.signOut();
                   startActivity(new Intent(this, Connexion.class));
                   finish(); }
       );
       reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               utilisateur userprofil=snapshot.getValue(utilisateur.class);
               if (userprofil != null)
               {   String nom=userprofil.nom;
                   String prenom=userprofil.prenom;
                   String mailuser=userprofil.mail;
                  // String etatuser=userprofil.etat;
                   emailtext.setText(mailuser);
                   prenomnom.setText(nom+" "+prenom);
                 //  etattext.setText(etatuser);
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(Monprofile.this, "erreur", Toast.LENGTH_SHORT).show();
           }
       });

    }
}