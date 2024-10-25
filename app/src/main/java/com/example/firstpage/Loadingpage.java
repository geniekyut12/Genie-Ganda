package com.example.firstpage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class Loadingpage extends AppCompatActivity {

    private Button logoutBtn;
    private GoogleSignInClient googleSignInClient;
    private VideoView videoView; // Declare VideoView variable
    private ProgressBar progressBar; // Declare ProgressBar variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loadingpage);

        // Initialize the VideoView
        videoView = findViewById(R.id.videoViewBackground);
        progressBar = findViewById(R.id.progressBar); // Initialize ProgressBar

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.main_background);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.start();
            progressBar.setVisibility(View.GONE); // Hide ProgressBar when video starts
        });

        // Simulate loading
        showLoading();
        videoView.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.INVISIBLE); // Hide VideoView while loading
    }

    // Call this method when you want to log out
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        googleSignInClient.signOut().addOnCompleteListener(this, task -> {
            Intent intent = new Intent(Loadingpage.this, Homepage   .class); // Replace with your login activity
            startActivity(intent);
            finish();
        });
    }
}
