package com.example.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class Question3 extends AppCompatActivity {

    private RadioGroup radioGroupOptions;
    private Button Question3;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);

        // Initialize Firebase instances
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

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

            // Get the total carbon from previous activity
            double totalCarbon = getIntent().getDoubleExtra("total_carbon", 0.0);

            // Calculate the food waste carbon footprint based on selected option
            double carbonFootprintFromWaste = calculateCarbonFromWaste(selectedOption);

            // Add food waste carbon to the total carbon footprint
            totalCarbon += carbonFootprintFromWaste;

            // Save the final result to Firestore
            saveToFirestore(selectedOption, carbonFootprintFromWaste, totalCarbon);
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

    private void saveToFirestore(String selectedOption, double carbonFootprintFromWaste, double totalCarbon) {
        String userId = mAuth.getCurrentUser().getUid();
        String username = mAuth.getCurrentUser().getDisplayName(); // Assuming username is available as display name

        CollectionReference carbonFootprintsRef = db.collection("carbon_footprints");

        // Fetch and update the existing document
        carbonFootprintsRef.whereEqualTo("username", username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                String docId = document.getId();

                // Prepare the combined data for Question3
                Map<String, Object> question3Data = new HashMap<>();
                question3Data.put("wasteFrequency", selectedOption);
                question3Data.put("wasteCarbonFootprint", carbonFootprintFromWaste + " kg CO₂");

                // Update the existing document with Question3 data and the final total carbon
                carbonFootprintsRef.document(docId).update("Question3", question3Data, "totalCarbonFootprint", totalCarbon)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(Question3.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();

                            // Proceed to the next activity
                            Intent intent = new Intent(Question3.this, AfterQuestion.class);
                            intent.putExtra("total_carbon", totalCarbon); // Pass the final total carbon
                            startActivity(intent);

                            // Optionally finish this activity to remove it from the back stack
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(Question3.this, "Failed to save data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        });

            } else {
                Toast.makeText(Question3.this, "No document found for user: " + username, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
