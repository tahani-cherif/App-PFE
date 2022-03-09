package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class Para_voix extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paravoix);
        Spinner sp =findViewById(R.id.spinner1);
        Spinner sp2 =findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner1,R.layout.my_size_item);
        adapter.setDropDownViewResource(R.layout.my_dropdwon_item);
        sp.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.spinner2, R.layout.my_size_item);
        adapter2.setDropDownViewResource(R.layout.my_dropdwon_item);
        sp2.setAdapter(adapter2);
        ImageView retour=findViewById(R.id.retourpar);
        retour.setOnClickListener(v->
                { startActivity(new Intent(this,MainActivity.class));
                    finish();}
        );
    }
}