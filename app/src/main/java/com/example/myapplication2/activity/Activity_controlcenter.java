package com.example.myapplication2.activity;

import static com.example.myapplication2.MainActivity.REQUEST_CODE_SPEECH_INPUT;
import static com.example.myapplication2.MainActivity.agen;
import static com.example.myapplication2.MainActivity.camera;
import static com.example.myapplication2.MainActivity.contact;
import static com.example.myapplication2.MainActivity.gamil;
import static com.example.myapplication2.MainActivity.horloge;
import static com.example.myapplication2.MainActivity.langue;
import static com.example.myapplication2.MainActivity.lastposition;
import static com.example.myapplication2.MainActivity.map;
import static com.example.myapplication2.MainActivity.messages;
import static com.example.myapplication2.MainActivity.photo;
import static com.example.myapplication2.MainActivity.telephone;
import static com.example.myapplication2.MainActivity.textToSpeech;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.CompoundButton;
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

public class Activity_controlcenter extends AppCompatActivity {
    private ImageView voicecc;
    private SwitchMaterial agendas,gmails,cameras,contactS,horloges,telephones,messagess,mapss,photos;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centrecontrole10);
        ImageView retourcent=findViewById(R.id.retourcent);
        mAuth = FirebaseAuth.getInstance();
        agendas=findViewById(R.id.agenda);
        gmails=findViewById(R.id.gmail);
        cameras=findViewById(R.id.camera);
        contactS=findViewById(R.id.contacts);
        horloges=findViewById(R.id.horloge);
        telephones=findViewById(R.id.telephone);
        messagess=findViewById(R.id.messages);
        mapss=findViewById(R.id.map);
        photos=findViewById(R.id.photo);
        voicecc=findViewById(R.id.voicecc);
        SharedPreferences sharedPreferences = getSharedPreferences("agenda", MODE_PRIVATE);
        gmails.setChecked(sharedPreferences.getBoolean("gmail1",true));
        cameras.setChecked(sharedPreferences.getBoolean("camera1",true));
        contactS.setChecked(sharedPreferences.getBoolean("contact1",true));
        horloges.setChecked(sharedPreferences.getBoolean("horloge1",true));
        telephones.setChecked(sharedPreferences.getBoolean("telephone1",true));
        messagess.setChecked(sharedPreferences.getBoolean("message1",true));
        mapss.setChecked(sharedPreferences.getBoolean("map1",true));
        photos.setChecked(sharedPreferences.getBoolean("photo1",true));
        agendas.setChecked(sharedPreferences.getBoolean("agen1",true));
        if(agen==1)
        { SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
            editor.putBoolean("agen1",true);
            editor.apply();}
        else{
            SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
            editor.putBoolean("agen1",false);
            editor.apply();
        }
        if(gamil==1)
        {
            SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
            editor.putBoolean("gmail1",true);
            editor.apply();
        }
        else{
            SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
            editor.putBoolean("gmail1",false);
            editor.apply();
        }
        if(camera==1)
        {
            SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
            editor.putBoolean("camera1",true);
            editor.apply();
        }else{
            SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
            editor.putBoolean("camera1",false);
            editor.apply();
        }
        if(contact==1)
        {
            SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
            editor.putBoolean("contact1",true);
            editor.apply();
        }
        else{
            SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
            editor.putBoolean("contact1",false);
            editor.apply();
        }
        if(horloge==1)
        {
            SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
            editor.putBoolean("horloge",true);
            editor.apply();
        }else{
            SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
            editor.putBoolean("horloge",false);
            editor.apply();
        }
        if(telephone==1)
        {
            SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
            editor.putBoolean("telephone1",true);
            editor.apply();
        }else{
            SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
            editor.putBoolean("telephone1",false);
            editor.apply();
        }
        if(messages==1)
        {SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
            editor.putBoolean("message1",true);
            editor.apply();}
        else{SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
            editor.putBoolean("message1",false);
            editor.apply();}
        if(map==1)
        {
            SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
            editor.putBoolean("map1",true);
            editor.apply();
        }else{
            SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
            editor.putBoolean("map1",false);
            editor.apply();
        }
        if(photo==1)
        { SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
            editor.putBoolean("photo1",true);
            editor.apply();}
        else{ SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
            editor.putBoolean("photo1",false);
            editor.apply();}

        user=mAuth.getCurrentUser();
        userID=user.getUid();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        agendas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                    editor.putBoolean("agen1",true);
                    editor.apply();
                    String x=getResources().getString(R.string.app)+getResources().getString(R.string.agenda)
                            +getResources().getString(R.string.activerr);
                    textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("agen",1);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
                else
                {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                    editor.putBoolean("agen1",false);
                    editor.apply();
                    String x=getResources().getString(R.string.app)+getResources().getString(R.string.agenda)
                        +getResources().getString(R.string.desa);
                    textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("agen",0);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
                }
        });
        gmails.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {  SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                    editor.putBoolean("gmail1",true);
                    editor.apply();
                    String x=getResources().getString(R.string.app)+getResources().getString(R.string.GMAIL)
                        +getResources().getString(R.string.activerr);
                    textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("gamil",1);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
                else
                {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                    editor.putBoolean("gmail1",false);
                    editor.apply();
                    String x=getResources().getString(R.string.app)+getResources().getString(R.string.GMAIL)
                        +getResources().getString(R.string.desa);
                    textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("gamil",0);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
            }

        });
              cameras.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                    editor.putBoolean("camera1",true);
                    editor.apply();
                    String x=getResources().getString(R.string.app)+getResources().getString(R.string.camera)
                        +getResources().getString(R.string.activerr);
                    textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("camera",1);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
                else
                {    SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                    editor.putBoolean("camera1",false);
                    editor.apply();
                    String x=getResources().getString(R.string.app)+getResources().getString(R.string.camera)
                        +getResources().getString(R.string.desa);
                    textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("camera",0);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
            }
        });
        contactS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                    editor.putBoolean("contact1",true);
                    editor.apply();
                    String x=getResources().getString(R.string.app)+getResources().getString(R.string.contact)
                        +getResources().getString(R.string.activerr);
                    textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("contact",1);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
                else
                {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                    editor.putBoolean("contact1",false);
                    editor.apply();
                    String x=getResources().getString(R.string.app)+getResources().getString(R.string.contact)
                        +getResources().getString(R.string.desa);
                    textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("contact",0);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
            }
        });
        horloges.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                    editor.putBoolean("horloge",true);
                    editor.apply();
                    String x=getResources().getString(R.string.app)+getResources().getString(R.string.hor)
                        +getResources().getString(R.string.activerr);
                    textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("horloge",1);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
                else
                { SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                    editor.putBoolean("horloge",false);
                    editor.apply();
                    String x=getResources().getString(R.string.app)+getResources().getString(R.string.hor)
                        +getResources().getString(R.string.desa);
                    textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("horloge",0);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
            }
        });
        telephones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                    editor.putBoolean("telephone1",true);
                    editor.apply();
                    String x=getResources().getString(R.string.app)+getResources().getString(R.string.telephone)
                        +getResources().getString(R.string.activerr);
                    textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("telephone",1);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
                else
                {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                    editor.putBoolean("telephone1",false);
                    editor.apply();
                    String x=getResources().getString(R.string.app)+getResources().getString(R.string.telephone)
                        +getResources().getString(R.string.desa);
                    textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("telephone",0);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
            }
        });
        messagess.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                    editor.putBoolean("message1",true);
                    editor.apply();
                    String x=getResources().getString(R.string.app)+getResources().getString(R.string.msg)
                        +getResources().getString(R.string.activerr);
                    textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("messages",1);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
                else
                {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                    editor.putBoolean("message1",false);
                    editor.apply();
                    String x=getResources().getString(R.string.app)+getResources().getString(R.string.msg)
                        +getResources().getString(R.string.desa);
                    textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("messages",0);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });

                }
            }
        });
        mapss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {  SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                    editor.putBoolean("map1",true);
                    editor.apply();
                    String x=getResources().getString(R.string.app)+getResources().getString(R.string.map)
                        +getResources().getString(R.string.activerr);
                    textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("map",1);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
                else
                {  SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                    editor.putBoolean("map1",false);
                    editor.apply();
                    String x=getResources().getString(R.string.app)+getResources().getString(R.string.map)
                        +getResources().getString(R.string.desa);
                    textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("map",0);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
            }
        });
        photos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                    editor.putBoolean("photo1",true);
                    editor.apply();
                    String x=getResources().getString(R.string.app)+getResources().getString(R.string.photo)
                        +getResources().getString(R.string.activerr);
                    textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("photo",1);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
                else
                {  SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                    editor.putBoolean("photo1",false);
                    editor.apply();
                    String x=getResources().getString(R.string.app)+getResources().getString(R.string.photo)
                        +getResources().getString(R.string.desa);
                   textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH,null);
                    HashMap<String,Object> hashmap=new HashMap<>();
                    hashmap.put("photo",0);
                    reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                    {
                    }).addOnFailureListener(er->
                    { });
                }
            }
        });
        retourcent.setOnClickListener(v->
                { startActivity(new Intent(this, MainActivity.class));
                    finish();
                    String b=getResources().getString(R.string.retourAcc);
                    textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);}
        );
        voicecc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        String b=getResources().getString(R.string.retourAcc);
       textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
        return;
    }

    private void speak() {
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
                    if(x.equals("activer agenda")||x.equals("turn on calendar")||x.equals("تفعيل التقويم"))
                    {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                        editor.putBoolean("agen1",true);
                        editor.apply();
                      agendas.setChecked(true);
                        String b=getResources().getString(R.string.app)+getResources().getString(R.string.agenda)
                                +getResources().getString(R.string.activerr);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        HashMap<String,Object> hashmap=new HashMap<>();
                        hashmap.put("agen",1);
                        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                        {
                        }).addOnFailureListener(er->
                        { });
                    }
                    else if(x.equals("activer Gmail")||x.equals("turn on Gmail")||x.equals("تفعيل البريد"))
                    {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                        editor.putBoolean("gmail1",true);
                        editor.apply();
                        String b=getResources().getString(R.string.app)+getResources().getString(R.string.GMAIL)
                                +getResources().getString(R.string.activerr);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        gmails.setChecked(true);
                        HashMap<String,Object> hashmap=new HashMap<>();
                        hashmap.put("gmail",1);
                        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                        {
                        }).addOnFailureListener(er->
                        { });

                    }
                    else if(x.equals("activer caméra")||x.equals("turn on camera")||x.equals("تفعيل الكاميرا"))
                    {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                        editor.putBoolean("camera1",true);
                        editor.apply();
                        String b=getResources().getString(R.string.app)+getResources().getString(R.string.camera)
                            +getResources().getString(R.string.activerr);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        cameras.setChecked(true);
                        HashMap<String,Object> hashmap=new HashMap<>();
                        hashmap.put("camera",1);
                        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                        {
                        }).addOnFailureListener(er->
                        { });
                    }
                    else if(x.equals("activer contact")||x.equals("turn on contact")||x.equals("تفعيل جهات الاتصال"))
                    {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                        editor.putBoolean("contact1",true);
                        editor.apply();
                        String b=getResources().getString(R.string.app)+getResources().getString(R.string.contact)
                            +getResources().getString(R.string.activerr);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        contactS.setChecked(true);
                        HashMap<String,Object> hashmap=new HashMap<>();
                        hashmap.put("contact",1);
                        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                        {
                        }).addOnFailureListener(er->
                        { });
                    }
                    else if(x.equals("activer horloge")||x.equals("turn on clock")||x.equals("تفعيل الساعه"))
                    {SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                        editor.putBoolean("horloge",true);
                        editor.apply();
                        String b=getResources().getString(R.string.app)+getResources().getString(R.string.hor)
                            +getResources().getString(R.string.activerr);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        horloges.setChecked(true);
                        HashMap<String,Object> hashmap=new HashMap<>();
                        hashmap.put("horloge",1);
                        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                        {
                        }).addOnFailureListener(er->
                        { });
                    }
                    else if(x.equals("activer téléphone")||x.equals("turn on phone")||x.equals("تفعيل الهاتف"))
                    {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                        editor.putBoolean("telephone1",true);
                        editor.apply();
                        String b=getResources().getString(R.string.app)+getResources().getString(R.string.telephone)
                            +getResources().getString(R.string.activerr);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        telephones.setChecked(true);
                        HashMap<String,Object> hashmap=new HashMap<>();
                        hashmap.put("telephone",1);
                        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                        {
                        }).addOnFailureListener(er->
                        { });
                    }
                    else if(x.equals("activer message")||x.equals("turn on message")||x.equals("تفعيل الرسائل"))
                    {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                        editor.putBoolean("message1",true);
                        editor.apply();
                        String b=getResources().getString(R.string.app)+getResources().getString(R.string.msg)
                            +getResources().getString(R.string.activerr);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        messagess.setChecked(true);
                        HashMap<String,Object> hashmap=new HashMap<>();
                        hashmap.put("messages",1);
                        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                        {
                        }).addOnFailureListener(er->
                        { });
                    }
                    else if(x.equals("activer map")||x.equals("turn on maps")||x.equals("تفعيل الخرائط"))
                    {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                        editor.putBoolean("map1",true);
                        editor.apply();
                        String b=getResources().getString(R.string.app)+getResources().getString(R.string.map)
                            +getResources().getString(R.string.activerr);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        mapss.setChecked(true);
                        HashMap<String,Object> hashmap=new HashMap<>();
                        hashmap.put("map",1);
                        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                        {
                        }).addOnFailureListener(er->
                        { });
                    }
                    else if(x.equals("activer photos")||x.equals("turn on photo")||x.equals("تفعيل الصور"))
                    {  SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                        editor.putBoolean("photo1",true);
                        editor.apply();
                        String b=getResources().getString(R.string.app)+getResources().getString(R.string.photo)
                            +getResources().getString(R.string.activerr);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        photos.setChecked(true);
                        HashMap<String,Object> hashmap=new HashMap<>();
                        hashmap.put("photo",1);
                        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                        {
                        }).addOnFailureListener(er->
                        { });
                    }
                    /////
                    else if(x.equals("désactiver agenda")||x.equals("turn off calendar")||x.equals("تعطيل التقويم"))
                    {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                        editor.putBoolean("agen1",false);
                        editor.apply();
                        agendas.setChecked(false);
                        String b=getResources().getString(R.string.app)+getResources().getString(R.string.agenda)
                                +getResources().getString(R.string.desa);
                       textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        HashMap<String,Object> hashmap=new HashMap<>();
                        hashmap.put("agen",0);
                        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                        {
                        }).addOnFailureListener(er->
                        { });
                    }
                  else if(x.equals("désactiver Gmail")||x.equals("turn off Gmail")||x.equals("تعطيل البريد"))
                    {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                        editor.putBoolean("gmail1",false);
                        editor.apply();
                        String b=getResources().getString(R.string.app)+getResources().getString(R.string.GMAIL)
                            +getResources().getString(R.string.desa);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        gmails.setChecked(false);
                        HashMap<String,Object> hashmap=new HashMap<>();
                        hashmap.put("gmail",0);
                        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                        {
                        }).addOnFailureListener(er->
                        { });
                    }
                    else if(x.equals("désactiver caméra")||x.equals("turn off camera")||x.equals("تعطيل الكاميرا"))
                    {  SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                        editor.putBoolean("camera1",false);
                        editor.apply();
                        String b=getResources().getString(R.string.app)+getResources().getString(R.string.camera)
                            +getResources().getString(R.string.desa);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        cameras.setChecked(false);
                        HashMap<String,Object> hashmap=new HashMap<>();
                        hashmap.put("camera",0);
                        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                        {
                        }).addOnFailureListener(er->
                        { });
                    }
                    else if(x.equals("désactiver contact")||x.equals("turn off contact")||x.equals("تعطيل جهات الاتصال"))
                    {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                        editor.putBoolean("contact1",false);
                        editor.apply();
                        String b=getResources().getString(R.string.app)+getResources().getString(R.string.contact)
                            +getResources().getString(R.string.desa);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        contactS.setChecked(false);
                        HashMap<String,Object> hashmap=new HashMap<>();
                        hashmap.put("contact",0);
                        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                        {
                        }).addOnFailureListener(er->
                        { });
                    }
                    else if(x.equals("désactiver horloge")||x.equals("turn off clock")||x.equals("تعطيل الساعه"))
                    {  SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                        editor.putBoolean("horloge",false);
                        editor.apply();
                        String b=getResources().getString(R.string.app)+getResources().getString(R.string.hor)
                            +getResources().getString(R.string.desa);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        horloges.setChecked(false);
                        HashMap<String,Object> hashmap=new HashMap<>();
                        hashmap.put("horloge",0);
                        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                        {
                        }).addOnFailureListener(er->
                        { });
                    }
                    else if(x.equals("désactiver téléphone")||x.equals("turn off phone")||x.equals("تعطيل الهاتف"))
                    {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                        editor.putBoolean("telephone1",false);
                        editor.apply();
                        String b=getResources().getString(R.string.app)+getResources().getString(R.string.telephone)
                            +getResources().getString(R.string.desa);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        telephones.setChecked(false);
                        HashMap<String,Object> hashmap=new HashMap<>();
                        hashmap.put("telephone",0);
                        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                        {
                        }).addOnFailureListener(er->
                        { });
                    }
                    else if(x.equals("désactiver message")||x.equals("turn off message")||x.equals("تعطيل الرسائل"))
                    {   SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                        editor.putBoolean("message1",false);
                        editor.apply();
                        String b=getResources().getString(R.string.app)+getResources().getString(R.string.msg)
                            +getResources().getString(R.string.desa);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        messagess.setChecked(false);
                        HashMap<String,Object> hashmap=new HashMap<>();
                        hashmap.put("messages",0);
                        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                        {
                        }).addOnFailureListener(er->
                        { });
                    }

                    else if(x.equals("désactiver map")||x.equals("turn off maps")||x.equals("تعطيل الخرائط"))
                    {  SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                        editor.putBoolean("map1",false);
                        editor.apply();
                        String b=getResources().getString(R.string.app)+getResources().getString(R.string.map)
                            +getResources().getString(R.string.desa);
                       textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        mapss.setChecked(false);
                        HashMap<String,Object> hashmap=new HashMap<>();
                        hashmap.put("map",0);
                        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                        {
                        }).addOnFailureListener(er->
                        { });
                    }
                    else if(x.equals("désactiver photos")||x.equals("turn off photo")||x.equals("تعطيل الصور"))
                    {  SharedPreferences.Editor editor=getSharedPreferences("agenda",MODE_PRIVATE).edit();
                        editor.putBoolean("photo1",false);
                        editor.apply();
                        String b=getResources().getString(R.string.app)+getResources().getString(R.string.photo)
                            +getResources().getString(R.string.desa);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        photos.setChecked(false);
                        HashMap<String,Object> hashmap=new HashMap<>();
                        hashmap.put("photo",0);
                        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
                        {
                        }).addOnFailureListener(er->
                        { });
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
   /* @Override
    protected void onPause() {
        if(textToSpeech !=null || textToSpeech.isSpeaking()||lg==0)
        {
            textToSpeech.stop();
        }
        super.onPause();
    }*/
}