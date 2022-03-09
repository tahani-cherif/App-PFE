package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class Nom_prenom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomprenom);
        ImageView retournom=findViewById(R.id.retournom);
        retournom.setOnClickListener(v ->
                { startActivity(new Intent(this, Para_generau_compte.class));
                    finish();}
        );
    }
}