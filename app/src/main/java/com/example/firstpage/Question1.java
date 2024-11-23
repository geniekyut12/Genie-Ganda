package com.example.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Question1 extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView SBtext1;
    private int finalAnswer = 0; // Variable to store the final selected value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        // Initialize the SeekBar and TextView
        seekBar = findViewById(R.id.seekBar1);
        SBtext1 = findViewById(R.id.SBtext1);

        // Setup the SeekBar with a listener
        setupSeekBar();
    }

    // Function to set up the SeekBar and handle value changes
    private void setupSeekBar() {
        // Set initial progress (optional)
        seekBar.setProgress(0);  // Set initial progress to the midpoint (assuming max = 15)
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
                Toast.makeText(Question1.this, "Final selected value: " + finalAnswer, Toast.LENGTH_SHORT).show();

                // Move to the next question
                Intent intent = new Intent(Question1.this, Question2.class);
                intent.putExtra("selectedValue", finalAnswer); // Pass the final answer to the next activity
                startActivity(intent);
            }
        });
    }
}
