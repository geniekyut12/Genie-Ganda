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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Signin extends AppCompatActivity {

    private EditText txtEmail, LastPass;
    private Button Loginbtn, google_sign_in_btn;
    private ProgressBar progressBar;
    private GoogleSignInOptions gso;
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
        boolean isLoggedIn = sharedPreferences.getBoolean(PREF_IS_LOGGED_IN, false);

        if (isLoggedIn) {
            startActivity(new Intent(Signin.this, Loadingpage.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_signin);

        TextView registerTextView = findViewById(R.id.Register);
        registerTextView.setOnClickListener(v -> startActivity(new Intent(Signin.this, Register.class)));

        txtEmail = findViewById(R.id.txtEmail);
        LastPass = findViewById(R.id.LastPass);
        Loginbtn = findViewById(R.id.btnLogIn);
        progressBar = findViewById(R.id.progressBar);
        google_sign_in_btn = findViewById(R.id.google_sign_in_btn);
        videoView = findViewById(R.id.videoViewBackground);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mainbg);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.start();
        });

        firebase = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        gsc = GoogleSignIn.getClient(Signin.this, gso);

        google_sign_in_btn.setOnClickListener(v -> signIn());
        Loginbtn.setOnClickListener(v -> signUpUser());
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
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebase.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE); // Hide ProgressBar once login is complete
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebase.getCurrentUser();
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("id", user.getUid());
                        map.put("name", user.getDisplayName());
                        map.put("profile", user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null);
                        db.collection("users").document(user.getUid()).set(map);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(PREF_IS_LOGGED_IN, true);
                        editor.apply();

                        startActivity(new Intent(Signin.this, Loadingpage.class));
                        finish();
                    } else {
                        Toast.makeText(Signin.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signUpUser() {
        String input = txtEmail.getText().toString().trim(); // This can be email or username
        String password = LastPass.getText().toString().trim();

        if (TextUtils.isEmpty(input)) {
            txtEmail.setError("Email or username is required.");
            txtEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            LastPass.setError("Password is required.");
            LastPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        if (Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            loginWithEmail(input, password);
        } else {
            // Query Firestore instead of Realtime Database
            db.collection("users")
                    .whereEqualTo("username", input)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            String email = task.getResult().getDocuments().get(0).getString("email");
                            loginWithEmail(email, password);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            txtEmail.setError("Username not found.");
                            txtEmail.requestFocus();
                        }
                    });
        }
    }

    private void loginWithEmail(String email, String password) {
        firebase.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebase.getCurrentUser();
                        if (user != null && user.isEmailVerified()) {
                            // Email verified, log the user in
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(PREF_IS_LOGGED_IN, true);
                            editor.apply();

                            startActivity(new Intent(Signin.this, Loadingpage.class));
                            finish();
                        } else if (user != null && !user.isEmailVerified()) {
                            // Email not verified, show error message
                            Toast.makeText(Signin.this, "Please verify your email before logging in.", Toast.LENGTH_SHORT).show();
                            firebase.signOut();  // Sign the user out if email is not verified
                        } else {
                            Toast.makeText(Signin.this, "User not found.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Login failed due to wrong credentials or other errors
                        Toast.makeText(Signin.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void logoutUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_IS_LOGGED_IN, false);
        editor.apply();

        firebase.signOut();
        gsc.signOut().addOnCompleteListener(this, task -> {
            Intent intent = new Intent(Signin.this, Signin.class);
            startActivity(intent);
            finish();
        });
    }
}