package com.example.firstpage;

import android.content.Intent;
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
    // Define the method for the button action
    private void performCalculation() {
        // Redirect to Question1 activity
       Intent intent = new Intent(Questions.this, Question1.class);
        startActivity(intent);
    }

}
