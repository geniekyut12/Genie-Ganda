package com.example.firstpage;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
