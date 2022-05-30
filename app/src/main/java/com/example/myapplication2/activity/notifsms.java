package com.example.myapplication2.activity;

import static com.example.myapplication2.MainActivity.notif;
import static com.example.myapplication2.MainActivity.textToSpeech;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.myapplication2.R;

public class notifsms extends BroadcastReceiver {
   public static String msgBody;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
        {   Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            if (bundle != null){
                String msg_from = null;
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                      msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        msgBody = msgs[i].getMessageBody();
                    }
                        if (notif==1) {
                    String b =context.getResources().getString(R.string.notifsms1)+
                            msg_from+context.getResources().getString(R.string.notifsms2)+msgBody;
                    textToSpeech.speak(b, TextToSpeech.QUEUE_FLUSH, null);
                    Notify.build(context)
                            .setTitle(msg_from)
                            .setContent(msgBody)
                            .setSmallIcon(R.drawable.logo)
                            .setLargeIcon(R.drawable.logo)
                            .largeCircularIcon()
                            .setColor(R.color.black)
                            .show();}
                }catch(Exception e){ Log.d("Exception caught",e.getMessage()); } } } }}
