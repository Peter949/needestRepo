package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.api.API;
import com.example.application.api.AuthParam;
import com.example.application.main.Application;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Sign_in extends AppCompatActivity {
    private API api;
    private Retrofit retrofit;
    private Button button;
    private EditText email, password;
    private TextView regText;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        button = findViewById(R.id.authButton);
        email = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPassword);
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("http://cinema.areas.su/").build();
        api = retrofit.create(API.class);
        regText = findViewById(R.id.regText);
        regText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Sign_in.this, Sign_up.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Auth();
            }
        });
    }

    private void Auth()
    {
        AuthParam authParam = new AuthParam();
        authParam.setEmail(email.getText().toString());
        authParam.setPassword(password.getText().toString());
        Call<AuthParam> call = api.doAuth(authParam);
        call.enqueue(new Callback<AuthParam>() {
            @Override
            public void onResponse(Call<AuthParam> call, Response<AuthParam> response)
            {
                if(response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Авторизация прошла успешно!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Sign_in.this, Application.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Авторизация прошла не успешно!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthParam> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}