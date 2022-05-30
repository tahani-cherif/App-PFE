package com.example.myapplication2.activity;

import static com.example.myapplication2.MainActivity.textToSpeech;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;
import com.example.myapplication2.utilisateur;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_userprofil extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monprofil);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Toolbar my_toolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        ImageView routeur=findViewById(R.id.retour);
        ImageView deconx=findViewById(R.id.dec);
        final TextView emailtext=findViewById(R.id.emailprofil);
        final TextView etattext=findViewById(R.id.etatprofil);
        final TextView prenomnom=findViewById(R.id.nomprofil);

//        userID=user.getUid();
        currentUser = mAuth.getCurrentUser();


        if(currentUser!= null) {
            userID = currentUser.getUid(); //Do what you need to do with the id
            Log.d("Entered","yessss");
        }
        routeur.setOnClickListener(v ->
                { startActivity(new Intent(this, MainActivity.class));
                    finish();}

        );
        deconx.setOnClickListener(v ->
                {    mAuth.signOut();
                    startActivity(new Intent(this, Activity_connexion.class));
                    finish(); }
        );
        reference= FirebaseDatabase.getInstance().getReference().child("Users");
      reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
               if(snapshot1.getKey().equals(userID)){
                utilisateur userprofil=snapshot1.getValue(utilisateur.class);
                if (userprofil != null)
                {   String nom=userprofil.getNom();
                    String prenom=userprofil.getPrenom();
                    String mailuser=userprofil.getMail();
                    String etatuser=userprofil.getEtat();
                    emailtext.setText(mailuser);
                    prenomnom.setText(nom+" "+prenom);
                    etattext.setText(etatuser);
                    String b=nom+prenom+mailuser+etatuser;
                    textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
               //     Log.d("Currentdate",userprofil.getMail());
                }}}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Activity_userprofil.this, "erreur", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onBackPressed() {
        String b=getResources().getString(R.string.retourAcc);
        finish();
        int speech=textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
        startActivity(new Intent(this,MainActivity.class));
        return;
    }
}