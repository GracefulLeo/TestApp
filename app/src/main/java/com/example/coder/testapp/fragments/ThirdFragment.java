package com.example.coder.testapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.coder.testapp.R;

import java.net.MalformedURLException;
import java.net.URL;

public class ThirdFragment extends Fragment {

    private static final String TAG = "Third fragment";

    //widget
    private WebView webView;

    //vars
    private String urlString;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        System.out.println("Bundle comes: " + bundle);

        if (bundle != null) {
            urlString = bundle.getString("param3");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started.");

        View view = inflater.inflate(R.layout.fragment_third, container, false);


        webView = view.findViewById(R.id.web_view_third_fragment);


        webView.loadUrl(urlString);
        return view;
    }
}
