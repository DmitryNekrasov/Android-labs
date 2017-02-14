package com.scannorone.lab03;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyServiceFirst extends Service {

    final String LOG_TAG = "myLog";

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        int n;
        try {
            n = Integer.valueOf(intent.getStringExtra("number"));
        } catch (NumberFormatException ex) {
            n = 1;
        }
        someTask(Math.max(1, n));
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    private void someTask(final int n) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.BROADCAST_ACTION);
                intent.putExtra(MainActivity.PARAM_STATUS, MainActivity.STATUS_START);
                sendBroadcast(intent);

                PrimeNumbers primes = new PrimeNumbers(n);

                intent.putExtra(MainActivity.PARAM_STATUS, MainActivity.STATUS_FINISH);
                intent.putExtra(MainActivity.PARAM_RESULT, primes.toString());
                sendBroadcast(intent);

                Log.d(LOG_TAG + " result", primes.toString());
            }
        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
