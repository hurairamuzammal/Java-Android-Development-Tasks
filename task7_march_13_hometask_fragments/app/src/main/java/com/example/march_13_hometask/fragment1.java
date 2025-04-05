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


public class fragment1 extends Fragment {


    private EditText editText;
    private Button btnSend;

    // TODO: Rename and change types of parameters

    public fragment1() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);

        editText = view.findViewById(R.id.editText);
        btnSend = view.findViewById(R.id.btnSend);

        btnSend.setOnClickListener(v -> {
            String text = editText.getText().toString();

            fragment2 fragment2 = new fragment2();
            Bundle bundle = new Bundle();
            bundle.putString("data1", text);
            fragment2.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, fragment2)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}