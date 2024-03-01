package com.example.stepcal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stepcal.API.ApiInterface;
import com.example.stepcal.DTO.User;
import com.example.stepcal.R;
import com.example.stepcal.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    private ApiInterface apiInterface;

    private String userEmail;
    private String authToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        apiInterface= RetrofitClient.getRetrofit().create(ApiInterface.class);
        authToken=getSavedToken();
        apiInterface.getProfile(" Bearer "+authToken).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                System.out.println(response);

                User profile=response.body();
                TextView email=findViewById(R.id.email);
                assert profile != null;
                email.setText(profile.getEmail());
                System.out.println(profile);
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toast.makeText(Profile.this, t.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(t.toString());
            }
        });
    }
    private String getSavedToken() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getString("token", "");
    }
}