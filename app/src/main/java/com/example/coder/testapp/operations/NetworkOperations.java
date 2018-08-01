package com.example.coder.testapp.operations;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.coder.testapp.IMainActivity;
import com.example.coder.testapp.model.Example;
import com.example.coder.testapp.model.Menu;
import com.example.coder.testapp.utils.App;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkOperations {

    private static final String TAG = "Network operations";

    private ArrayList<Menu> menuList;
    private Example mRespons;
    private Context mContext;
    private IMainActivity mInterface;

    public NetworkOperations (Context context){
        this.mContext = context;
    }
    public void getResponses(final List<Menu> list) {


        App.getServerAPI().getExample().enqueue(new Callback<Example>() {
            @Override
            public void onResponse(@NonNull Call<Example> call,
                                   @NonNull Response<Example> response) {
                if (response.body()!= null) {
                    mRespons = response.body();

                    //For retrofit2 to work properly, need to put all logic, into on response
                    //in other case, everything what will be returned in this method almost guaranteed
                    // would be a null
                    if (mRespons != null) {
                        list.addAll(mRespons.getMenu());

                        if (list.size()>0) {

                            System.out.println("list before interface called " + list);
                            mInterface = (IMainActivity) mContext;
                            mInterface.initDrawer(list);

                       }
                    }
                }
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(mContext, "No internet. Relaunch it.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}