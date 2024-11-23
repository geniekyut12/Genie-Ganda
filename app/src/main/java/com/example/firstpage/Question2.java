package com.example.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Question2 extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView SBtext2;
    private int finalAnswer = 0; // Variable to store the final selected value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);

        // Initialize the SeekBar and TextView
        seekBar = findViewById(R.id.seekBar2);
        SBtext2 = findViewById(R.id.SBtext2);

        // Setup the SeekBar with a listener
        setupSeekBar();
    }

    private void setupSeekBar() {
        // Set initial progress
        seekBar.setProgress(0);  // Start SeekBar at 0
        SBtext2.setText("0/15"); // Display the initial value

        // Add listener for value changes
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the TextView to show the current progress
                SBtext2.setText(progress + "/15");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Optionally handle when the user starts sliding
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Capture the final progress when the user stops sliding
                finalAnswer = seekBar.getProgress();

                // Display the final value in a Toast
                Toast.makeText(Question2.this, "Selected value: " + finalAnswer, Toast.LENGTH_SHORT).show();

                // Automatically redirect to Question3
                Intent intent = new Intent(Question2.this, Question3.class);
                intent.putExtra("selectedValue", finalAnswer);
                startActivity(intent);

                // Optionally finish the current activity to remove it from the back stack
                finish();
            }
        });
    }
}
