package com.example.myapplication2.activity;

import static com.example.myapplication2.MainActivity.langue;
import static com.example.myapplication2.MainActivity.lastposition;
import static com.example.myapplication2.MainActivity.textToSpeech;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.R;
import com.example.myapplication2.utilisateur;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Activity_settingpassword extends AppCompatActivity {
    private EditText motdepasse,nouveaumdp,confirmemdp;
    private Button modifmdp;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID,mdpbd;
    private static final int REQUEST_CODE_SPEECH_INPUT=1000;
    private ImageView voice;
    private int test=0;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motsdepasse);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        currentUser = mAuth.getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Users");
        ImageView routeurmot=findViewById(R.id.retourmot);
        motdepasse=findViewById(R.id.mdpc);
        nouveaumdp=findViewById(R.id.nmdp);
        confirmemdp=findViewById(R.id.cnmdp);
        modifmdp=findViewById(R.id.modifmtdp);
        user=mAuth.getCurrentUser();
        userID=user.getUid();
        voice=findViewById(R.id.voicemots);
        if(currentUser!= null) {
            userID = currentUser.getUid();
            Log.d("Entered",userID);
        }

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });
        routeurmot.setOnClickListener(v->
        {
            startActivity(new Intent(this, Acitivity_usersetting.class));
            finish();
            String b=getResources().getString(R.string.retouparcomp);
            int speech=textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);}
                );
        modifmdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modif();
            }
        });
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                utilisateur userprofil=snapshot.getValue(utilisateur.class);
                Log.d("Currentdate",userprofil.getMail());
                if (userprofil != null)
                { mdpbd=userprofil.getMotsdepasse();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Activity_settingpassword.this, "erreur", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void modif()
    {   String mdp=motdepasse.getText().toString().trim();
        String Nouveaumdp=nouveaumdp.getText().toString().trim();
        String Confirmemdp=confirmemdp.getText().toString().trim();
        if(mdp.isEmpty())
        {   String b=getResources().getString(R.string.champoblig);
            Toast.makeText(Activity_settingpassword.this, b, Toast.LENGTH_SHORT).show();
            motdepasse.requestFocus();
            int speech=textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
            //  motdepasse.setCompoundDrawables(null,null,getDrawable(R.drawable.user),null);
            return;
        }
        if(!mdp.equals(mdpbd))//Vérification de mdp saisie avec le mdp actuel à la base de donnés , s’ils sont identiques
        {    String b=getResources().getString(R.string.verifmdp);
            Toast.makeText(Activity_settingpassword.this, b, Toast.LENGTH_SHORT).show();
            motdepasse.requestFocus();
            int speech=textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
            //  motdepasse.setCompoundDrawables(null,null,getDrawable(R.drawable.user),null);
            return;}
        if(Nouveaumdp.isEmpty())
        {   String b=getResources().getString(R.string.champoblig);
            Toast.makeText(Activity_settingpassword.this, b, Toast.LENGTH_SHORT).show();
            nouveaumdp.requestFocus();
            int speech=textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
            return;
        }
        if(Nouveaumdp.length()<8)
        {   String b=getResources().getString(R.string.max8);
            Toast.makeText(Activity_settingpassword.this,b, Toast.LENGTH_SHORT).show();
            int speech=textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
            nouveaumdp.requestFocus();
            return;
        }
        if(Confirmemdp.isEmpty())
        {   String b=getResources().getString(R.string.champoblig);
            Toast.makeText(Activity_settingpassword.this, b, Toast.LENGTH_SHORT).show();
            confirmemdp.requestFocus();
            int speech=textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
            return;
        }
        if (!(Nouveaumdp.equals(Confirmemdp)))
        {   String b=getResources().getString(R.string.verifmdp);
            Toast.makeText(Activity_settingpassword.this,b, Toast.LENGTH_SHORT).show();
            confirmemdp.requestFocus();
            int speech=textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
            return;
        }
        progressDialog=new ProgressDialog(Activity_settingpassword.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        HashMap<String,Object> hashmap=new HashMap<>();
        hashmap.put("motsdepasse",Nouveaumdp);
        reference.child(userID).updateChildren(hashmap).addOnSuccessListener(suc->
        {    motdepasse.setText("");
            nouveaumdp.setText("");
            confirmemdp.setText("");
            String b=getResources().getString(R.string.succe);
            textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
            Toast.makeText(Activity_settingpassword.this,b,Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }).addOnFailureListener(er->
        {
            String b=getResources().getString(R.string.verifdonne);
            textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
            Toast.makeText(Activity_settingpassword.this,b,Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        });
    }
    private  void speak()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        if(langue==1||lastposition ==0){
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"fr_FR");}
        else if(langue==2 || lastposition ==1){
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en-US"); }
        else if(langue==3|| lastposition ==2){  intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ar");}
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "");
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{
                if(resultCode==RESULT_OK && null!=data)
                {
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String x=result.get(0);
                    if(x.equals("oui")||x.equals("yes")||x.equals("نعم"))
                    {   String b=getResources().getString(R.string.entmdp);
                       textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        motdepasse.requestFocus();
                    }
                    else if(x.equals("nouveau mot de passe")||x.equals("new password")||x.equals("كلمه المرور الجديده"))
                    {   String b=getResources().getString(R.string.entvmdp);
                        textToSpeech.speak(b,TextToSpeech.QUEUE_FLUSH,null);
                        nouveaumdp.requestFocus();
                    }
                    else if(x.equals("confirmer nouveau mot de passe")||x.equals("confirm the new password")||x.equals("تاكيد كلمه المرور الجديده"))
                    {   String b=getResources().getString(R.string.entcmdp);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
                        confirmemdp.requestFocus();
                    }
                    else if(x.equals("modifier")||x.equals("edit")||x.equals("تعديل"))
                    {
                        modif();
                    }
                    else{
                        String b=getResources().getString(R.string.fal);
                        int speech=textToSpeech.speak(b,TextToSpeech.QUEUE_FLUSH,null);
                    }

                }
                break;
            }
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, Acitivity_usersetting.class));
        finish();
        String b=getResources().getString(R.string.retouparcomp);
        int speech=textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH,null);
        return;
    }
}
