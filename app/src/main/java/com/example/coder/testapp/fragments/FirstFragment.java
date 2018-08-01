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

public class FirstFragment extends Fragment {

    private static final String TAG = "First fragment";

    //widgets
    private TextView textView;

    //vars
    private String param;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        System.out.println("Bundle comes: " + bundle);

        if (bundle != null) {
            param = bundle.getString("param1");
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started.");

        View view = inflater.inflate(R.layout.fragment_first, container, false);

        textView = view.findViewById(R.id.text_view_first_fragment);

        textView.setText(param);

        return view;
    }
}
