package com.example.application.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.KinoAdapter;
import com.example.application.R;
import com.example.application.api.API;
import com.example.application.api.KinoParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Application extends AppCompatActivity
{
    private API api;
    private Retrofit retrofit;
    private RecyclerView listOfKino;
    private List<KinoParam> mKinos;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("http://cinema.areas.su/").build();
        api = retrofit.create(API.class);
        mKinos = new ArrayList<>();
        listOfKino = findViewById(R.id.listOfKino);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listOfKino.setLayoutManager(layoutManager);
        KinoAdapter adapter = new KinoAdapter(mKinos);
        listOfKino.setAdapter(adapter);
        getKino();
    }
    private void getKino()
    {
        Call<List<KinoParam>> call = api.getKino();
        call.enqueue(new Callback<List<KinoParam>>()
        {
            @Override
            public void onResponse(Call<List<KinoParam>> call, Response<List<KinoParam>> response)
            {
                if(response.isSuccessful())
                {
                    mKinos.addAll(response.body());
                    listOfKino.getAdapter().notifyDataSetChanged();
                }
                else
                {
                    try
                    {
                        ResponseBody errorBody = response.errorBody();
                        Toast.makeText(Application.this, errorBody.string(), Toast.LENGTH_SHORT).show();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<KinoParam>> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}