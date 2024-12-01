package com.example.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Question2 extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView SBtext2;
    private Button Question2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);

        // Initialize the SeekBar, TextView, and Button
        seekBar = findViewById(R.id.seekBar2);
        SBtext2 = findViewById(R.id.SBtext2);


        // Retrieve transportation carbon footprint from intent
        double transportationCarbon = getIntent().getDoubleExtra("transportation_carbon", 0.0);

        // Set up SeekBar listener
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the TextView with current progress
                SBtext2.setText(progress + " kg");
                SBtext2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Optional: Handle when the user starts sliding
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Capture the final weight value when the user stops sliding
                int weight = seekBar.getProgress();

                // Perform calculation for food carbon footprint
                double foodCarbon = calculateCarbonFootprint(weight);

                // Combine food carbon footprint with transportation carbon footprint
                double totalCarbon = transportationCarbon + foodCarbon;

                // Show a Toast with the calculated carbon footprint
                Toast.makeText(Question2.this, "Total Carbon Footprint: " + totalCarbon + " kg CO₂", Toast.LENGTH_SHORT).show();

                // Redirect to the next activity (FoodWasteActivity)
                Intent intent = new Intent(Question2.this, Question3.class);
                intent.putExtra("total_carbon", totalCarbon);
                startActivity(intent);

                // Optionally finish this activity to remove it from the back stack
                finish();
            }
        });
    }

    // Example method to calculate carbon footprint based on weight (kg)
    private double calculateCarbonFootprint(int weight) {
        // Example logic: 2.5 kg CO₂ per kg of food
        return weight * 2.5;
    }
}
