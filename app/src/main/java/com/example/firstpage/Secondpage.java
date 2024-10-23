package com.example.firstpage;


import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;


public class Secondpage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondpage);


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
