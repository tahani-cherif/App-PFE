package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MdpoublieActivity extends AppCompatActivity  {
    Button valid;
    EditText smdp,cvmdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mdpoublie);
        valid=findViewById(R.id.mdpv);
        smdp=findViewById(R.id.smdp);
        cvmdp=findViewById(R.id.cvmdp);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a=smdp.getText().toString().trim();
                String b=cvmdp.getText().toString().trim();
                if (a.isEmpty())
                { smdp.setError("mot de passe  obligatoire");
                    smdp.requestFocus();
                    return;
                }

                if (a.length()<8)
                { smdp.setError("max 8");
                    smdp.requestFocus();
                    return;
                }
                if (b.isEmpty())
                {  cvmdp.setError("mot de passe  obligatoire");
                    cvmdp.requestFocus();
                    return;
                }
                if (!(b.equals(a)))
                {  cvmdp.setError(" verifier mot de passe ");
                    cvmdp.requestFocus();
                    return;
                }
                startActivity(new Intent(MdpoublieActivity.this, Connexion.class));
                finish();
            }
        });
    }

}