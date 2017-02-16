package com.scannorone.lab03;

import android.app.IntentService;
import android.content.Intent;

public class MyServiceThird extends IntentService {

    public MyServiceThird() {
        super("MyServiceThird");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Intent resultIntent = new Intent(MainActivity.BROADCAST_ACTION);
            resultIntent.putExtra(MainActivity.PARAM_STATUS, MainActivity.STATUS_START);
            sendBroadcast(resultIntent);

            int n;
            try {
                n = Integer.valueOf(intent.getStringExtra("number"));
            } catch (NumberFormatException ex) {
                n = 1;
            }

            PrimeNumbers primes = new PrimeNumbers(Math.max(1, n));

            resultIntent.putExtra(MainActivity.PARAM_STATUS, MainActivity.STATUS_FINISH);
            resultIntent.putExtra(MainActivity.PARAM_RESULT, primes.toString());
            sendBroadcast(resultIntent);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
