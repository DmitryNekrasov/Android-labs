package com.scannorone.lab01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {

    EditText resultEditText;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        resultEditText = (EditText) findViewById(R.id.resultEditText);
        backButton = (Button) findViewById(R.id.backButton);

        Intent intent = getIntent();

        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        resultEditText.setText(firstName + " " + lastName);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result", resultEditText.getText().toString());
                setResult(123, intent);
                finish();
            }
        });
    }
}
