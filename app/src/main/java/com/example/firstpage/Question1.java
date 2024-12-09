package com.example.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Question1 extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView SBtext1;
    private Button nextButton;
    private int finalAnswer = 0;
    private RadioButton btnBus, btnJeep, btnCar, btnTric, btnMotor;
    private RadioGroup radioGroupQ1; // Declare the RadioGroup
    private int distance = 5; // Default distance (in km)
    private double totalCarbonFootprint = 0.0;

    // Firebase variables for accessing Firestore
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        // Initialize Firebase Authentication and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize views
        seekBar = findViewById(R.id.seekBar1);
        SBtext1 = findViewById(R.id.SBtext1);
        nextButton = findViewById(R.id.q1btn);

        // Initialize RadioGroup and RadioButtons
        radioGroupQ1 = findViewById(R.id.radioGroupQ1); // Ensure this is present in your XML layout
        btnBus = findViewById(R.id.btnBus);
        btnJeep = findViewById(R.id.btnJeep);
        btnCar = findViewById(R.id.btnCar);
        btnTric = findViewById(R.id.btnTric);
        btnMotor = findViewById(R.id.btnMotor);

        // Set up SeekBar listener to update distance
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distance = progress;
                SBtext1.setText(distance + " km");
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

        nextButton.setOnClickListener(v -> {
            int selectedId = radioGroupQ1.getCheckedRadioButtonId();

            // Check if a transportation method is selected
            if (selectedId == -1) {
                // No RadioButton selected
                Log.d("Question1", "No transportation selected");
                Toast.makeText(Question1.this, "Please select a transportation method.", Toast.LENGTH_SHORT).show();
                return; // Exit early if no selection is made
            }

            // Get the selected transportation method
            RadioButton selectedRadioButton = findViewById(selectedId);
            String selectedTransport = selectedRadioButton.getText().toString();

            // If the SeekBar value is 0, prevent proceeding
            if (distance == 0) {
                Toast.makeText(Question1.this, "Distance cannot be 0. Please adjust the SeekBar.", Toast.LENGTH_SHORT).show();
                return; // Exit early if distance is 0
            }

            // Add the selected transportation method to the transportationDetails string
            StringBuilder transportationDetails = new StringBuilder();
            transportationDetails.append(selectedTransport).append(" ");

            // Example logic for calculating carbon footprint
            switch (selectedTransport) {
                case "Bus":
                    totalCarbonFootprint = distance * 90; // Example: Bus emits 90g CO2 per km
                    break;
                case "Jeep":
                    totalCarbonFootprint = distance * 150;
                    break;
                case "Car":
                    totalCarbonFootprint = distance * 170;
                    break;
                case "Tricycle":
                    totalCarbonFootprint = distance * 71;
                    break;
                case "Motorcycle":
                    totalCarbonFootprint = distance * 60;
                    break;
                case "Bike":
                case "Walk":
                    totalCarbonFootprint = 0; // No emissions
                    break;
            }

            Log.d("Question1", "Selected: " + selectedTransport);
            Log.d("Question1", "Total Carbon Footprint: " + totalCarbonFootprint);
            Log.d("Question1", "Transportation details: " + transportationDetails.toString());

            // Get the current user ID and fetch the user's details from Firestore
            String userId = mAuth.getCurrentUser().getUid();
            CollectionReference usersRef = db.collection("users");

            // Fetch the user document
            usersRef.document(userId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Fetch firstName, lastName, and username from the user document
                        String firstName = document.getString("firstName");
                        String lastName = document.getString("lastName");
                        String username = document.getString("username");

                        // Now, store the data in the 'carbon_footprints' collection
                        CollectionReference carbonFootprintsRef = db.collection("carbon_footprints");

                        // Create a document with user details and the calculated carbon footprint
                        Map<String, Object> carbonData = new HashMap<>();
                        carbonData.put("firstName", firstName);
                        carbonData.put("lastName", lastName);
                        carbonData.put("username", username); // Store username
                        carbonData.put("Question1", distance + " km " + transportationDetails.toString());
                        carbonData.put("carbonFootprint", totalCarbonFootprint);

                        // Add the data to the 'carbon_footprints' collection with auto-generated document ID
                        carbonFootprintsRef.add(carbonData)
                                .addOnSuccessListener(documentReference -> {
                                    Log.d("Firestore", "Data added with auto-generated ID: " + documentReference.getId());
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("Firestore", "Error adding document", e);
                                });

                        // Proceed to the next activity
                        Intent intent = new Intent(Question1.this, Question2.class);
                        intent.putExtra("selectedValue", finalAnswer); // Pass the final answer to the next activity
                        startActivity(intent);
                    }
                } else {
                    Log.d("Firestore", "Failed to fetch user details");
                }
            });
        });
    }
    }
