package com.example.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class FootPrintFragment extends Fragment {

    public FootPrintFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_foot_print_fragment, container, false);

        // Initialize views
        LinearLayout linearLayout1 = view.findViewById(R.id.linearLayout1);
        LinearLayout linearLayout2 = view.findViewById(R.id.linearLayout2);
        LinearLayout linearLayout3 = view.findViewById(R.id.linearLayout3);

        ImageView arrowButton1 = view.findViewById(R.id.arrowButton1);
        ImageView arrowButton2 = view.findViewById(R.id.arrowButton2);
        ImageView arrowButton3 = view.findViewById(R.id.arrowButton3);

        // Set click listeners for each arrow button
        arrowButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Transportation activity
                Intent intent = new Intent(getActivity(), Transportation.class);
                startActivity(intent);
            }
        });

        arrowButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Food activity
                Intent intent = new Intent(getActivity(), Food.class);
                startActivity(intent);
            }
        });

        arrowButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Foodwaste activity
                Intent intent = new Intent(getActivity(), Foodwaste.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
