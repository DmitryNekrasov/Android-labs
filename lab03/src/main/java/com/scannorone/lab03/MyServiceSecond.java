package com.scannorone.lab03;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyServiceSecond extends Service {

    MyBinder binder = new MyBinder();

    public MyServiceSecond() {
    }

    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public String someTask(int n) {
        PrimeNumbers primes = new PrimeNumbers(n);
        return primes.toString();
    }

    class MyBinder extends Binder {
        MyServiceSecond getService() {
            return MyServiceSecond.this;
        }
    }
}
