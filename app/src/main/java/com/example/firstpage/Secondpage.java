package com.example.firstpage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class Secondpage extends AppCompatActivity {

    private Button btnSignIn;
    private Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondpage);


        // Register page button
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Secondpage.this, Register.class);
                startActivity(intent);
            }
        });

        // Login page button
        btnLogIn = findViewById(R.id.btnLogIn); // Ensure this ID matches the one in your XML
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Secondpage.this, Signin.class); // Ensure class name is correct
                startActivity(intent);
                finish();
            }
        });

        // Video background setup
        VideoView videoView = findViewById(R.id.videoViewBackground);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.blur_mainbg); // Ensure file name is correct and lowercase
        videoView.setVideoURI(uri);

        // Start video loop
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.start();
        });
    }
}
