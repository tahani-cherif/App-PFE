package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class Maincentrecontrole extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centrecontrole10);
        ImageView retourcent=findViewById(R.id.retourcent);
        retourcent.setOnClickListener(v->
        { startActivity(new Intent(this,MainActivity.class));
            finish();}
        );
    }
}