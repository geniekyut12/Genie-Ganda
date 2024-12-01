package com.example.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FootPrintFragment extends Fragment {

    private static final String TAG = "FootPrintFragment";

    public FootPrintFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_foot_print_fragment, container, false);

        // Initialize views
        TextView headerText = view.findViewById(R.id.headerText);
        LinearLayout linearLayout1 = view.findViewById(R.id.linearLayout1);
        LinearLayout linearLayout2 = view.findViewById(R.id.linearLayout2);
        LinearLayout linearLayout3 = view.findViewById(R.id.linearLayout3);
        ImageView arrowButton1 = view.findViewById(R.id.arrowButton1);
        ImageView arrowButton2 = view.findViewById(R.id.arrowButton2);
        ImageView arrowButton3 = view.findViewById(R.id.arrowButton3);

        // Retrieve the currently logged-in user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            Log.d(TAG, "User ID: " + userId);

            // Reference to the user's data in Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference userRef = db.collection("users").document(userId);

            // Retrieve first and last name
            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        String firstName = document.getString("firstName");
                        String lastName = document.getString("lastName");

                        Log.d(TAG, "First Name: " + firstName);
                        Log.d(TAG, "Last Name: " + lastName);

                        if (firstName != null && lastName != null) {
                            headerText.setText("Hello, " + firstName + " " + lastName);
                        } else {
                            headerText.setText("Hello, User");
                            Log.e(TAG, "First name or last name is missing for user: " + userId);
                        }
                    } else {
                        Log.e(TAG, "No such document exists for the user: " + userId);
                        headerText.setText("Hello, User");
                    }
                } else {
                    Log.e(TAG, "Error getting document: " + task.getException());
                    headerText.setText("Hello, User");
                }
            });
        } else {
            headerText.setText("Hello, Guest");
            Log.e(TAG, "User is not logged in");
        }

        // Set click listeners for navigation buttons
        arrowButton1.setOnClickListener(v -> startActivity(new Intent(getActivity(), Transportation.class)));
        arrowButton2.setOnClickListener(v -> startActivity(new Intent(getActivity(), Food.class)));
        arrowButton3.setOnClickListener(v -> startActivity(new Intent(getActivity(), Foodwaste.class)));

        return view;
    }
}