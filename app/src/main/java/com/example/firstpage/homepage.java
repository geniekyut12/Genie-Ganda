package com.example.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class homepage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_homepage, container, false);

        // Set up click listeners for each LinearLayout
        LinearLayout masugidLayout = view.findViewById(R.id.masugidEntR);
        LinearLayout waniLayout = view.findViewById(R.id.waniEntR);
        LinearLayout playmakerLayout = view.findViewById(R.id.playmakerEntR);

        // Set up onClick listeners for each layout
        masugidLayout.setOnClickListener(v -> {
            // Navigate to Masugid Enterprise activity
            Intent intent = new Intent(getActivity(), Masugid_Ent.class); // Replace with actual activity
            startActivity(intent);
        });

        waniLayout.setOnClickListener(v -> {
            // Navigate to Wani Enterprise activity
            Intent intent = new Intent(getActivity(), Wani_Ent.class); // Replace with actual activity
            startActivity(intent);
        });

        playmakerLayout.setOnClickListener(v -> {
            // Navigate to Playmaker Enterprise activity
            Intent intent = new Intent(getActivity(), PlayMaker_Ent.class); // Replace with actual activity
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
