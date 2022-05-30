package com.example.myapplication2;

import static com.example.myapplication2.activity.Activity_settingvoice.lg;
import static com.example.myapplication2.activity.Activity_settingvoice.speed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication2.activity.Acitivity_usersetting;
import com.example.myapplication2.activity.Activity_Battery;
import com.example.myapplication2.activity.Activity_Langue;
import com.example.myapplication2.activity.Activity_connexion;
import com.example.myapplication2.activity.Activity_controlcenter;
import com.example.myapplication2.activity.Activity_notification;
import com.example.myapplication2.activity.Activity_settingvoice;
import com.example.myapplication2.activity.Activity_userprofil;
import com.example.myapplication2.activity.itemsearch;
import com.example.myapplication2.activity.notifphone;
import com.example.myapplication2.activity.notifsms;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    TextView time;
    private DatabaseReference reference;
    private String userID;
    private FirebaseAuth mAuth;
    private Activity_Battery mBatteryReceiver;
    private IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    DecimalFormat df = new DecimalFormat("#.##");
    ImageView cloudy, voice, voix, paus;
    public static TextToSpeech textToSpeech;
    public static String meteo, formatime, formadate,list;
    String  output = "";
    SharedPreferences sharedPreferences;
    public static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    private Object notifsms;
    private Object notifphone;
    private  DrawerLayout draw;
    SearchView searchView;
    ListView ls;
    String[] list2;
    int[] liste3={R.drawable.agenda,
            R.drawable.gmail,
            R.drawable.camera,
            R.drawable.contact,
            R.drawable.horloge,
            R.drawable.telephone,
            R.drawable.message,
            R.drawable.maps,
            R.drawable.photos};
    List<itemsearch> listitem=new ArrayList<>();
    CustomAdapter customAdapter;
    String isConnedted3="";
    public static  int lastposition,lastposition2,langue,vite;
    public static float vitesse;
    SharedPreferences.Editor editor,editor2;
    SharedPreferences sharedPreferencess;
    public static int agen,gamil,camera,contact,horloge,telephone,messages,map,photo,notif;
    TextView dateinput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences2 = getSharedPreferences("lastsetting", Context.MODE_PRIVATE);
        if (sharedPreferences2 != null) {
             lastposition =  sharedPreferences2.getInt("positionLangue",-1);
            lastposition2 =  sharedPreferences2.getInt("positionvoice",-1);
            langue =  sharedPreferences2.getInt("fra",0);
            vitesse=sharedPreferences2.getFloat("vitesse",1);
      vite=sharedPreferences2.getInt("SPEED",0);

            Log.d("bb", String.valueOf(speed));
            Log.d("bbb", String.valueOf(lastposition));
        }
        editor=sharedPreferences2.edit();
        sharedPreferencess= getSharedPreferences("agenda", Context.MODE_PRIVATE);
        editor2=sharedPreferencess.edit();
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        Locale savedLocale = conf.locale;
        Log.d("test", String.valueOf(savedLocale));
        Log.d("zz", String.valueOf(lg));
        if (savedLocale.equals("en_US")) {
            setAppLocale("en");
        } else if (savedLocale.equals("fr_FR")) {
            setAppLocale("fr");
        } else if (savedLocale.equals("ar_EG")) {
            setAppLocale("ar");
        }
        list2 = new String[]{getResources().getString(R.string.agenda), getResources().getString(R.string.GMAIL), getResources().getString(R.string.camera), getResources().getString(R.string.contact), getResources().getString(R.string.hor), getResources().getString(R.string.telephone), getResources().getString(R.string.msg), getResources().getString(R.string.map), getResources().getString(R.string.photo)};
        int  test2=lastposition2;
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    if (langue == 1) {
                        setAppLocale("fr");
                        if(test2==0)
                            textToSpeech.setLanguage(Locale.FRENCH);
                        if(test2==1){
                            textToSpeech.setLanguage(Locale.CANADA_FRENCH);
                        }
                    }
                    else   if (langue==2) {
                        setAppLocale("en");
                        if(test2==0)
                            textToSpeech.setLanguage(Locale.US);
                        if(test2==1){
                            Locale loc = new Locale ("en", "AU");
                            textToSpeech.setLanguage(loc);
                        }

                    } else if (langue== 3) {
                        Locale locale = new Locale("ar_TN");
                        textToSpeech.setLanguage(locale);
                        setAppLocale("ar");
                    } } else {
                        Log.e("TTS", "Initilization Failed!"); } }} );

       if (langue==2) {
            if(test2==0)
                textToSpeech.setLanguage(Locale.ENGLISH);
            setAppLocale("en");
            editor.putInt("positionLangue",0).commit();
            editor.putInt("fra",2);
            if(test2==1){
                Locale loc = new Locale ("en", "AU");
                textToSpeech.setLanguage(loc);
                setAppLocale("en");
                editor.putInt("positionLangue",0).commit();
                editor.putInt("fra",2);
            }
        }
        else if(langue==3){
            textToSpeech.setLanguage(new Locale("ar_TN"));
            editor.putInt("positionLangue",2).commit();
            editor.putInt("fra",3);
            setAppLocale("ar");
        }
        else if(langue==1)
        {  if(test2==0)
            textToSpeech.setLanguage(Locale.FRENCH);
            editor.putInt("positionLangue",0).commit();
            editor.putInt("fra",1);
            setAppLocale("fr");
            if(test2==1){
                textToSpeech.setLanguage(Locale.CANADA_FRENCH);
                editor.putInt("positionLangue",1).commit();
                editor.putInt("fra",1);
                setAppLocale("fr");
            }
        }

        Log.d("voix", String.valueOf(lastposition2));
        textToSpeech.setSpeechRate(vitesse);
        cloudy = findViewById(R.id.cloudy);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        int timez = calendar.get(Calendar.HOUR_OF_DAY);
        sharedPreferences = getSharedPreferences("Setting", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("connected", "yes");
        editor.apply();
        mBatteryReceiver = new Activity_Battery();
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // Date date = Calendar.getInstance().getTime();
       // formadate = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
       // formatime = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
        time = findViewById(R.id.time);
      //  time.setText(formatime);
         dateinput = findViewById(R.id.date);
     //   dateinput.setText(formadate);
        Toolbar my_toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        draw = findViewById(R.id.drawer);
        ImageView profil = findViewById(R.id.profil);
        ImageView imgmenu = findViewById(R.id.menu);
        ImageView cloudy = findViewById(R.id.cloudy);
        NavigationView navigation = findViewById(R.id.navigation);
        navigation.setItemIconTintList(null);
        voice = findViewById(R.id.voicemenu);
        voix = findViewById(R.id.option7);
        paus = findViewById(R.id.paus);
        ls = findViewById(R.id.lv1);
        searchView = findViewById(R.id.searchView);
        currentUser = mAuth.getCurrentUser();
        if(currentUser!= null) {
            userID = currentUser.getUid();
            Log.d("Entered","yessss");
        }
        reference= FirebaseDatabase.getInstance().getReference().child("Users");
      reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                utilisateur userprofil=snapshot.getValue(utilisateur.class);
               // Log.d("Currentdate",userprofil.getMail());
                if (userprofil != null)
                {  agen=userprofil.getAgen();
                    gamil=userprofil.getGamil();
                    contact=userprofil.getContact();
                    camera=userprofil.getCamera();
                    horloge=userprofil.getHorloge();
                    telephone=userprofil.getTelephone();
                    messages=userprofil.getMessages();
                    map=userprofil.getMap();
                    photo=userprofil.getPhoto();
                    notif=userprofil.getNotif();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        Log.d("notif", String.valueOf(agen));
        paus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               list = getResources().getString(R.string.date) + formadate + getResources().getString(R.string.time) + formatime + getResources().getString(R.string.temp) + output + getResources().getString(R.string.appmenu);
                textToSpeech.speak(list, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        voix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });
        imgmenu.setOnClickListener(v ->
                {
                    draw.openDrawer(GravityCompat.START);
                    String x = getResources().getString(R.string.menu);
                    textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH, null);

                }
        );
        profil.setOnClickListener(v ->
                {
                    startActivity(new Intent(this, Activity_userprofil.class));
                }
        );
        navigation.setNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.option1:
                            Intent intent = new Intent(com.example.myapplication2.MainActivity.this, Acitivity_usersetting.class);
                            startActivity(intent);
                            draw.close();
                            String b = getResources().getString(R.string.parge);
                            textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH, null);

                            return true;
                        case R.id.option2:
                            Intent intent2 = new Intent(com.example.myapplication2.MainActivity.this, Activity_settingvoice.class);
                            startActivity(intent2);
                            draw.close();
                            String a = getResources().getString(R.string.parvoix);
                            textToSpeech.speak(a, TextToSpeech.QUEUE_FLUSH, null);
                            return true;
                        case R.id.option3:
                            Intent intent3 = new Intent(com.example.myapplication2.MainActivity.this, Activity_controlcenter.class);
                            startActivity(intent3);
                            draw.close();
                            String b3 = getResources().getString(R.string.cc);
                            textToSpeech.speak(b3, TextToSpeech.QUEUE_FLUSH, null);
                            return true;
                        case R.id.option4:
                            Intent intent4 = new Intent(com.example.myapplication2.MainActivity.this, Activity_Langue.class);
                            startActivity(intent4);
                            String b4 = getResources().getString(R.string.parla);
                            textToSpeech.speak(b4, TextToSpeech.QUEUE_FLUSH, null);
                            return true;
                        case R.id.option5:
                            Intent intent5 = new Intent(com.example.myapplication2.MainActivity.this, Activity_notification.class);
                            startActivity(intent5);
                            draw.close();
                            String b5 = getResources().getString(R.string.parnot);
                            textToSpeech.speak(b5, TextToSpeech.QUEUE_FLUSH, null);
                            return true;
                        case R.id.option6:
                            mAuth.signOut();
                            SharedPreferences.Editor editor2 = sharedPreferences.edit();
                            editor2.putString("connected", "no");
                            editor2.apply();
                            Intent intent6 = new Intent(com.example.myapplication2.MainActivity.this, Activity_connexion.class);
                            startActivity(intent6);
                            finish();
                            draw.close();
                            String b6 = getResources().getString(R.string.parconx);
                            textToSpeech.speak(b6, TextToSpeech.QUEUE_FLUSH, null);
                            editor2.putInt("gmail",1).commit();
                            editor2.putInt("agen",1).commit();
                            editor2.putInt("camera",1).commit();
                            editor2.putInt("contact",1).commit();
                            editor2.putInt("horloge",1).commit();
                            editor2.putInt("telephone",1).commit();
                            editor2.putInt("message",1).commit();
                            editor2.putInt("map",1).commit();
                            editor2.putInt("photo",1).commit();
                            editor2.putInt("notif",1).commit();
                            return true;
                        default:
                            return false;
                    }
                }
        );
        TextView temp1 = findViewById(R.id.temp);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.openweathermap.org/data/2.5/weather?q=tunis,&appid=e53301e27efa0b66d05045d91b2742d3",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp") - 273.15;
                    //   int humidity = jsonObjectMain.getInt("humidity");
                    output += df.format(temp) + " °C";
                    //  + " Humidity: " + humidity + "%";
                    temp1.setText(output);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        meteo = output;
        if (isnight(timez)) {
            cloudy.setImageDrawable(getDrawable(R.drawable.cloud));
        }
        Log.d("test", String.valueOf(timez));
        for (int i = 0; i < list2.length; i++) {
            itemsearch item = new itemsearch(list2[i], liste3[i]);
            listitem.add(item);
        }
        customAdapter = new CustomAdapter(listitem, this);
        ls.setAdapter(customAdapter);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (list2[i].equals(getResources().getString(R.string.agenda))) {
                    agenda();
                } else if (list2[i].equals(getResources().getString(R.string.GMAIL))) {
                    gmail();
                } else if (list2[i].equals(getResources().getString(R.string.camera))) {
                    camera();
                } else if (list2[i].equals(getResources().getString(R.string.contact))) {
                    contact();
                } else if (list2[i].equals(getResources().getString(R.string.hor))) {
                    horloge();
                } else if (list2[i].equals(getResources().getString(R.string.telephone))) {
                    phone();
                } else if (list2[i].equals(getResources().getString(R.string.msg))) {
                    messages();
                } else if (list2[i].equals(getResources().getString(R.string.map))) {
                    maps();
                } else if (list2[i].equals(getResources().getString(R.string.photo))) {
                    photos();
                }

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                customAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                customAdapter.getFilter().filter(s);
                return false;
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("Setting", Context.MODE_PRIVATE);
        Date date = Calendar.getInstance().getTime();
        formadate = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
        formatime = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
        time.setText(formatime);
       dateinput = findViewById(R.id.date);
        dateinput.setText(formadate);
        if (sharedPreferences != null) {
            String isConnedted = sharedPreferences.getString("battrie", "yes");
            Log.d("battrie", isConnedted);
            if (isConnedted.equals("yes")) {
                registerReceiver(mBatteryReceiver, mIntentFilter);
                return;
            }
        }
    }

    private boolean isnight(int time) {
        if (5 > time || time > 17) {
            return true;
        } else {
            return false;
        }
    }

    public void phone() {
        if (sharedPreferencess != null) {
            if (telephone==1) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                startActivity(intent);
            } else{
                String x=getResources().getString(R.string.appdesac);
                textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    public void maps() {
        if (sharedPreferencess != null) {
            if (map==1) {
                startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_MAPS));
            }else{  String x=getResources().getString(R.string.appdesac);
                textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH, null);}
        }
    }

    public void contact() {
        if (sharedPreferencess != null) {
            if (contact==1) {
                startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_CONTACTS));
            }else{  String x=getResources().getString(R.string.appdesac);
                textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH, null);}
        }}

    public void camera() {
        if (sharedPreferencess != null) {
            if (camera==1) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }else{  String x=getResources().getString(R.string.appdesac);
                textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH, null);}
        }
    }

    public void photos() {
        if (sharedPreferencess != null) {
            if (photo==1) {
                startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_GALLERY));
            }else{  String x=getResources().getString(R.string.appdesac);
                textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH, null);}
        }
    }

    public void agenda() {
        if (sharedPreferencess != null) {
            if (agen==1) {
                startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_CALENDAR));
            }else{  String x=getResources().getString(R.string.appdesac);
                textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH, null);}
        }
    }

    public void messages() {
        if (sharedPreferencess != null) {
            if (messages==1) {
                startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_MESSAGING));
            }else{  String x=getResources().getString(R.string.appdesac);
                textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH, null);}
        }
    }

    public void horloge() {
        if (sharedPreferencess != null) {
            if (horloge==1) {
                Intent openClockIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
                startActivity(openClockIntent);
            }else{ String x=getResources().getString(R.string.appdesac);
                textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH, null);}
        }
    }

    public void gmail() {
        if (sharedPreferencess != null) {
            if (gamil==1) {
                startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_EMAIL));
            }else{  String x=getResources().getString(R.string.appdesac);
                textToSpeech.speak(x, TextToSpeech.QUEUE_FLUSH, null);}
        }

    }

    public void speak() {
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
        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String x = result.get(0);
                    if (x.equals("paramètres généraux du compte")||x.equals("general account setting")||x.equals("اعدادات الحساب العامه")) {
                        String b =  getResources().getString(R.string.parge);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH, null);
                        startActivity(new Intent(this, Acitivity_usersetting.class));
                        draw.close();
                    } else if (x.equals("paramètres de la voix")||x.equals("voice setting")||x.equals("اعدادات الصوت")) {
                        String b = getResources().getString(R.string.parvo);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH, null);
                        startActivity(new Intent(this, Activity_settingvoice.class));
                        draw.close();
                    } else if (x.equals("langue")||x.equals("language")||x.equals("لغه")) {
                        String b =getResources().getString(R.string.parla);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH, null);
                        startActivity(new Intent(this, Activity_Langue.class));
                        draw.close();
                    } else if (x.equals("notification")||x.equals("اشعار")) {
                        String b =getResources().getString(R.string.parnot);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH, null);
                        startActivity(new Intent(this, Activity_notification.class));
                        draw.close();
                    } else if (x.equals("déconnexion")||x.equals("sign out")||x.equals("تسجيل الخروج")) {
                        mAuth.signOut();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("connected", "no");
                        editor.apply();
                        finish();
                        String b =getResources().getString(R.string.parconx);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH, null);
                        startActivity(new Intent(this, Activity_connexion.class));
                        draw.close();
                    } else if (x.equals("centre de contrôle")||x.equals("control center")||x.equals("مركز التحكم")) {
                        String b =getResources().getString(R.string.cc);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH, null);
                        startActivity(new Intent(this, Activity_controlcenter.class));
                        draw.close();
                    }
                    else if(x.equals("menu"))
                    {
                        draw.openDrawer(GravityCompat.START);
                        String b= getResources().getString(R.string.menu);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH, null);
                    }else if(x.equals("lire le contenu"))
                    {
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        finish();
                        String a = getResources().getString(R.string.date) + formadate +getResources().getString(R.string.time)
                                + formatime +getResources().getString(R.string.temp) + output
                                +getResources().getString(R.string.appmenu) ;
                        textToSpeech.speak(a, TextToSpeech.QUEUE_FLUSH, null);
                    }
                    //les application
                    else if (x.equals("Gmail")||x.equals("البريد")) {
                        if (sharedPreferencess != null) {
                            if (gamil==1) {
                                startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_EMAIL));
                            }else{  String a=getResources().getString(R.string.appdesac);
                                textToSpeech.speak(a, TextToSpeech.QUEUE_FLUSH, null);}
                        }

                    } else if (x.equals("photos")||x.equals("صور")) {
                        if (sharedPreferencess != null) {
                            if (photo==1) {
                                startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_GALLERY));
                            }else{  String a=getResources().getString(R.string.appdesac);
                                textToSpeech.speak(a, TextToSpeech.QUEUE_FLUSH, null);}
                        }
                    } else if (x.equals("téléphone")||x.equals("phone")||x.equals("الهاتف")) {
                        if (sharedPreferencess != null) {
                            if (telephone==1) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                startActivity(intent);
                            } else{
                                String a=getResources().getString(R.string.appdesac);
                                textToSpeech.speak(a, TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }
                    } else if (x.equals("agenda")||x.equals("calendar")||x.equals("التقويم")) {
                        if (sharedPreferencess != null) {
                            if (agen==1) {
                                startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_CALENDAR));
                            }else{  String a=getResources().getString(R.string.appdesac);
                                textToSpeech.speak(a, TextToSpeech.QUEUE_FLUSH, null);}
                        }
                    } else if (x.equals("caméra")||x.equals("camera")||x.equals("كاميرا")) {
                        if (sharedPreferencess != null) {
                            if (camera==1) {
                                Intent intent = new Intent();
                                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivity(intent);
                            }else{  String a=getResources().getString(R.string.appdesac);
                                textToSpeech.speak(a, TextToSpeech.QUEUE_FLUSH, null);}
                        }
                    } else if (x.equals("contact")||x.equals("جهات الاتصال")) {
                        if (sharedPreferencess != null) {
                            if (contact==1) {
                                startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_CONTACTS));
                            }else{  String a=getResources().getString(R.string.appdesac);
                                textToSpeech.speak(a, TextToSpeech.QUEUE_FLUSH, null);}
                        }
                    } else if (x.equals("horloge")||x.equals("clock")||x.equals("ساعه")) {
                        if (sharedPreferencess != null) {
                            if (horloge==1) {
                                Intent openClockIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
                                startActivity(openClockIntent);
                            }else{ String a=getResources().getString(R.string.appdesac);
                                textToSpeech.speak(a, TextToSpeech.QUEUE_FLUSH, null);}
                        }
                    } else if (x.equals("message")||x.equals("الرسائل")) {
                        if (sharedPreferencess != null) {
                            if (messages==1) {
                                startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_MESSAGING));
                            }else{  String a=getResources().getString(R.string.appdesac);
                                textToSpeech.speak(a, TextToSpeech.QUEUE_FLUSH, null);}
                        }
                    } else if (x.equals("Map")||x.equals("Maps")||x.equals("خرائط")) {
                        if (sharedPreferencess != null) {
                            if (map==1) {
                                startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_MAPS));
                            }else{  String a=getResources().getString(R.string.appdesac);
                                textToSpeech.speak(a, TextToSpeech.QUEUE_FLUSH, null);}
                        }
                    }
                    ///
                    else if (x.equals("mon profil")||x.equals("my profile")||x.equals("ملفي")) {
                        startActivity(new Intent(this, Activity_userprofil.class));
                    }
                    else if (x.equals("time")||x.equals("الوقت")) {
                        String b =getResources().getString(R.string.time) + formatime;
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH, null);
                    } else if (x.equals("date")||x.equals("تاريخ اليوم")) {
                        String b = getResources().getString(R.string.date)+ formadate;
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH, null);
                    } else if (x.equals("météo")||x.equals("weather report")||x.equals("درجه الحراره")) {
                        String b=getResources().getString(R.string.temp)+output;
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH, null);
                    } else {
                        String b =getResources().getString(R.string.fal);
                        textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                break;
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            notifsms = new notifsms();
            notifphone = new notifphone();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
            registerReceiver((BroadcastReceiver) notifsms, intentFilter);
            registerReceiver((BroadcastReceiver) notifphone, intentFilter);
        }

    }
    @Override
    public void onBackPressed() {
        finish();
        return;
    }
    public class CustomAdapter extends BaseAdapter implements Filterable {
        private List<itemsearch> itemsearchList;
        private List<itemsearch> itemsearchListfiltered;
        private Context context;

        public CustomAdapter(List<itemsearch> itemsearchList, Context context) {
            this.itemsearchList = itemsearchList;
            this.itemsearchListfiltered=itemsearchList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return itemsearchListfiltered.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1=getLayoutInflater().inflate(R.layout.simple_list_item_1,null);
            TextView name=view1.findViewById(R.id.test);
            ImageView image=view1.findViewById(R.id.test2);
            name.setText(itemsearchListfiltered.get(i).getName());
            image.setImageResource(itemsearchListfiltered.get(i).getImg());
            return view1;
        }

        @Override
        public Filter getFilter() {
            Filter filter=new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults filterResults=new FilterResults();
                    if(charSequence==null || charSequence.length()==0)
                    {
                        filterResults.count=itemsearchList.size();
                        filterResults.values=itemsearchList;
                    }
                    else{
                        String searchstr=charSequence.toString();
                        List<itemsearch> resultdata=new ArrayList<>();
                        for (itemsearch itemsearch:itemsearchList)
                        {
                            if(itemsearch.getName().contains(searchstr))
                            {
                                resultdata.add(itemsearch);
                            }
                            filterResults.count=resultdata.size();
                            filterResults.values=resultdata;
                        }
                    }
                    return filterResults;
                }
                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    itemsearchListfiltered=(List<itemsearch>)filterResults.values;
                    notifyDataSetChanged();
                }
            };
            return filter;
        }
    }
    public void setAppLocale(String localecode)
    {
        Resources res=getResources();
        DisplayMetrics dm= res.getDisplayMetrics();
        Configuration conf= res.getConfiguration();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            conf.setLocale(new Locale(localecode.toLowerCase()));

        }else{
            conf.locale=new Locale((localecode.toLowerCase()));
        }
        res.updateConfiguration(conf,dm);
    }

}


