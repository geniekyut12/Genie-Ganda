package com.example.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Question2 extends AppCompatActivity {

    private SeekBar seekBar;
    TextView SBtext2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);

        // Initialize the SeekBar
        seekBar = findViewById(R.id.seekBar2);
        SBtext2 = findViewById(R.id.SBtext2);
        // Setup the SeekBar with a listener
        setupSeekBar();
    }

    // Function to set up the SeekBar and handle value changes
    private void setupSeekBar() {
        // Set initial progress (optional)
        seekBar.setProgress(50);  // Set initial progress to 50 out of 100

        // Add listener for value changes
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Show the selected value in a Toast
                SBtext2.setVisibility(View.VISIBLE);
                SBtext2.setText(progress+"/15");

                Toast.makeText(Question2.this, "Selected value: " + progress, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Optionally handle when the user starts sliding
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Optionally handle when the user stops sliding
                Intent intent = new Intent(Question2.this, Question3.class);
                startActivity(intent);
            }
        });
    }
}