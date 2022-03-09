package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class Monprofile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monprofil);
        Toolbar my_toolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        ImageView routeur=findViewById(R.id.retour);
        ImageView deconx=findViewById(R.id.dec);
        routeur.setOnClickListener(v ->
                { startActivity(new Intent(this,MainActivity.class));
                    finish();}

        );
       deconx.setOnClickListener(v ->
               { startActivity(new Intent(this, Connexion.class));
                   finish(); }
       );

    }
}