package com.example.myapplication2.activity;

import static com.example.myapplication2.MainActivity.langue;
import static com.example.myapplication2.MainActivity.lastposition;
import static com.example.myapplication2.MainActivity.textToSpeech;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.Locale;

public class Activity_Langue extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT=1000;
    ImageView voice;
    SwitchMaterial  fran,ang,arb;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_langue);
        ImageView retourlang=findViewById(R.id.retourlang);
        fran=findViewById(R.id.fran);
        ang=findViewById(R.id.ang);
        arb=findViewById(R.id.arb);
        voice=findViewById(R.id.voicelang);
        sharedPreferences=getSharedPreferences("lastsetting",MODE_PRIVATE);
        editor= sharedPreferences.edit();
        fran.setChecked(sharedPreferences.getBoolean("francais",true));
        ang.setChecked(sharedPreferences.getBoolean("anglais",false));
        arb.setChecked(sharedPreferences.getBoolean("arabe",false));
        if(langue==1)
        {   fran.setChecked(true);
            ang.setChecked(false);
            arb.setChecked(false);
            editor.putBoolean("francais",true);
            editor.putBoolean("arabe",false);
            editor.putBoolean("anglais",false);
            editor.apply();
            setAppLocale("fr");
            textToSpeech.setLanguage(Locale.FRENCH);
            editor.putInt("positionLangue",0).commit();
        }
        else if(langue==2)
        {     ang.setChecked(true);
            fran.setChecked(false);
            arb.setChecked(false);
            editor.putBoolean("francais",false);
            editor.putBoolean("arabe",true);
            editor.putBoolean("anglais",false);
            editor.apply();
            setAppLocale("en");
            textToSpeech.setLanguage(Locale.ENGLISH);
            editor.putInt("fra",2).commit();
            editor.putInt("positionLangue",1).commit();
        }
        else if(langue==3)
        {   arb.setChecked(true);
            fran.setChecked(false);
            ang.setChecked(false);
            editor.putBoolean("francais",false);
            editor.putBoolean("arabe",false);
            editor.putBoolean("anglais",true);
            editor.apply();
            setAppLocale("ar");
            textToSpeech.setLanguage(new Locale("ar_TN"));
            editor.putInt("fra",3).commit();
            editor.putInt("positionLangue",2).commit();
        }
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });
        fran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fran();
            }
        });
        ang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ang();
            }
        });
        arb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arb();
            }
        });
        retourlang.setOnClickListener(v->
        {startActivity(new Intent(this, MainActivity.class));
            finish();
            String b=getResources().getString(R.string.retourAcc);
            textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);});

    }
    public  void fran()
    {
        if(fran.isChecked())
        {
            textToSpeech.setLanguage(Locale.FRENCH);
            String b=getResources().getString(R.string.activfra);
            textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
            setAppLocale("fr");
            fran.setChecked(true);
            ang.setChecked(false);
            arb.setChecked(false);
            editor.putInt("fra",1);
            editor.putBoolean("francais",true);
            editor.putBoolean("arabe",false);
            editor.putBoolean("anglais",false);
            editor.putInt("positionLangue",0).commit();
            editor.apply();
        }
        else
        {
            String b=getResources().getString(R.string.activang);
            textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
            setAppLocale("en");
            textToSpeech.setLanguage(Locale.ENGLISH);
            fran.setChecked(false);
            ang.setChecked(true);
            arb.setChecked(false);
            editor.putInt("fra",2);
            editor.putBoolean("francais",false);
            editor.putBoolean("anglais",true);
            editor.putBoolean("arabe",false);
            editor.putInt("positionLangue",1).commit();
            editor.apply();
        }
    }
    public void ang()
    {
        if(ang.isChecked())
        {
            setAppLocale("en");
            textToSpeech.setLanguage(Locale.ENGLISH);
            String b=getResources().getString(R.string.activang);
            textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
            ang.setChecked(true);
            fran.setChecked(false);
            arb.setChecked(false);
            editor.putInt("fra",2);
            editor.putBoolean("francais",false);
            editor.putBoolean("anglais",true);
            editor.putBoolean("arabe",false);
            editor.putInt("positionLangue",1).commit();
            editor.apply();
        }
        else
        {
           textToSpeech.setLanguage(Locale.FRENCH);
            String b=getResources().getString(R.string.activfra);
            textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
            setAppLocale("fr");
            ang.setChecked(false);
            arb.setChecked(false);
            fran.setChecked(true);
            editor.putInt("fra",1);
            editor.putBoolean("francais",false);
            editor.putBoolean("anglais",true);
            editor.putBoolean("arabe",false);
            editor.putInt("positionLangue",0).commit();
            editor.apply();
        }
    }
    public void arb()
    {
        if(arb.isChecked())
        { textToSpeech.setLanguage(new Locale("ar_TN"));
            String b=getResources().getString(R.string.activarab);
            textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
            setAppLocale("ar");
            arb.setChecked(true);
            fran.setChecked(false);
            ang.setChecked(false);
            editor.putInt("fra",3);
            editor.putBoolean("francais",false);
            editor.putBoolean("arabe",true);
            editor.putBoolean("anglais",false);
            editor.putInt("positionLangue",2).commit();
            editor.apply();
        }
        else
        {
            textToSpeech.setLanguage(Locale.FRENCH);
            String b=getResources().getString(R.string.activfra);
            textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
            setAppLocale("fr");
            fran.setChecked(true);
            arb.setChecked(false);
            editor.putInt("fra",1);
            editor.putBoolean("francais",true);
            editor.putBoolean("arabe",false);
            editor.putBoolean("anglais",false);
            editor.putInt("positionLangue",0).commit();
            editor.apply();

        }
    }
    public void setAppLocale(String localecode)
    {
        Resources res=getResources();
        DisplayMetrics dm= res.getDisplayMetrics();
        Configuration conf= res.getConfiguration();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            conf.setLocale(new Locale(localecode.toLowerCase()));

        }else{
            conf.locale=new Locale((localecode.toLowerCase()));
        }
        res.updateConfiguration(conf,dm);
    }
    private  void speak()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        if(langue==1||lastposition ==0){
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"fr_FR");}
        else if(langue==2 || lastposition ==1){
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en-US"); }
        else if(langue==3|| lastposition ==2){
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ar");}
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
                {   ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String x=result.get(0);
                    if(x.equals("français")||x.equals("French")||x.equals("الفرنسيه"))
                    {   fran.setChecked(true);
                        ang.setChecked(false);
                        arb.setChecked(false);
                        editor.putBoolean("francais",true);
                        editor.putBoolean("arabe",false);
                        editor.putBoolean("anglais",false);
                        editor.apply();
                        setAppLocale("fr");
                        textToSpeech.setLanguage(Locale.FRENCH);
                        String b=getResources().getString(R.string.activfra);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        editor.putInt("fra",1).commit();
                        editor.putInt("positionLangue",0).commit();
                    }
                    else if(x.equals("anglais")||x.equals("English")||x.equals("الفرنسيه"))
                    {
                        fran.setChecked(false);
                        ang.setChecked(true);
                        arb.setChecked(false);
                        editor.putBoolean("francais",false);
                        editor.putBoolean("arabe",true);
                        editor.putBoolean("anglais",false);
                        editor.apply();
                        setAppLocale("en");
                        textToSpeech.setLanguage(Locale.ENGLISH);
                        String b=getResources().getString(R.string.activang);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        editor.putInt("positionLangue",1).commit();
                        editor.putInt("fra",2).commit();}
                    else if(x.equals("arabe")||x.equals("arab")||x.equals("الإنجليزيه"))
                    {
                        fran.setChecked(false);
                        ang.setChecked(false);
                        arb.setChecked(true);
                        editor.putBoolean("francais",false);
                        editor.putBoolean("arabe",false);
                        editor.putBoolean("anglais",true);
                        editor.apply();
                        setAppLocale("ar");
                        String b=getResources().getString(R.string.activarab);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        textToSpeech.setLanguage(new Locale("ar_TN"));
                        editor.putInt("positionLangue",2).commit();
                        editor.putInt("fra",3).commit();
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
        startActivity(new Intent(this, MainActivity.class));
        finish();
        String b=getResources().getString(R.string.retourAcc);
        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
        return;
    }

}