package com.example.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AfterQuestion extends AppCompatActivity {

    private Button nextbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_question);

        // Initialize and set up "Next" button
        nextbtn = findViewById(R.id.nextbtn);
        nextbtn.setOnClickListener(v -> {
            Intent intent = new Intent(AfterQuestion.this, navbar.class);
            // Clear the back stack to prevent going back to this activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Retrieve the selected answer (String)
        String selectedAnswer = getIntent().getStringExtra("selected_answer");
        if (selectedAnswer != null) {
            Toast.makeText(this, "Received answer: " + selectedAnswer, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No answer received", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        // Optionally display a message or log the back press
        Toast.makeText(this, "You cannot go back from this page.", Toast.LENGTH_SHORT).show();

        // Call super.onBackPressed() to adhere to the requirement
        super.onBackPressed();
    }
}
