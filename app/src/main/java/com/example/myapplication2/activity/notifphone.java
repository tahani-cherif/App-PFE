package com.example.myapplication2.activity;

import static com.example.myapplication2.MainActivity.notif;
import static com.example.myapplication2.MainActivity.textToSpeech;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication2.R;

public class notifphone extends BroadcastReceiver {
    Context context;
    @Override
    public void onReceive(Context c, Intent intent) {
        context=c;
        try {
            String state=intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING))
            {
                TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                CustomPhoneStateListener customPhoneListener = new CustomPhoneStateListener();
                telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
                Bundle bundle = intent.getExtras();
                    if (notif==1) {
                String phoneNr= bundle.getString("incoming_number");
                String b =context.getResources().getString(R.string.notifphone)+phoneNr;
               textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH, null);
                Notify.build(context)
                        .setTitle(phoneNr)
                        .setContent(phoneNr)
                        .setSmallIcon(R.drawable.logo)
                        .setLargeIcon(R.drawable.logo)
                        .largeCircularIcon()
                        .setColor(R.color.black)
                        .show();
            }}
            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK))
            {
                TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                CustomPhoneStateListener customPhoneListener = new CustomPhoneStateListener();
                telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);//
                Bundle bundle = intent.getExtras();
                String phoneNr= bundle.getString("incoming_number");
                String b =phoneNr;
            }
            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE))
            {
                TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                CustomPhoneStateListener customPhoneListener = new CustomPhoneStateListener();
                telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
                Bundle bundle = intent.getExtras();
                String phoneNr= bundle.getString("incoming_number");

                    if (notif==1) {
                String b =context.getResources().getString(R.string.notifphone)+phoneNr;
                textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(context, "received", Toast.LENGTH_SHORT).show();
                Notify.build(context)
                        .setTitle(phoneNr)
                        .setContent(phoneNr)
                        .setSmallIcon(R.drawable.logo)
                        .setLargeIcon(R.drawable.logo)
                        .largeCircularIcon()
                        .setColor(R.color.black)
                        .show();
            }}
        } catch (Exception e) {
            Log.e("Phone Receive Error", " " + e);
        }
}}
