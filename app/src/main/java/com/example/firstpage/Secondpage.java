package com.example.firstpage;


import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;


public class Secondpage extends AppCompatActivity {

    private Button btnSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondpage);

        // next page
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Secondpage.this, Register.class);
                startActivity(intent);
            }
        });

        // Find VideoView by its ID
        VideoView videoView = findViewById(R.id.videoViewBackground);


        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.blur_mainbg); // yung filename dapat all lowercase
        videoView.setVideoURI(uri);


        // Start at loop ng vid
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.start();
        });
    }
}
