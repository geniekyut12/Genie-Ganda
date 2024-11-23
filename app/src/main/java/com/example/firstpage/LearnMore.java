package com.example.firstpage;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class LearnMore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_more);

        // Initialize Back Button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> {
            // Finish the current activity to return to the previous one
            finish();
        });
    }
}


