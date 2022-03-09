package com.example.myapplication2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.BatteryManager;
import android.widget.ImageView;
import android.widget.TextView;

public class Batter extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        TextView percentageLabel = ((MainActivity)context).findViewById(R.id.test1);
        ImageView batteryImage = ((MainActivity)context).findViewById(R.id.imgbatt);
            // Percentage
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int percentage = level * 100 / scale;
            percentageLabel.setText(percentage + "%");
           String action = intent.getAction();
           Resources res = context.getResources();
        if (action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)) {
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    batteryImage.setImageDrawable(res.getDrawable(R.drawable.battteriencharge));
                    break;
            }
            if (percentage >= 90) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.batterie100));

            } else if (90 > percentage && percentage >= 75) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.batterie75));

            } else if (75 > percentage && percentage >= 40) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.batterie50));

            } else if (40 > percentage && percentage >= 15) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.batterie25));

            }else if(status == BatteryManager.BATTERY_STATUS_CHARGING )
            {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.battteriencharge));
            }
            else {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.batterie15));

            }

        }
    }
}
