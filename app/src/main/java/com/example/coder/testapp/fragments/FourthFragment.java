package com.example.coder.testapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coder.testapp.R;

public class FourthFragment extends Fragment {

    private static final String TAG = "Fourth fragment";

    //widgets
    private TextView tv;

    //vars
    private String text;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        System.out.println("Bundle comes: " + bundle);

        if (bundle != null) {
            text = bundle.getString("param4");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started.");

        View view = inflater.inflate(R.layout.fragment_fourth, container, false);

        tv = view.findViewById(R.id.text_view_fourth_fragment);

        tv.setText(text);
        return view;
    }
}
