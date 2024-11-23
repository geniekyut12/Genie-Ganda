package com.example.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class homepage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_homepage, container, false);

        // Set an OnClickListener for the "Learn More" TextView
        TextView learnMoreTextView = view.findViewById(R.id.learn_more_button);
        learnMoreTextView.setOnClickListener(v -> {
            // Navigate to the next activity when clicked
            Intent intent = new Intent(getActivity(), LearnMore.class); // Replace LearnMore.class with your target activity
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // You can perform any additional tasks when returning to this fragment
    }
}
