package com.example.firstpage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Signin extends AppCompatActivity {

    private EditText txtEmail, LastPass;
    private Button Loginbtn, google_sign_in_btn, forgotPassword;
    private ProgressBar progressBar;
    private FirebaseAuth firebase;
    private FirebaseFirestore db;
    private GoogleSignInClient gsc;
    private static final int RC_SIGN_IN = 20;
    private VideoView videoView;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "loginPrefs";
    private static final String PREF_IS_LOGGED_IN = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(PREF_IS_LOGGED_IN, false)) {
            navigateToLoadingPage();
            return;
        }

        setContentView(R.layout.activity_signin);

        initViews();
        setupVideoBackground();
        setupGoogleSignIn();

        google_sign_in_btn.setOnClickListener(v -> signIn());
        Loginbtn.setOnClickListener(v -> signUpUser());

        // Forgot Password
        forgotPassword.setOnClickListener(v -> showForgotPasswordDialog());
    }

    private void initViews() {
        txtEmail = findViewById(R.id.txtEmail);
        LastPass = findViewById(R.id.LastPass);
        Loginbtn = findViewById(R.id.btnLogIn);
        progressBar = findViewById(R.id.progressBar);
        google_sign_in_btn = findViewById(R.id.google_sign_in_btn);
        videoView = findViewById(R.id.videoViewBackground);
        forgotPassword = findViewById(R.id.forgotPassword);

        firebase = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        findViewById(R.id.Register).setOnClickListener(v ->
                startActivity(new Intent(Signin.this, Register.class))
        );
    }

    private void setupVideoBackground() {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mainbg);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.start();
        });
    }

    private void setupGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Sign-in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        progressBar.setVisibility(View.VISIBLE);
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebase.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                handleSuccessfulLogin();
            } else {
                Toast.makeText(Signin.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleSuccessfulLogin() {
        FirebaseUser user = firebase.getCurrentUser();
        if (user != null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", user.getUid());
            map.put("name", user.getDisplayName());
            map.put("profile", user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null);
            db.collection("users").document(user.getUid()).set(map);

            sharedPreferences.edit().putBoolean(PREF_IS_LOGGED_IN, true).apply();
            navigateToLoadingPage();
        }
    }

    private void signUpUser() {
        String input = txtEmail.getText().toString().trim();
        String password = LastPass.getText().toString().trim();

        if (validateInputs(input, password)) {
            progressBar.setVisibility(View.VISIBLE);
            if (Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
                loginWithEmail(input, password);
            } else {
                queryUsername(input, password);
            }
        }
    }

    private boolean validateInputs(String input, String password) {
        if (TextUtils.isEmpty(input)) {
            txtEmail.setError("Email or username is required.");
            txtEmail.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            LastPass.setError("Password is required.");
            LastPass.requestFocus();
            return false;
        }
        return true;
    }

    private void queryUsername(String username, String password) {
        db.collection("users").whereEqualTo("username", username).get().addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                String email = task.getResult().getDocuments().get(0).getString("email");
                loginWithEmail(email, password);
            } else {
                txtEmail.setError("Username not found.");
                txtEmail.requestFocus();
            }
        });
    }

    private void loginWithEmail(String email, String password) {
        firebase.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                handleSuccessfulLogin();
            } else {
                Toast.makeText(Signin.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToLoadingPage() {
        startActivity(new Intent(Signin.this, Loadingpage.class));
        finish();
    }

    private void showForgotPasswordDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Forgot Password");

        final EditText input = new EditText(this);
        input.setHint("Enter your email");
        builder.setView(input);

        builder.setPositiveButton("Send", (dialog, which) -> {
            String email = input.getText().toString().trim();
            if (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                sendPasswordResetEmail(email);
            } else {
                Toast.makeText(Signin.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void sendPasswordResetEmail(String email) {
        progressBar.setVisibility(View.VISIBLE);
        firebase.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                Toast.makeText(Signin.this, "Reset email sent to " + email, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Signin.this, "Error sending reset email: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
