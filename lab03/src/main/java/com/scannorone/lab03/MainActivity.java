package com.scannorone.lab03;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText nEditText;
    TextView resultTextView;

    Button startFirstServiceButton;
    Button stopFirstServiceButton;
    Intent firstServiceIntent;
    BroadcastReceiver broadcastReceiver;

    Button bindButton;
    Button unbindButton;
    Button startSecondServiceButton;
    Intent secondServiceIntent;
    ServiceConnection serviceConnection;
    MyServiceSecond secondService;
    boolean bound = false;

    Button startThirdServiceButton;
    Intent thirdServiceIntent;

    public static final int STATUS_START = 100;
    public static final int STATUS_FINISH = 200;

    public static final String PARAM_STATUS = "status";
    public static final String PARAM_RESULT = "result";

    public static final String BROADCAST_ACTION = "broadcast_action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nEditText = (EditText) findViewById(R.id.nEditText);
        resultTextView = (TextView) findViewById(R.id.resultTextView);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int status = intent.getIntExtra(PARAM_STATUS, 0);

                if (status == STATUS_START) {
                    resultTextView.setText("Task start");
                }

                if (status == STATUS_FINISH) {
                    String result = intent.getStringExtra(PARAM_RESULT);
                    resultTextView.setText("Task finish, result: " + result);
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);

        createForFirstService();
        createForSecondService();
        createForThirdService();
    }

    void createForFirstService() {
        startFirstServiceButton = (Button) findViewById(R.id.startFirstServiceButton);
        stopFirstServiceButton = (Button) findViewById(R.id.stopFirstServiceButton);
        firstServiceIntent = new Intent(this, MyServiceFirst.class);

        startFirstServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstServiceIntent.putExtra("number", nEditText.getText().toString());
                startService(firstServiceIntent);
            }
        });

        stopFirstServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(firstServiceIntent);
            }
        });
    }

    void createForSecondService() {
        bindButton = (Button) findViewById(R.id.bindButton);
        unbindButton = (Button) findViewById(R.id.unbindButton);
        startSecondServiceButton = (Button) findViewById(R.id.startSecondServiceButton);

        secondServiceIntent = new Intent(this, MyServiceSecond.class);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                secondService = ((MyServiceSecond.MyBinder) service).getService();
                bound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                bound = false;
            }
        };

        bindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(secondServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });

        unbindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bound) {
                    return;
                }
                unbindService(serviceConnection);
                bound = false;
            }
        });

        startSecondServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bound) {
                    int n;
                    try {
                        n = Integer.valueOf(nEditText.getText().toString());
                    } catch (NumberFormatException ex) {
                        n = 1;
                    }
                    String result = secondService.someTask(Math.max(1, n));
                    resultTextView.setText("Task finish, result: " + result);
                }
            }
        });
    }

    void createForThirdService() {
        startThirdServiceButton = (Button) findViewById(R.id.startThirdServiceButton);
        thirdServiceIntent = new Intent(this, MyServiceThird.class);

        startThirdServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thirdServiceIntent.putExtra("number", nEditText.getText().toString());
                startService(thirdServiceIntent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
