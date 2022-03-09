package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class Langue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_langue);
        ImageView retourlang=findViewById(R.id.retourlang);
        retourlang.setOnClickListener(v->
        {startActivity(new Intent(this,MainActivity.class));
        finish();});

    }
}