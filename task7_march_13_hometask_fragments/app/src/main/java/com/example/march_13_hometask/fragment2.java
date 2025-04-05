package com.example.march_13_hometask;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class fragment2 extends Fragment {


    private TextView textView;
    private EditText editText;
    private Button btnSend;
    public fragment2() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);

        textView = view.findViewById(R.id.textView);
        editText = view.findViewById(R.id.editText);
        btnSend = view.findViewById(R.id.btnSend);

        // Receive data from Fragment 1
        Bundle bundle = getArguments();
        if (bundle != null) {
            String receivedText = bundle.getString("data1");
            textView.setText("From Fragment 1: " + receivedText);
        }

        btnSend.setOnClickListener(v -> {
            String text = editText.getText().toString();

            fragment3 fragment3 = new fragment3();
            Bundle newBundle = new Bundle();
            newBundle.putString("data2", text);
            fragment3.setArguments(newBundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, fragment3)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}