package com.example.firstpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class Question3 extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton selectedRadioButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Replace with your layout XML name

        radioGroup = findViewById(R.id.FScale);

        // Set the listener to handle radio button selection
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check if a radio button is selected
                selectedRadioButton = findViewById(checkedId);

                // Check if any radio button is selected, then navigate to the next activity
                if (selectedRadioButton != null) {
                    // Proceed to the next screen when a choice is selected
                    navigateToNextScreen();
                }
            }
        });
    }

    private void navigateToNextScreen() {
        // Intent to move to AfterQuestion Activity
        Intent intent = new Intent(Question3.this, AfterQuestion.class);
        startActivity(intent);
    }
}
