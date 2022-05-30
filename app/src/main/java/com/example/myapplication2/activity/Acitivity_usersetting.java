package com.example.myapplication2.activity;

import static com.example.myapplication2.MainActivity.langue;
import static com.example.myapplication2.MainActivity.lastposition;
import static com.example.myapplication2.MainActivity.textToSpeech;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;

public class Acitivity_usersetting extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT=1000;
    ImageView voice;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_para_generau_compte);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
       currentUser = mAuth.getCurrentUser();
        ImageView retourpara=findViewById(R.id.retourpara);
        RelativeLayout modefnom=findViewById(R.id.modefnom);
        RelativeLayout modefmot=findViewById(R.id.modefmot);
        final TextView nomprenom=findViewById(R.id.nometprenom);
        voice=findViewById(R.id.voicemodif);
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });
        retourpara.setOnClickListener(v ->
                { startActivity(new Intent(this, MainActivity.class));
                    finish();
                    String b=getResources().getString(R.string.retourAcc);
                    int speech=textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);}
        );
        modefnom.setOnClickListener(v ->
                {startActivity(new Intent(this, Activity_settingusername.class));
                    finish();
                    String b=getResources().getString(R.string.ouvrnom);
                    int speech=textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);}
        );
        modefmot.setOnClickListener(v ->
        {startActivity(new Intent(this, Activity_settingpassword.class));
            finish();
            String b=getResources().getString(R.string.ouvrmdp);
            int speech=textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
        }
        );
      if(currentUser!= null) {
            userID = currentUser.getUid(); //Do what you need to do with the id
            Log.d("Entered","yessss");
        }
        reference= FirebaseDatabase.getInstance().getReference().child("Users");
      reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot snapshot1:snapshot.getChildren()){
                   if(snapshot1.getKey().equals(userID)){
                utilisateur userprofil=snapshot1.getValue(utilisateur.class);
                 Log.d("Currentdate",userprofil.getMail());
                if (userprofil != null)
                {   String nom=userprofil.getNom();
                    String prenom=userprofil.getPrenom();
                    nomprenom.setText(nom +" "+prenom);
                }}}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Acitivity_usersetting.this, "erreur", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private  void speak()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        if(langue==1||lastposition ==0){
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"fr_FR");}
        else if(langue==2 || lastposition ==1){
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en-US"); }
        else if(langue==3|| lastposition ==2){  intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ar");}
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "");
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{
                if(resultCode==RESULT_OK && null!=data)
                {
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String x=result.get(0);
                    if(x.equals("nom et prénom") ||x.equals("last name first name")||x.equals("الاسم واللقب")  )
                    {  String b=getResources().getString(R.string.ouvrnom)+getResources().getString(R.string.remplirechamp);
                        int speech=textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        startActivity(new Intent(this, Activity_settingusername.class));
                        finish();
                    }
                    else if(x.equals("mot de passe")||x.equals("password")||x.equals("كلمه المرور"))
                    {    String b=getResources().getString(R.string.ouvrmdp)+getResources().getString(R.string.remplirechamp);
                        int speech=textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        startActivity(new Intent(this, Activity_settingpassword.class));
                        finish();
                    }
                    else{
                        String b=getResources().getString(R.string.fal);
                        int speech=textToSpeech.speak(b,TextToSpeech.QUEUE_FLUSH,null);
                    }

                }
                break;
            }
        }
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