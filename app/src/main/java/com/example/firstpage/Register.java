package com.example.firstpage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register extends AppCompatActivity {


    private EditText txtFname, txtLname, txtEmail, txtpass, txtconpass;
    private Button SignUpbtn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private TextView textViewLoginNow;

    @Override
    public void onStart() {
        super.onStart();
        // Initialize Firebase Auth instance
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already logged in, redirect to MainActivity
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Find Views
        txtFname = findViewById(R.id.txtFname);
        txtLname = findViewById(R.id.txtLname);
        txtEmail = findViewById(R.id.txtEmail);
        txtpass = findViewById(R.id.txtpass);
        txtconpass = findViewById(R.id.txtconpass);
        SignUpbtn = findViewById(R.id.SignUpbtn);
        progressBar = findViewById(R.id.progressBar); // Initialize progressBar
        textViewLoginNow = findViewById(R.id.loginNow);

        // Set Sign-Up button click listener
        SignUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });

        // Redirect to login page if user clicks "Login Now"
        textViewLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        // Find VideoView by its ID
        VideoView videoView = findViewById(R.id.videoViewBackground);

        // Set the video path (video file in res/raw/main_background.mp4)
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.main_background); // resource name should be lowercase
        videoView.setVideoURI(uri);

        // Start the video and loop it
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);  // Set video looping
            videoView.start();    // Start the video
        });
    }

    private void signUpUser() {
        // Get user input
        String firstName = txtFname.getText().toString().trim();
        String lastName = txtLname.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String password = txtpass.getText().toString().trim();
        String confirmPassword = txtconpass.getText().toString().trim();

        // Input validation
        if (TextUtils.isEmpty(firstName)) {
            txtFname.setError("First name is required.");
            txtFname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lastName)) {
            txtLname.setError("Last name is required.");
            txtLname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            txtEmail.setError("Email is required.");
            txtEmail.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Please provide a valid email.");
            txtEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            txtpass.setError("Password is required.");
            txtpass.requestFocus();
            return;
        }
        if (password.length() < 8) {  // Check if password is at least 8 characters
            txtpass.setError("Password must be at least 8 characters.");
            txtpass.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            txtconpass.setError("Passwords do not match.");
            txtconpass.requestFocus();
            return;
        }

        // Show progressBar while signing up
        progressBar.setVisibility(View.VISIBLE);

        // Create a new user with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign-up successful, update the user profile
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(firstName + " " + lastName)
                                    .build();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(task1 -> {
                                        progressBar.setVisibility(View.GONE); // Hide progressBar after profile update
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(Register.this, "User registered successfully.", Toast.LENGTH_SHORT).show();
                                            // Redirect to MainActivity or home screen
                                            Intent intent = new Intent(Register.this, MainActivity.class);
                                            startActivity(intent);
                                            finish(); // Close the register activity
                                        }
                                    });
                        }
                    } else {
                        progressBar.setVisibility(View.GONE); // Hide progressBar if registration fails
                        Toast.makeText(Register.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}