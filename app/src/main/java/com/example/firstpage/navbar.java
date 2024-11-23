package com.example.firstpage;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class navbar extends AppCompatActivity {

    private int selectedItemId = R.id.nav_home; // Store the selected item ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        // Check if a specific fragment is requested via intent
        String fragmentToLoad = getIntent().getStringExtra("fragment_to_load");

        // Load the specified fragment, or default to homepage
        if (savedInstanceState == null) {
            if (fragmentToLoad != null && fragmentToLoad.equals("footprint")) {
                loadFragment(new FootPrintFragment(), false); // No back stack for the first fragment
                selectedItemId = R.id.nav_footprint;
            } else {
                loadFragment(new homepage(), false); // Default homepage fragment
                selectedItemId = R.id.nav_home;
            }
        }

        // Set up the bottom navigation view item selection listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            // Determine which fragment to load based on the selected item
            if (item.getItemId() == R.id.nav_footprint) {
                selectedFragment = new FootPrintFragment();
                selectedItemId = R.id.nav_footprint;
            } else if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new homepage();
                selectedItemId = R.id.nav_home;
            } else if (item.getItemId() == R.id.nav_rewards) {
                selectedFragment = new RewardsFragment();
                selectedItemId = R.id.nav_rewards;
            } else if (item.getItemId() == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
                selectedItemId = R.id.nav_profile;
            } else {
                return false;
            }

            // Load the selected fragment and add it to the back stack
            if (selectedFragment != null) {
                loadFragment(selectedFragment, true); // Add to back stack
            }

            // Reset all items to default icon color
            resetItemColors(bottomNavigationView);

            // Highlight the selected item icon with a light green color
            item.getIcon().setTint(Color.parseColor("#90EE90")); // Light green

            return true;
        });

        // Ensure the previously selected item is marked as selected when the activity is restored
        bottomNavigationView.setSelectedItemId(selectedItemId);
    }

    // Helper method to load a fragment into the fragment container
    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment); // Replace existing fragment

        if (addToBackStack) {
            transaction.addToBackStack(null); // Add fragment transaction to back stack
        }

        transaction.commit(); // Commit the transaction
    }

    // Reset all items to their default color
    private void resetItemColors(BottomNavigationView bottomNavigationView) {
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            bottomNavigationView.getMenu().getItem(i).getIcon().setTint(Color.GRAY); // Default color
        }
    }

    @Override
    public void onBackPressed() {
        // Handle back press event: Pop from the fragment stack if possible
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack(); // Pop the fragment off the stack

            // After popping the fragment, update the selected item color
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
            resetItemColors(bottomNavigationView);

            // Update the selected item based on the fragment shown
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment instanceof FootPrintFragment) {
                bottomNavigationView.setSelectedItemId(R.id.nav_footprint);
            } else if (currentFragment instanceof homepage) {
                bottomNavigationView.setSelectedItemId(R.id.nav_home);
            } else if (currentFragment instanceof RewardsFragment) {
                bottomNavigationView.setSelectedItemId(R.id.nav_rewards);
            } else if (currentFragment instanceof ProfileFragment) {
                bottomNavigationView.setSelectedItemId(R.id.nav_profile);
            }

            // Highlight the newly selected item
            bottomNavigationView.getMenu().getItem(bottomNavigationView.getSelectedItemId()).getIcon().setTint(Color.parseColor("#90EE90")); // Light green
        } else {
            super.onBackPressed(); // Default behavior (finish activity if no fragments left in stack)
        }
    }
}
