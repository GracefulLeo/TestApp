package com.example.coder.testapp.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.coder.testapp.MainActivity;
import com.example.coder.testapp.R;

import java.io.InputStream;

public class SecondFragment extends Fragment {

    private static final String TAG = "Second fragment";

    //widget
    private ImageView imageView;

    //vars
    private String param;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        System.out.println("Bundle comes: " + bundle);

        if (bundle != null) {
            param = bundle.getString("param2");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started.");

        View view = inflater.inflate(R.layout.fragment_second, container, false);

        imageView = view.findViewById(R.id.image_view_second_fragment);
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(getActivity())
                .load(param)
                .apply(requestOptions)
                .into(imageView);

        return view;
    }
}

