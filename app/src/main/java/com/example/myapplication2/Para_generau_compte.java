package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Para_generau_compte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_para_generau_compte);
        ImageView retourpara=findViewById(R.id.retourpara);
        RelativeLayout modefnom=findViewById(R.id.modefnom);
        RelativeLayout modefmot=findViewById(R.id.modefmot);
        retourpara.setOnClickListener(v ->
                { startActivity(new Intent(this,MainActivity.class));
                    finish();}
        );
        modefnom.setOnClickListener(v ->
                {startActivity(new Intent(this, Nom_prenom.class));
                    finish();}
        );
        modefmot.setOnClickListener(v ->
        {startActivity(new Intent(this, Motsdepasse.class));
            finish();}
        );
    }

}