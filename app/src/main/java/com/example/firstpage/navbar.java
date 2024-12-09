package com.example.firstpage;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class navbar extends AppCompatActivity {

    private int selectedItemId = R.id.nav_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        String fragmentToLoad = getIntent().getStringExtra("fragment_to_load");

        if (savedInstanceState == null) {
            if ("footprint".equals(fragmentToLoad)) {
                loadFragment(new FootPrintFragment(), false);
                selectedItemId = R.id.nav_footprint;
            } else {
                loadFragment(new FootPrintFragment(), false);
                selectedItemId = R.id.nav_footprint;
            }
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_footprint) {
                selectedFragment = new FootPrintFragment();
                selectedItemId = R.id.nav_footprint;
            } else if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new homepage();
                selectedItemId = R.id.nav_home;
            } else if (item.getItemId() == R.id.nav_challenges) {
                selectedFragment = new ChallengeFragment();
                selectedItemId = R.id.nav_challenges;
            } else if (item.getItemId() == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
                selectedItemId = R.id.nav_profile;
            } else {
                return false;
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment, true);
            }

            for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
                bottomNavigationView.getMenu().getItem(i).getIcon().setTint(Color.GRAY);
            }

            item.getIcon().setTint(Color.parseColor("#90EE90"));
            return true;
        });

        bottomNavigationView.setSelectedItemId(selectedItemId);
    }

    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
