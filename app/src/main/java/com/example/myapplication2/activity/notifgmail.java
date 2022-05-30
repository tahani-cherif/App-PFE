package com.example.myapplication2.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;

public class notifgmail extends BroadcastReceiver {

    Context cntxt;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Email Received", Toast.LENGTH_LONG).show();
        showNotification(context);
    }

    private void showNotification(Context context) {

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Resources res = context.getResources();
        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.gojo)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.gojo))
                .setTicker(res.getString(R.string.app_name))
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(res.getString(R.string.app_name))
                .setContentText(res.getString(R.string.app_name));
        Notification n = builder.getNotification();
        nm.notify(1, n);
    }
}
