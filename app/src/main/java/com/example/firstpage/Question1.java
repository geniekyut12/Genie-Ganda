package com.example.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Question1 extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView SBtext1;
    private Button nextButton;
    private int finalAnswer = 0; // Variable to store the final selected value

    private CheckBox checkboxBus, checkboxJeep, checkboxCar, checkboxTric, checkboxMotor;
    private int distance = 5; // Default distance (in km)
    private double totalCarbonFootprint = 0.0;

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

        // Log initialization to check if views are null
        Log.d("Question1", "SeekBar: " + seekBar);
        Log.d("Question1", "TextView: " + SBtext1);
        Log.d("Question1", "CheckBox (Tricycle): " + checkboxTric);
        Log.d("Question1", "CheckBox (Jeep): " + checkboxJeep);
        Log.d("Question1", "CheckBox (Bus): " + checkboxBus);
        Log.d("Question1", "CheckBox (Motorcycle): " + checkboxMotor);
        Log.d("Question1", "CheckBox (Car): " + checkboxCar);
        Log.d("Question1", "Button: " + nextButton);

        // Set up SeekBar listener to update distance
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distance = progress;
                SBtext1.setText(distance + " km"); // Update the distance display
                SBtext1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });

        // Set up the Next button listener
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the total carbon footprint
                totalCarbonFootprint = 0.0;

                // Calculate carbon footprint for each mode of transportation
                if (checkboxTric.isChecked()) {
                    totalCarbonFootprint += distance * 71; // Example: Tricycle emits 71g CO2 per km
                }
                if (checkboxJeep.isChecked()) {
                    totalCarbonFootprint += distance * 150; // Example: Jeep emits 150g CO2 per km
                }
                if (checkboxBus.isChecked()) {
                    totalCarbonFootprint += distance * 90; // Example: Bus emits 90g CO2 per km
                }
                if (checkboxCar.isChecked()) {
                    totalCarbonFootprint += distance * 170; // Example: Car emits 170g CO2 per km
                }
                if (checkboxMotor.isChecked()) {
                    totalCarbonFootprint += distance * 0; // Motor (bicycle) emits 0g CO2 per km
                }

                // Log the carbon footprint for debugging
                Log.d("Question1", "Total Carbon Footprint: " + totalCarbonFootprint);

                // Proceed to the next activity
                Intent intent = new Intent(Question1.this, Question2.class);
                intent.putExtra("selectedValue", finalAnswer); // Pass the final answer to the next activity
                startActivity(intent);
            }
        });
    }
}
