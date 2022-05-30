package com.example.myapplication2.activity;

import static com.example.myapplication2.MainActivity.langue;
import static com.example.myapplication2.MainActivity.lastposition;
import static com.example.myapplication2.MainActivity.textToSpeech;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Activity_settingusername extends AppCompatActivity {
    private EditText changenom,changeprenom;
    private Button modifchange;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private static final int REQUEST_CODE_SPEECH_INPUT=1000;
    private ImageView voice;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_nomprenom);
        ImageView retournom=findViewById(R.id.retournom);
        changenom=findViewById(R.id.nomchange);
        changeprenom=findViewById(R.id.prenomchange);
        modifchange=findViewById(R.id.modifusename);
        voice=findViewById(R.id.voicenompre);
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });
        retournom.setOnClickListener(v ->
                { startActivity(new Intent(this, Acitivity_usersetting.class));
                    finish();
                    String b=getResources().getString(R.string.retouparcomp);
                    textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);}
        );
        modifchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modif();
            }
        });
    }

    public void modif(){
        String nomchange=changenom.getText().toString().trim();
        String prenomchange=changeprenom.getText().toString().trim();
        if(nomchange.isEmpty())
        {   String b=getResources().getString(R.string.champoblig);
            Toast.makeText(Activity_settingusername.this, b, Toast.LENGTH_SHORT).show();
            changenom.requestFocus();
            textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
            return;
        }
        if(prenomchange.isEmpty())
        {   String b=getResources().getString(R.string.champoblig);
            Toast.makeText(Activity_settingusername.this, b, Toast.LENGTH_SHORT).show();
            changeprenom.requestFocus();
            textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
            return;
        }
        progressDialog=new ProgressDialog(Activity_settingusername.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        HashMap<String,Object> hashmap=new HashMap<>();
        hashmap.put("nom",nomchange);
        hashmap.put("prenom",prenomchange);
        user=mAuth.getCurrentUser();
        userID=user.getUid();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
        {
            changenom.setText("");
            changeprenom.setText("");
            String b=getResources().getString(R.string.succe);
            textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
            Toast.makeText(this,b,Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }).addOnFailureListener(er->
        {     String b=getResources().getString(R.string.verifdonne);
            textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
            Toast.makeText(this,b,Toast.LENGTH_SHORT).show();
             progressDialog.dismiss(); });
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
                    if(x.equals("oui")||x.equals("yes")||x.equals("نعم"))
                    {  String b=getResources().getString(R.string.entnom);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        changenom.requestFocus();
                        if(!textToSpeech.isSpeaking())
                        {speak();}
                    }
                    else if(x.equals("prénom")||x.equals("last name")||x.equals("اللقب"))
                    {   String b=getResources().getString(R.string.entnompre);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        changeprenom.requestFocus();
                    }
                    else if(x.equals("modifier")||x.equals("edit")||x.equals("تعديل"))
                    {
                        modif();
                    }
                    else{
                        String b=getResources().getString(R.string.fal);
                        textToSpeech.speak(b,TextToSpeech.QUEUE_FLUSH,null);
                    }
                }
                break;
            }
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, Acitivity_usersetting.class));
        finish();
        String b=getResources().getString(R.string.retouparcomp);
        int speech=textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
        return;
    }
}