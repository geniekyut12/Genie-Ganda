package com.example.firstpage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_profile_fragment, container, false);

        // Log Out Button
        Button logOutButton = rootView.findViewById(R.id.btnLogOut);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the user session (SharedPreferences)
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);  // Set isLoggedIn to false
                editor.apply();  // Apply changes

                // Navigate back to MainActivity (or Signin) after logout
                Intent intent = new Intent(getActivity(), MainActivity.class);  // Navigate to MainActivity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);  // Clear back stack
                startActivity(intent);
                getActivity().finish();  // Finish ProfileFragment (or Activity)
            }
        });

        return rootView;
    }
}
