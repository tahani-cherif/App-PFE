package com.example.myapplication2.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.BatteryManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication2.MainActivity;
import com.example.myapplication2.R;

public class Activity_Battery extends BroadcastReceiver {
    public static int percentage;
    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences sharedPreferences= context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("battrie", "yes");
        editor.apply();
        TextView percentageLabel = ((MainActivity)context).findViewById(R.id.test1);
        ImageView batteryImage = ((MainActivity)context).findViewById(R.id.imgbatt);
            // Percentage
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            percentage = level * 100 / scale;
            percentageLabel.setText(percentage + "%");
            String action = intent.getAction();
            Resources res = context.getResources();
        if (action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)) {
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            String message = "";

            switch (status) {
                case BatteryManager.BATTERY_STATUS_FULL:
                    message = "Full";
                    break;
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    message = "Charging";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    message = "Discharging";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    message = "Not charging";
                    break;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    message = "Unknown";
                    break;
            }
            if(message.equals("Charging"))
            {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.battteriencharge));
            }
            else if (percentage >= 90) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.batterie100));

            } else if (90 > percentage && percentage >= 75) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.batterie75));

            } else if (75 > percentage && percentage >= 40) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.batterie50));
            } else if (40 > percentage && percentage >= 15) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.batterie25));
            }
            else {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.batterie15));

            }

        }

    }

}
