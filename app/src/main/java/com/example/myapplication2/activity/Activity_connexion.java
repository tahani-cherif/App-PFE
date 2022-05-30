package com.example.myapplication2.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class Activity_connexion extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText mail,motsdepasse;
    private TextView insc,mdpo;
    private Button conxbutton;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    TextToSpeech textToSpeech2;
    String formadate ;
    String formatime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        sharedPreferences = getSharedPreferences("Setting", Context.MODE_PRIVATE);
        Date date = Calendar.getInstance().getTime();
        formadate = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
        formatime = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
        textToSpeech2 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    } }  });
        if (sharedPreferences != null) {
            String isConnedted = sharedPreferences.getString("connected", "");
            Log.d("isConnected", isConnedted);
            if (isConnedted.equals("yes")) {
                startActivity(new Intent(this,MainActivity.class));
                finish();
                return;}
            else{
        initView();
        initClickListener();}
        }
    }

    private void initView() {
        mail=findViewById(R.id.rr);
        motsdepasse=findViewById(R.id.motsdepasse1);
        conxbutton=findViewById(R.id.buttonconx);
        mdpo=findViewById(R.id.mdpo);
        insc=findViewById(R.id.insc);
    }

    private void initClickListener() {
        mAuth = FirebaseAuth.getInstance();
        insc.setOnClickListener(this);
        conxbutton.setOnClickListener(this);
        mdpo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.insc:
                startActivity(new Intent(this, Activity_Inscription.class));
                finish();
                break;
            case R.id.buttonconx:
                connexion();
                break;
            case  R.id.mdpo:
                startActivity(new Intent(this, Activity_forgetpassword.class));

        }

    }

    private void connexion() {
        String mailconx=mail.getText().toString().trim();
        String motdepasseconx=motsdepasse.getText().toString().trim();
        if(mailconx.isEmpty())
        {
            Toast.makeText(this, "champ obligatoire", Toast.LENGTH_SHORT).show();
            mail.requestFocus();
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mailconx).matches())
        {
            Toast.makeText(Activity_connexion.this, "Adresse non valide", Toast.LENGTH_SHORT).show();
            return;
        }
        if (motdepasseconx.isEmpty())
        {   Toast.makeText(this, "champ obligatoire", Toast.LENGTH_SHORT).show();
            motsdepasse.requestFocus();
            return;
        }
        if (motdepasseconx.length()<8)
        {  Toast.makeText(this, "mot de passe doit contenir au minimum 8 caractère ", Toast.LENGTH_SHORT).show();
            motsdepasse.requestFocus();
            return;
        }
        progressDialog=new ProgressDialog(Activity_connexion.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
         mAuth.signInWithEmailAndPassword(mailconx,motdepasseconx)
                 .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful())
                         {
                             startActivity(new Intent(Activity_connexion.this, MainActivity.class));
                             String x = getResources().getString(R.string.date) + formadate + getResources().getString(R.string.time) + formatime + getResources().getString(R.string.appmenu);
                             textToSpeech2.speak(x, TextToSpeech.QUEUE_FLUSH, null);
                         }
                         else {
                             Toast.makeText(Activity_connexion.this, getResources().getString(R.string.probconx),
                                     Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                         }

                     }
                 });
    }

}