package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class Motsdepasse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motsdepasse);
        ImageView routeurmot=findViewById(R.id.retourmot);
        routeurmot.setOnClickListener(v->
        {startActivity(new Intent(this,MainActivity.class));
            finish();}
                );
    }
}