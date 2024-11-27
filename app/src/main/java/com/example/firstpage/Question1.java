package com.example.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Question1 extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView SBtext1;
    private Button nextButton;
    private int finalAnswer = 0; // Variable to store the final selected value

    private CheckBox checkboxBus, checkboxJeep, checkboxCar, checkboxTric, checkboxMotor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        // Initialize views
        seekBar = findViewById(R.id.seekBar1);
        SBtext1 = findViewById(R.id.SBtext1);
        nextButton = findViewById(R.id.q1btn);

        // CheckBox initialization
        checkboxBus = findViewById(R.id.checkboxBus);
        checkboxJeep = findViewById(R.id.checkboxJeep);
        checkboxCar = findViewById(R.id.checkboxCar);
        checkboxTric = findViewById(R.id.checkboxTric);
        checkboxMotor = findViewById(R.id.checkboxMotor);

        // Setup the SeekBar
        setupSeekBar();

        // Next button click listener
        nextButton.setOnClickListener(v -> validateAndProceed());
    }

    private void setupSeekBar() {
        // Set initial progress (optional)
        seekBar.setProgress(0);  // Set initial progress to the midpoint (assuming max = 50)
        SBtext1.setText("0/50"); // Display the initial value

        // Add listener for value changes
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the text dynamically but do not record the value yet
                SBtext1.setVisibility(View.VISIBLE);
                SBtext1.setText(progress + "/50");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Optionally handle when the user starts sliding
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Record the final value when the user stops sliding
                finalAnswer = seekBar.getProgress();
            }
        });
    }

    private void validateAndProceed() {
        // Check if SeekBar is not at 0 progress
        if (finalAnswer == 0) {
            Toast.makeText(this, "Please select a value on the SeekBar", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if at least one checkbox is selected
        if (!(checkboxBus.isChecked() || checkboxJeep.isChecked() || checkboxCar.isChecked() ||
                checkboxTric.isChecked() || checkboxMotor.isChecked())) {
            Toast.makeText(this, "Please select at least one transportation option", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proceed to the next activity if validation passes
        Intent intent = new Intent(Question1.this, Question2.class);
        intent.putExtra("selectedValue", finalAnswer); // Pass the final answer to the next activity
        startActivity(intent);
    }
}
