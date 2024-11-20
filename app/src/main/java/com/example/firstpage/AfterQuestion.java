package com.example.firstpage;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AfterQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_question);

        // Retrieve the selected answer (String)
        String selectedAnswer = getIntent().getStringExtra("selected_answer"); // Use the correct key
        if (selectedAnswer != null) {
            Toast.makeText(this, "Received answer: " + selectedAnswer, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No answer received", Toast.LENGTH_SHORT).show();
        }
    }
}
