package com.example.myapplication2.activity;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class CustomPhoneStateListener extends PhoneStateListener
{  public void onCallStateChange(int state, String incomingNumber)
{
    switch(state)
    { case TelephonyManager.CALL_STATE_RINGING:
    }
}}


