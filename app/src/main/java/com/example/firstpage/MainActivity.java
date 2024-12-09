package com.example.firstpage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button getstartbtn;
    private static final String PREFS_NAME = "loginPrefs";
    private static final String PREF_IS_LOGGED_IN = "isLoggedIn";
    private static final String PREF_HAS_COMPLETED_ONBOARDING = "hasCompletedOnboarding";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Access SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Check if user is logged in and has completed onboarding
        boolean isLoggedIn = sharedPreferences.getBoolean(PREF_IS_LOGGED_IN, false);
        boolean hasCompletedOnboarding = sharedPreferences.getBoolean(PREF_HAS_COMPLETED_ONBOARDING, false);

        if (isLoggedIn) {
            if (hasCompletedOnboarding) {
                // Redirect to navbar if onboarding is complete
                Intent intent = new Intent(MainActivity.this, navbar.class);
                startActivity(intent);
            } else {
                // Redirect to onboarding questions if not complete
                Intent intent = new Intent(MainActivity.this, Questions.class);
                startActivity(intent);
            }
            finish();  // Stop MainActivity
            return;
        }

        // If not logged in, show MainActivity layout
        setContentView(R.layout.activity_main);

        // Initialize and set up "Get Started" button
        getstartbtn = findViewById(R.id.getstartbtn);
        getstartbtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Signin.class);
            startActivity(intent);
        });

        // Initialize and set up VideoView
        VideoView videoView = findViewById(R.id.videoViewBackground);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mainbg);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.start();
        });
    }
}
