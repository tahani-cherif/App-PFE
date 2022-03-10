package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class Langue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_langue);
        ImageView retourlang=findViewById(R.id.retourlang);
        SwitchMaterial  fran=findViewById(R.id.fran);
        SwitchMaterial ang=findViewById(R.id.ang);
        fran.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(fran.isChecked())
                {
                    ang.setChecked(false);
                }
                else
                {
                    ang.setChecked(true);
                }
            }
        });
        ang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(ang.isChecked())
                {
                    fran.setChecked(false);
                }
                else
                {
                    fran.setChecked(true);
                }
            }
        });
        retourlang.setOnClickListener(v->
        {startActivity(new Intent(this,MainActivity.class));
        finish();});
        fran.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(fran.isChecked())
                {
                    ang.setChecked(false);
                }
                else
                {
                    ang.setChecked(true);
                }
            }
        });
        ang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(ang.isChecked())
                {
                    fran.setChecked(false);
                }
                else
                {
                    fran.setChecked(true);
                }
            }
        });
    }
}