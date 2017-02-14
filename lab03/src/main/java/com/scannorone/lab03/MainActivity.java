package com.scannorone.lab03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText nEditText;

    Button startFirstServiceButton;
    Button stopFirstServiceButton;
    Intent firstServiceIntent;

    TextView resultTextView;

    BroadcastReceiver broadcastReceiver;

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

        startFirstServiceButton = (Button) findViewById(R.id.startFirstServiceButton);
        stopFirstServiceButton = (Button) findViewById(R.id.stopFirstServiceButton);
        firstServiceIntent = new Intent(this, MyServiceFirst.class);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
