package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.application.api.API;
import com.example.application.api.RegParam;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Sign_up extends AppCompatActivity
{
    private Button button;
    private EditText email, password, lastName, firstName;
    private API api;
    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        button = findViewById(R.id.regButton);
        email = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPassword);
        lastName = findViewById(R.id.regLast);
        firstName = findViewById(R.id.regFirst);
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("http://cinema.areas.su/").build();
        api = retrofit.create(API.class);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Reg();
            }
        });
    }
    private void Reg()
    {
        RegParam regParam = new RegParam();
        regParam.setEmail(email.getText().toString());
        regParam.setPassword(password.getText().toString());
        regParam.setFirstName(firstName.getText().toString());
        regParam.setLastName(lastName.getText().toString());
        Call<RegParam> call = api.doReg(regParam);
        call.enqueue(new Callback<RegParam>()
        {
            @Override
            public void onResponse(Call<RegParam> call, Response<RegParam> response)
            {
                if(response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Регистрация прошла успешно!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Регистрация прошла не успешно!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegParam> call, Throwable t)
            {

            }
        });
    }
}