package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Batter mBatteryReceiver = new Batter();
    private IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    DecimalFormat df = new DecimalFormat("#.##");
    private WifiManager wifiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView wifi=findViewById(R.id.wifi);
        Date date= Calendar.getInstance().getTime();
        String formadate= DateFormat.getDateInstance(DateFormat.LONG).format(date);
        String formatime=DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
        TextView time=findViewById(R.id.time);
        time.setText(formatime);
        TextView dateinput=findViewById(R.id.date);
        dateinput.setText(formadate);
        Toolbar my_toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        DrawerLayout draw = findViewById(R.id.drawer);
        ImageView profil = findViewById(R.id.profil);
        ImageView imgmenu = findViewById(R.id.menu);
        ImageView cloudy=findViewById(R.id.cloudy);
        NavigationView navigation = findViewById(R.id.navigation);
        navigation.setItemIconTintList(null);
        imgmenu.setOnClickListener(v ->
                draw.openDrawer(GravityCompat.START));
        profil.setOnClickListener(v ->
                startActivity(new Intent(this, Monprofile.class))

        );
        navigation.setNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.option1:
                            Intent intent = new Intent(MainActivity.this, Para_generau_compte.class);
                            startActivity(intent);
                            return true;
                        case R.id.option2:
                            Intent intent2 = new Intent(MainActivity.this, Para_voix.class);
                            startActivity(intent2);
                            return true;
                        case R.id.option3:
                            Intent intent3 = new Intent(MainActivity.this, Maincentrecontrole.class);
                            startActivity(intent3);
                            return true;
                        case R.id.option4:
                            Intent intent4 = new Intent(MainActivity.this, Langue.class);
                            startActivity(intent4);
                            return true;
                        case R.id.option5:
                            Intent intent5 = new Intent(MainActivity.this, Notification.class);
                            startActivity(intent5);
                            return true;
                        case R.id.option6:
                            Intent intent6 = new Intent(MainActivity.this, Connexion.class);
                            startActivity(intent6);
                            finish();
                            return true;
                        default:
                            return false;
                    }   }
        );
        TextView temp1 = findViewById(R.id.temp);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://api.openweathermap.org/data/2.5/weather?q=tunis,&appid=e53301e27efa0b66d05045d91b2742d3" , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String output = "";
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
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
       // TextView tt=findViewById(R.id.tt);
     /*   wifi.setOnClickListener(view ->
        {
            if(wifiManager !=null)
            {
                wifiManager.setWifiEnabled(false);
                tt.setText("WiFi is OFF");
            }
            else{
                wifiManager.setWifiEnabled(true);
                tt.setText("WiFi is OFF");
            }
        }
     );*/

    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBatteryReceiver, mIntentFilter);
    }


}