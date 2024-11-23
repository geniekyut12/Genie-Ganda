package com.example.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Question3 extends AppCompatActivity {

    private RadioGroup radioGroupOptions;
    private int finalAnswer = -1; // Variable to store the final selected value (-1 means no selection)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);

        // Initialize the RadioGroup
        radioGroupOptions = findViewById(R.id.radioGroupOptions);

        // Set up the RadioGroup listener
        setupRadioGroup();
    }

    private void setupRadioGroup() {
        radioGroupOptions.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioNever) {
                finalAnswer = 0;
            } else if (checkedId == R.id.radioRarely) {
                finalAnswer = 1;
            } else if (checkedId == R.id.radioSometimes) {
                finalAnswer = 2;
            } else if (checkedId == R.id.radioOften) {
                finalAnswer = 3;
            } else if (checkedId == R.id.radioAlways) {
                finalAnswer = 4;
            } else {
                finalAnswer = -1; // No valid selection
            }

            // Display a toast message for the selected option
            String selectedOption = ((RadioButton) findViewById(checkedId)).getText().toString();
            Toast.makeText(Question3.this, "Selected: " + selectedOption, Toast.LENGTH_SHORT).show();

            // Redirect to the next activity after selection
            redirectToNextActivity();
        });
    }


    private void redirectToNextActivity() {
        if (finalAnswer != -1) {
            Intent intent = new Intent(Question3.this, AfterQuestion.class);
            intent.putExtra("selectedValue", finalAnswer);
            startActivity(intent);

            // Finish current activity to prevent returning to it
            finish();
        } else {
            Toast.makeText(this, "Please select an option.", Toast.LENGTH_SHORT).show();
        }
    }
}
