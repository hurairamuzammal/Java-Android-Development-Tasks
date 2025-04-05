package com.example.march_13_hometask;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class fragment3 extends Fragment {
    private TextView textView;


    public fragment3() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);

        textView = view.findViewById(R.id.textView);

        // Receive data from Fragment 2
        Bundle bundle = getArguments();
        if (bundle != null) {
            String receivedText = bundle.getString("data2");
            textView.setText("Text from Fragment 2: " + receivedText);
        }

        return view;
    }
}