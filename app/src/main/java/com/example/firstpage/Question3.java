package com.example.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Question3 extends AppCompatActivity {

    private RadioGroup radioGroupOptions;
    private Button Question3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);

        // Initialize the views
        Question3 = findViewById(R.id.q3btn);
        radioGroupOptions = findViewById(R.id.radioGroupOptions);

        Question3.setOnClickListener(v -> {
            // Get the selected radio button ID
            int selectedId = radioGroupOptions.getCheckedRadioButtonId();

            if (selectedId == -1) {
                // No option selected
                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
                return;
            }

            // Find the selected RadioButton
            RadioButton selectedRadioButton = findViewById(selectedId);
            String selectedOption = selectedRadioButton.getText().toString();

            // Get the total carbon from previous activity (FoodCarbonActivity)
            double totalCarbon = getIntent().getDoubleExtra("total_carbon", 0.0);

            // Calculate the food waste carbon footprint based on selected option
            double carbonFootprintFromWaste = calculateCarbonFromWaste(selectedOption);

            // Add food waste carbon to the total carbon footprint
            totalCarbon += carbonFootprintFromWaste;

            // Pass the final total carbon footprint to TotalCarbonActivity
            Intent intent = new Intent(Question3.this, AfterQuestion.class);
            intent.putExtra("total_carbon", totalCarbon); // Pass the final total carbon
            startActivity(intent);
        });
    }

    // A sample calculation method based on food waste frequency
    private double calculateCarbonFromWaste(String selectedOption) {
        double carbonEmission = 0;

        switch (selectedOption) {
            case "Never":
                carbonEmission = 0;
                break;
            case "Rarely":
                carbonEmission = 0.5;
                break;
            case "Sometimes":
                carbonEmission = 1.0;
                break;
            case "Often":
                carbonEmission = 1.5;
                break;
            case "Always":
                carbonEmission = 2.0;
                break;
        }

        return carbonEmission;
    }
}
