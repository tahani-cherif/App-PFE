package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.activity.Activity_connexion;

import java.util.Timer;
import java.util.TimerTask;

public class Activity_ecran_demarrage extends AppCompatActivity {
    Timer timer;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_demarrage);
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               if (i<100)
               {
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                       }
                   });
                   i++;
               }else{
                   timer.cancel();
                   Intent intent=new Intent(Activity_ecran_demarrage.this, Activity_connexion.class);
                   startActivity(intent);
                   finish();
               }
            }
        },0,50);

    }
}