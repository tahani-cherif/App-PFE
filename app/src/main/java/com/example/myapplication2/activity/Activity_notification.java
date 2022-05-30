package com.example.myapplication2.activity;

import static com.example.myapplication2.MainActivity.langue;
import static com.example.myapplication2.MainActivity.lastposition;
import static com.example.myapplication2.MainActivity.notif;
import static com.example.myapplication2.MainActivity.textToSpeech;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Activity_notification extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT=1000;
    ImageView voice;
    SwitchMaterial notife;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mAuth = FirebaseAuth.getInstance();
        ImageView retournot=findViewById(R.id.retournot);
        notife=findViewById(R.id.notif);
        voice=findViewById(R.id.voicenotif);
      /*  SharedPreferences sharedPreferences2= getSharedPreferences("agenda", Context.MODE_PRIVATE);
        if (sharedPreferences2!= null) {
           int notif=sharedPreferences2.getInt("notif",1);
           if(notif==1)
           {
               SharedPreferences sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);
               notife.setChecked(sharedPreferences.getBoolean("test2",true));
           }
        }*/
        if(notif==1)
        {
            SharedPreferences.Editor editor=getSharedPreferences("test",MODE_PRIVATE).edit();
            editor.putBoolean("test2",true);
            editor.apply();
        }
        else{
            SharedPreferences.Editor editor=getSharedPreferences("test",MODE_PRIVATE).edit();
            editor.putBoolean("test2",false);
            editor.apply();
        }
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });
        retournot.setOnClickListener(v->
        {startActivity(new Intent(this, MainActivity.class));
            finish();
            String b=getResources().getString(R.string.retourAcc);
            textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);}
        );
        SharedPreferences sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);
        notife.setChecked(sharedPreferences.getBoolean("test2",true));
        user=mAuth.getCurrentUser();
        userID=user.getUid();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        notife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notife.isChecked())
                {
                    SharedPreferences.Editor editor=getSharedPreferences("test",MODE_PRIVATE).edit();
                    editor.putBoolean("test2",true);
                    editor.apply();
                    notife.setChecked(true);
                    String y=getResources().getString(R.string.actnotif);
                    Toast.makeText(Activity_notification.this,y, Toast.LENGTH_SHORT).show();
                    textToSpeech.speak(y, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("notif",1);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
                else{
                    SharedPreferences.Editor editor=getSharedPreferences("test",MODE_PRIVATE).edit();
                    editor.putBoolean("test2",false);
                    editor.apply();
                    notife.setChecked(false);
                    String a=getResources().getString(R.string.decnotif);
                    Toast.makeText(Activity_notification.this,a, Toast.LENGTH_SHORT).show();
                    textToSpeech.speak(a, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("notif",0);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
            }
        });
      /*  if(notife.isChecked())
        {
            SharedPreferences.Editor editor2=getSharedPreferences("notif",MODE_PRIVATE).edit();
            editor2.putInt("tt",1);
            editor2.apply();
        }
        else{
            SharedPreferences.Editor editor2=getSharedPreferences("notif",MODE_PRIVATE).edit();
            editor2.putInt("tt",1);
            editor2.apply();
        }*/

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
                    if(x.equals("désactiver notification")||x.equals("turn off notification")||x.equals("تعطيل الاشعارات"))
                    {   notife.setChecked(false);
                        String b=getResources().getString(R.string.decnotif);
                        int speech=textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                    }
                   else if(x.equals("activer notification")||x.equals("turn on notification")||x.equals("تفعيل الاشعارات"))
                    {   notife.setChecked(true);
                        String b=getResources().getString(R.string.actnotif);
                        int speech=textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
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
        startActivity(new Intent(this, MainActivity.class));
        finish();
        String b=getResources().getString(R.string.retourAcc);
        int speech=textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
        return;
    }
}