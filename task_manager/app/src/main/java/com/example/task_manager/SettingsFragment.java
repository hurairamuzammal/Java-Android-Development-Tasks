package com.example.task_manager;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

public class SettingsFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "settings_prefs";
    private static final String KEY_NOTIFICATIONS = "notifications_enabled";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the Switch
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchNotifications = view.findViewById(R.id.notification_switch);

        // Initialize SharedPreferences
        sharedPreferences = getActivity().getSharedPreferences(PREF_NAME, getActivity().MODE_PRIVATE);

        // Load the saved state
        boolean isChecked = sharedPreferences.getBoolean(KEY_NOTIFICATIONS, false);
        switchNotifications.setChecked(isChecked);

        // Save state when toggled
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked1) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_NOTIFICATIONS, isChecked1);
            editor.apply(); // Save changes
        });
    }

}

