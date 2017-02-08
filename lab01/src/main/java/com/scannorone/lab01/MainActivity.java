package com.scannorone.lab01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private Button applyButton;
    private TextView resultTextView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        applyButton = (Button) findViewById(R.id.applyButton);
        resultTextView = (TextView) findViewById(R.id.resultTextView);

        intent = new Intent(this, SecondActivity.class);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("firstName", firstNameEditText.getText().toString());
                intent.putExtra("lastName", lastNameEditText.getText().toString());
                startActivityForResult(intent, 123);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getStringExtra("result");
        resultTextView.setText(result);
    }
}
