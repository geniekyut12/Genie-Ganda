package com.example.firstpage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Loadingpage extends AppCompatActivity {

    private VideoView videoView; // Declare VideoView variable
    private ProgressBar progressBar; // Declare ProgressBar variable
    private static final int LOADING_TIME = 3000; // Duration for loading (3 seconds)
    private static final String TAG = "LoadingPage"; // Tag for logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loadingpage);

        // Initialize the VideoView and ProgressBar
        videoView = findViewById(R.id.videoViewBackground);
        progressBar = findViewById(R.id.progressBar); // Initialize ProgressBar

        // Check if videoView and progressBar are initialized properly
        if (videoView == null || progressBar == null) {
            Log.e(TAG, "VideoView or ProgressBar is not initialized.");
            return; // Exit if not properly initialized
        }

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.main_background);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.start();
            // Start showing loading after the video starts
            showLoading();
        });

        // Show VideoView while loading
        videoView.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.INVISIBLE); // Hide VideoView while loading
        // Simulate progress bar filling
        progressBar.setIndeterminate(false);
        progressBar.setMax(100);
        progressBar.setProgress(0);

        // Use a Handler to update the progress bar
        new Handler().postDelayed(new Runnable() {
            int progressStatus = 0;

            @Override
            public void run() {
                if (progressStatus < 100) {
                    progressStatus++;
                    progressBar.setProgress(progressStatus);
                    // Repeat this every 30 milliseconds
                    new Handler().postDelayed(this, LOADING_TIME / 100); // Adjust the delay
                } else {
                    // Once the progress reaches 100, we redirect to Homepage
                    redirectToHomepage();
                }
            }
        }, LOADING_TIME / 100); // Start updating progress
    }

    private void redirectToHomepage() {
        // Only redirect if the activity is still valid
        if (!isFinishing()) {
            Intent intent = new Intent(Loadingpage.this, Home.class); // Replace with your homepage activity
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release resources
        if (videoView != null) {
            videoView.stopPlayback();
        }
    }
}
