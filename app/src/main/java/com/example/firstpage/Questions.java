package com.example.firstpage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Questions extends AppCompatActivity {

    private android.widget.ImageView ImageView;
    private TextView MotivationalText, DescriptionText;
    private Button CalculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_questions);

        // Initialize the UI elements
        ImageView = findViewById(R.id.imageView);
        MotivationalText = findViewById(R.id.motivationalText);
        DescriptionText = findViewById(R.id.descriptionText);
        CalculateButton = findViewById(R.id.calculate);

        // Set up a listener for the calculate button
        CalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performCalculation();
            }
        });
    }

    // Define the method for the button action
    private void performCalculation() {
        // For now, show a simple Toast message for the calculation function
        Toast.makeText(this, "Calculation performed!", Toast.LENGTH_SHORT).show();

        // Additional logic for calculation can be added here
    }
}
