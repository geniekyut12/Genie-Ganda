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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Questions extends AppCompatActivity {

    private android.widget.ImageView ImageView;
    private TextView HelloText, MotivationalText, DescriptionText;
    private Button CalculateButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_questions);

        // Initialize the UI elements
        ImageView = findViewById(R.id.imageView);
        HelloText = findViewById(R.id.hello);  // Update to use the correct TextView ID
        MotivationalText = findViewById(R.id.motivationalText);
        CalculateButton = findViewById(R.id.calculate);

        // Initialize FirebaseAuth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Get the current user's ID (assumes user is logged in)
        String userId = mAuth.getCurrentUser().getUid();

        // Fetch the first name from Firestore
        DocumentReference userDocRef = db.collection("users").document(userId);
        userDocRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Get the first name from Firestore
                        String firstName = task.getResult().getString("firstName");
                        if (firstName != null) {
                            // Update the HelloText with the personalized greeting
                            HelloText.setText("Hello, " + firstName + "!");
                        }
                    } else {
                        // Handle any errors
                        Toast.makeText(Questions.this, "Failed to get user data", Toast.LENGTH_SHORT).show();
                    }
                });

        // Set up a listener for the calculate button
        CalculateButton.setOnClickListener(v -> performCalculation());
    }

    // Define the method for the button action
    private void performCalculation() {
        // Redirect to Question1 activity
        Intent intent = new Intent(Questions.this, Question1.class);
        startActivity(intent);
    }
}
