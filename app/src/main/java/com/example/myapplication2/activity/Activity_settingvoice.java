package com.example.myapplication2.activity;

import static com.example.myapplication2.MainActivity.REQUEST_CODE_SPEECH_INPUT;
import static com.example.myapplication2.MainActivity.langue;
import static com.example.myapplication2.MainActivity.lastposition;
import static com.example.myapplication2.MainActivity.textToSpeech;
import static com.example.myapplication2.MainActivity.vite;
import static com.example.myapplication2.MainActivity.vitesse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;

import java.util.ArrayList;
import java.util.Locale;

public class Activity_settingvoice extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    SeekBar seekBar;
    public static String selected_value = "", selected_valuevoice = "";
    public static int lg=1;
    ArrayAdapter<CharSequence> adapter,adapter2;
    public static Spinner spinnerLangue, spinnerVoix;
    ImageView voice;
    public static float speed;
    TextView test;
    SharedPreferences lastselect,lastselect2;
    SharedPreferences.Editor editor,editor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paravoix);

        lastselect=getSharedPreferences("lastsetting", Context.MODE_PRIVATE);
        editor=lastselect.edit();
        lastselect2=getSharedPreferences("lastsetting2", Context.MODE_PRIVATE);
        editor2=lastselect2.edit();
        spinnerLangue = findViewById(R.id.spinner_langue);
        spinnerVoix = findViewById(R.id.spinner2);
        seekBar = findViewById(R.id.seekbar);
        voice=findViewById(R.id.voiceparvoice);
        test=findViewById(R.id.speed2);
        adapter = ArrayAdapter.createFromResource(this, R.array.spinner1, R.layout.my_size_item);
        adapter.setDropDownViewResource(R.layout.my_dropdwon_item);
        spinnerLangue.setAdapter(adapter);
        spinnerLangue.setOnItemSelectedListener(this);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.spinner2, R.layout.my_size_item);
        adapter2.setDropDownViewResource(R.layout.my_dropdwon_item);
        spinnerVoix.setAdapter(adapter2);


          test.setText(vite+"/100");

        textToSpeech.setSpeechRate(vitesse);
        getLastPosition();
        spinnerVoix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_valuevoice = adapterView.getItemAtPosition(i).toString();
                editor2.putString("voice2",selected_valuevoice).commit();
                editor.putInt("positionvoice",i).commit();
                Integer lastposition =  lastselect.getInt("positionLangue",-1);
                if(lastposition==0){
                  if(i==0){
                    textToSpeech.setLanguage(Locale.FRENCH);
                   }else{ textToSpeech.setLanguage(Locale.CANADA_FRENCH);}}
            else if(lastposition==1){
                if(i==1)
                {
                    Locale loc = new Locale ("en", "AU");
                   textToSpeech.setLanguage(loc);
                }else{textToSpeech.setLanguage(Locale.ENGLISH);}}


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });
        ImageView retour = findViewById(R.id.retourpar);
        retour.setOnClickListener(v ->
                {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                    String b =getResources().getString(R.string.retourAcc);
                    textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH, null);
                }
        );
        Log.d("zz", String.valueOf(lg));
         seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
             @Override
             public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                 editor.putInt("SPEED",i).commit();
                if(90<=i && i<100){
                     speed =i/10F;
                    textToSpeech.setSpeechRate(speed);
                    editor.putFloat("vitesse",speed).commit();
                 }else if(70<=i&& i<90)
                 {
                     speed =i/30F;
                     textToSpeech.setSpeechRate(speed);
                     editor.putFloat("vitesse",speed).commit();
                 }else if(50<=i && i<70)
                 {
                     speed =i/50F;
                     textToSpeech.setSpeechRate(speed);
                     editor.putFloat("vitesse",speed).commit();
                 }else if(30<=i && i<50)
                 {
                     speed = i/60F;
                     textToSpeech.setSpeechRate(speed);
                     editor.putFloat("vitesse",speed).commit();
                 }else if(10<=i && i<30)
                 {
                     speed = i/80F;
                     textToSpeech.setSpeechRate(speed);
                     editor.putFloat("vitesse",speed).commit();
                 }else if(0<=i && i<10)
                 {
                     speed = i/100F;
                     textToSpeech.setSpeechRate(speed);
                     editor.putFloat("vitesse",speed).commit();
                 }
                test.setText(String.valueOf(i)+"/100");


             }

             @Override
             public void onStartTrackingTouch(SeekBar seekBar) {

             }

             @Override
             public void onStopTrackingTouch(SeekBar seekBar) {

             }
         });

        Integer lastposition2 =  lastselect.getInt("positionvoice",-1);

        if (lastposition2 == 0) {
            spinnerVoix.setSelection(0);
            if(langue==1)
            {textToSpeech.setLanguage(Locale.FRENCH);
            }else if(langue==2)
            {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        }
        else if(lastposition2==1){
            spinnerVoix.setSelection(1);
            if(langue==1)
            {
                textToSpeech.setLanguage(Locale.CANADA_FRENCH);
            }else if(langue==2){
                Locale loc = new Locale ("en", "AU");
                textToSpeech.setLanguage(loc);
            }

        }
        if(langue==1)
        {
            setAppLocale("fr");
            spinnerLangue.setSelection(0);
            editor.putString("voice","Francais").commit();
            editor.putInt("positionLangue",0).commit();
        }
        else if(langue==2)
        {
            setAppLocale("en");
            spinnerLangue.setSelection(1);
            editor.putString("voice","Francais").commit();
            editor.putInt("positionLangue",1).commit();
        } else if(langue==3)
        {
            setAppLocale("ar");
            spinnerLangue.setSelection(2);
            editor.putString("voice","Francais").commit();
            editor.putInt("positionLangue",2).commit();
        }

    }

    private void getLastPosition() {
        Integer lastposition =  lastselect.getInt("positionLangue",-1);
        if (lastposition == 1) {
            textToSpeech.setLanguage(Locale.ENGLISH);
            spinnerLangue.setSelection(1);
            setAppLocale("en");
            editor.putString("voice","Francais").commit();
            editor.putInt("positionLangue",1).commit();
            editor.putInt("fra",2).commit();
        }
        else if(lastposition==2){
            textToSpeech.setLanguage(new Locale("ar_EG"));
            spinnerLangue.setSelection(2);
            setAppLocale("ar");
            editor.putString("voice","Francais").commit();
            editor.putInt("positionLangue",2).commit();
            editor.putInt("fra",3).commit();
        }
        else if(lastposition==0)
        {
            textToSpeech.setLanguage(Locale.FRENCH);
            spinnerLangue.setSelection(0);
            setAppLocale("fr");
            editor.putString("voice","Francais").commit();
            editor.putInt("positionLangue",0).commit();
            editor.putInt("fra",1).commit();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected_value = parent.getItemAtPosition(position).toString();
        Log.d("sp", selected_value);
        if (selected_value.equals("Francais") || selected_value.equals("French") || selected_value.equals("الفرنسية")) {
            //textToSpeech.setLanguage(Locale.FRENCH);
            setAppLocale("fr");
            editor.putString("voice","Francais").commit();
            editor.putInt("positionLangue",position).commit();
            editor.putInt("fra",1).commit();
            Integer lastposition =  lastselect.getInt("positionvoice",-1);
            if(lastposition==0){
                textToSpeech.setLanguage(Locale.FRENCH);
            }else{ textToSpeech.setLanguage(Locale.CANADA_FRENCH);}
        } else if (selected_value.equals("Anglais") || selected_value.equals("English") || selected_value.equals("الإنجليزية")) {
            setAppLocale("en");
            editor.putString("voice","Anglais").commit();
            editor.putInt("positionLangue",position).commit();
            editor.putInt("fra",2).commit();
            Integer lastposition =  lastselect.getInt("positionvoice",-1);
            if(lastposition==0){
                textToSpeech.setLanguage(Locale.ENGLISH);;
            }else{ Locale loc = new Locale ("en", "AU");
                textToSpeech.setLanguage(loc);}
        } else if (selected_value.equals("Arabe") || selected_value.equals("Arab") || selected_value.equals("العربية")) {
            textToSpeech.setLanguage(new Locale("ar_EG"));
            setAppLocale("ar");
            editor.putString("voice","Arabe").commit();
            editor.putInt("positionLangue",position).commit();
            editor.putInt("fra",3).commit();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
        String b =getResources().getString(R.string.retourAcc);
        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH, null);
        return;
    }

    public void setAppLocale(String localecode) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(localecode.toLowerCase()));
        } else {
            conf.locale = new Locale((localecode.toLowerCase()));
        }
        res.updateConfiguration(conf, dm);
    }

    public void speak() {
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
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show(); } }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String x = result.get(0);
                    if(x.equals("anglais"))
                    {   spinnerLangue.setSelection(adapter.getPosition("Anglais"));
                        setAppLocale("en");
                        editor.putString("voice","Anglais").commit();
                        editor.putInt("positionLangue",1).commit();
                        editor.putInt("fra",2).commit();
                        Integer lastposition =  lastselect.getInt("positionvoice",-1);
                        if(lastposition==0){
                            textToSpeech.setLanguage(Locale.ENGLISH);;
                        }else{ Locale loc = new Locale ("en", "AU");
                            textToSpeech.setLanguage(loc);}
                    } else if(x.equals("francais"))
                    {    spinnerLangue.setSelection(adapter.getPosition("Francais"));
                        editor.putString("voice","Francais").commit();
                        editor.putInt("positionLangue",0).commit();
                        editor.putInt("fra",1).commit();
                        setAppLocale("fr");
                        Integer lastposition =  lastselect.getInt("positionvoice",-1);
                        if(lastposition==0){
                            textToSpeech.setLanguage(Locale.FRENCH);
                        }else{ textToSpeech.setLanguage(Locale.CANADA_FRENCH);}
                    } else if(x.equals("arabe"))
                    {   spinnerLangue.setSelection(adapter.getPosition("Arabe"));
                        textToSpeech.setLanguage(new Locale("ar_EG"));
                        setAppLocale("ar");
                        editor.putString("voice","Arabe").commit();
                        editor.putInt("positionLangue",2).commit();
                        editor.putInt("fra",3).commit();
                    }else if(x.equals("French"))
                    {
                        spinnerLangue.setSelection(adapter.getPosition("French"));
                        setAppLocale("fr");
                        editor.putString("voice","Francais").commit();
                        editor.putInt("positionLangue",0).commit();
                        editor.putInt("fra",1).commit();
                        Integer lastposition =  lastselect.getInt("positionvoice",-1);
                        if(lastposition==0){
                            textToSpeech.setLanguage(Locale.FRENCH);
                        }else{ textToSpeech.setLanguage(Locale.CANADA_FRENCH);}
                    }else if(x.equals("English"))
                    {
                        spinnerLangue.setSelection(adapter.getPosition("English"));
                         textToSpeech.setLanguage(Locale.ENGLISH);
                        setAppLocale("en");
                        editor.putString("voice","Anglais").commit();
                        editor.putInt("positionLangue",1).commit();
                        editor.putInt("fra",2).commit();
                        Integer lastposition =  lastselect.getInt("positionvoice",-1);
                        if(lastposition==0){
                            textToSpeech.setLanguage(Locale.ENGLISH);;
                        }else{ Locale loc = new Locale ("en", "AU");
                            textToSpeech.setLanguage(loc);}
                    }else if(x.equals("Arab"))
                    {
                        spinnerLangue.setSelection(adapter.getPosition("Arab"));
                         textToSpeech.setLanguage(new Locale("ar_EG"));
                        setAppLocale("ar");
                        editor.putString("voice","Arabe").commit();
                        editor.putInt("positionLangue",2).commit();
                        editor.putInt("fra",3).commit();
                    }else if(x.equals("الفرنسيه"))
                    {
                        spinnerLangue.setSelection(adapter.getPosition("الفرنسية"));
                        textToSpeech.setLanguage(Locale.FRENCH);
                        editor.putString("voice","Francais").commit();
                        editor.putInt("positionLangue",0).commit();
                        editor.putInt("fra",1).commit();
                        setAppLocale("fr");
                        Integer lastposition =  lastselect.getInt("positionvoice",-1);
                        if(lastposition==0){
                            textToSpeech.setLanguage(Locale.FRENCH);
                        }else{ textToSpeech.setLanguage(Locale.CANADA_FRENCH);}
                    }else if(x.equals("الإنجليزيه"))
                    {
                        spinnerLangue.setSelection(adapter.getPosition("الانجليزية"));
                         textToSpeech.setLanguage(Locale.ENGLISH);
                        editor.putString("voice","Anglais").commit();
                        editor.putInt("positionLangue",1).commit();
                        editor.putInt("fra",2).commit();
                        setAppLocale("en");
                        Integer lastposition =  lastselect.getInt("positionvoice",-1);
                        if(lastposition==0){
                            textToSpeech.setLanguage(Locale.ENGLISH);;
                        }else{ Locale loc = new Locale ("en", "AU");
                            textToSpeech.setLanguage(loc);}
                    }else if(x.equals("العربيه"))
                    {
                        spinnerLangue.setSelection(adapter.getPosition("العربيه"));
                        textToSpeech.setLanguage(new Locale("ar_EG"));
                        setAppLocale("ar");
                        editor.putString("voice","Arabe").commit();
                        editor.putInt("positionLangue",2).commit();
                        editor.putInt("fra",3).commit();
                    }
                    ////////////////////////
                    else if(x.equals("femme"))
                    {
                        spinnerVoix.setSelection(adapter2.getPosition("Femme"));
                        selected_valuevoice ="Femme";
                        editor2.putString("voice2",selected_valuevoice).commit();
                        editor.putInt("positionvoice",0).commit();

                    }
                    else if(x.equals("homme"))
                    {
                        spinnerVoix.setSelection(adapter2.getPosition("Homme"));
                        selected_valuevoice ="Homme";
                        editor2.putString("voice2",selected_valuevoice).commit();
                        editor.putInt("positionvoice",1).commit();
                    } else if(x.equals("women"))
                    {
                        spinnerVoix.setSelection(adapter2.getPosition("Women"));
                        selected_valuevoice ="Women";
                        editor2.putString("voice2",selected_valuevoice).commit();
                        editor.putInt("positionvoice",0).commit();

                    }
                    else if(x.equals("man"))
                    {
                        spinnerVoix.setSelection(adapter2.getPosition("Man"));
                        selected_valuevoice ="Man";
                        editor2.putString("voice2",selected_valuevoice).commit();
                        editor.putInt("positionvoice",1).commit();
                    } else if(x.equals("مراه"))
                    {
                        spinnerVoix.setSelection(adapter2.getPosition("مرأة"));
                        selected_valuevoice ="مرأة";
                        editor2.putString("voice2",selected_valuevoice).commit();
                        editor.putInt("positionvoice",0).commit();

                    }
                    else if(x.equals("رجل"))
                    {
                        spinnerVoix.setSelection(adapter2.getPosition("رجل"));
                        selected_valuevoice ="رجل";
                        editor2.putString("voice2",selected_valuevoice).commit();
                        editor.putInt("positionvoice",1).commit();
                    }else{
                        String b=getResources().getString(R.string.fal);
                        textToSpeech.speak(b,TextToSpeech.QUEUE_FLUSH,null);
                    }

                }
            }
        }
    }
   /* @Override
    protected void onPause() {
        if(textToSpeech !=null || textToSpeech.isSpeaking())
        {
            textToSpeech.stop();
        }
        super.onPause();
    }*/
}