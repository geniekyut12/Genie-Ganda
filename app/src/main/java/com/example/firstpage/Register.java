package com.example.firstpage;

import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.graphics.Typeface;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import androidx.appcompat.app.AlertDialog;
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
    private CheckBox checkboxTerms;

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        txtFname = findViewById(R.id.txtFname);
        txtLname = findViewById(R.id.txtLname);
        txtEmail = findViewById(R.id.txtEmail);
        txtpass = findViewById(R.id.txtpass);
        txtconpass = findViewById(R.id.txtconpass);
        SignUpbtn = findViewById(R.id.SignUpbtn);
        progressBar = findViewById(R.id.progressBar);
        textViewLoginNow = findViewById(R.id.loginNow);
        checkboxTerms = findViewById(R.id.checkboxTerms);

        // Show terms and conditions dialog on checkbox click
        checkboxTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTermsDialog();
            }
        });

        SignUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkboxTerms.isChecked()) {
                    Toast.makeText(getApplicationContext(), "You must agree to the terms and conditions.", Toast.LENGTH_SHORT).show();
                    return;
                }
                signUpUser();
            }
        });

        textViewLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Signin.class);
                startActivity(intent);
                finish();
            }
        });

        VideoView videoView = findViewById(R.id.videoViewBackground);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mainbg);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.start();
        });
    }

    private void showTermsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Terms and Conditions");

        String terms = "Terms and Conditions for C Verde\n" +
                "\n" +
                "1. Acceptance of Terms\n" +
                "•\tBy downloading, installing, or using the C-Verde application, you agree to be bound by these Terms and Conditions. If you do not agree to these terms, please do not use the application.\n" +
                "\n" +
                "2. Use of the Application\n" +
                "•\tC-Verde is a carbon footprint tracker that allows users to monitor and reduce their environmental impact. By using the application, you agree to:\n" +
                "•\tProvide accurate data regarding your activities to ensure accurate carbon footprint tracking.\n" +
                "•\tUse the app for lawful purposes and in compliance with all applicable laws.\n" +
                "•\tAvoid any unauthorized access, tampering, or reverse engineering of the application.\n" +
                "\n" +
                "3. Data Collection and Privacy\n" +
                "•\tC-Verde collects and processes personal information, such as your daily activities, energy usage, and travel habits, to calculate your carbon footprint. The information is stored securely and used solely for the purpose of improving your experience with the application.\n" +
                "•\tYour data will not be shared with third parties without your consent, except as required by law.\n" +
                "•\tWe take reasonable steps to ensure your data is protected, but no system is completely secure. By using the app, you acknowledge the risks of data breaches.\n" +
                "\n" +
                "4. User Responsibilities\n" +
                "You are responsible for:\n" +
                "•\tEnsuring that the information you provide is accurate and up-to-date.\n" +
                "•\tRegularly updating the app to benefit from new features and improvements.\n" +
                "•\tComplying with all local laws regarding environmental reporting if applicable.\n" +
                "\n" +
                "5. Accuracy of Information\n" +
                "•\tWhile C-Verde strives to provide accurate carbon footprint calculations, the results depend on the data you input. Variations in activities, energy sources, and other factors can lead to estimates rather than exact values. Therefore, C-Verde cannot guarantee the absolute accuracy of the carbon footprint estimates provided.\n" +
                "\n" +
                "6. Limitation of Liability\n" +
                "•\tC-Verde and its developers will not be liable for any direct, indirect, incidental, or consequential damages arising from your use of the application, including errors in carbon footprint estimations, data loss, or interruptions to the service.\n" +
                "\n" +
                "7. Updates and Changes\n" +
                "•\tC-Verde reserves the right to modify or update these Terms and Conditions at any time. Users will be notified of any changes via the app or email. Continued use of the app after changes are posted signifies your acceptance of the new terms.\n" +
                "\n" +
                "8. Termination\n" +
                "•\tWe reserve the right to terminate your access to C Verde if you violate any of these Terms and Conditions. Termination can occur without prior notice.\n" +
                "\n" +
                "9. Governing Law\n" +
                "•\tThese Terms and Conditions are governed by and construed in accordance with the laws.\n" +
                "\n" +
                "10. Contact Information\n" +
                "•\tFor any questions or concerns regarding these Terms and Conditions or the use of the C-Verde application, you can contact us at C-Verde Facebook page.\n";

        // Initialize spannableTerms with the terms string
        SpannableString spannableTerms = new SpannableString(terms);

        // Array of titles and their starting positions
        String[] titles = {
                "Terms and Conditions for C Verde",
                "1. Acceptance of Terms",
                "2. Use of the Application",
                "3. Data Collection and Privacy",
                "4. User Responsibilities",
                "5. Accuracy of Information",
                "6. Limitation of Liability",
                "7. Updates and Changes",
                "8. Termination",
                "9. Governing Law",
                "10. Contact Information",
        };

        // Bold the titles and their corresponding numbers
        for (String title : titles) {
            int startIndex = terms.indexOf(title);
            if (startIndex != -1) {
                int endIndex = startIndex + title.length();
                spannableTerms.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        // Set the spannable message to the dialog
        builder.setMessage(spannableTerms);

        builder.setPositiveButton("I Agree", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkboxTerms.setChecked(true); // Mark checkbox as checked when user agrees
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkboxTerms.setChecked(false); // Uncheck the checkbox if user cancels
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void signUpUser() {
        String firstName = txtFname.getText().toString().trim();
        String lastName = txtLname.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String password = txtpass.getText().toString().trim();
        String confirmPassword = txtconpass.getText().toString().trim();

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
        if (password.length() < 8) {
            txtpass.setError("Password must be at least 8 characters.");
            txtpass.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            txtconpass.setError("Passwords do not match.");
            txtconpass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(firstName + " " + lastName)
                                    .build();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            sendVerificationEmail(user);
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(Register.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendVerificationEmail(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(Register.this, "Verification email sent. Please check your email.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, Signin.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Register.this, "Failed to send verification email", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
