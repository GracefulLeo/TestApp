package com.example.coder.testapp.operations;

import com.example.coder.testapp.model.Example;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServerAPI {

    @GET("menu.json?dl=1")
    Call<Example> getExample();

}
