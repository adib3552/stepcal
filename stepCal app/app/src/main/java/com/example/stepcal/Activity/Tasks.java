package com.example.stepcal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stepcal.API.ApiInterface;
import com.example.stepcal.DTO.Task;
import com.example.stepcal.R;
import com.example.stepcal.Retrofit.RetrofitClient;

import java.lang.annotation.Target;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tasks extends AppCompatActivity {

    private ApiInterface apiInterface;

    private String userEmail;
    private String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        apiInterface= RetrofitClient.getRetrofit().create(ApiInterface.class);
        authToken=getSavedToken();
        apiInterface.getTask(" Bearer "+authToken).enqueue(new Callback<Task>() {
            @Override
            public void onResponse(@NonNull Call<Task> call, @NonNull Response<Task> response) {
                Task task=response.body();
                assert task != null;
                System.out.println(task);
                TextView target_calorie=findViewById(R.id.target);
                String ShowCalorie=target_calorie.getText().toString()+task.getTarget_Calorie();
                target_calorie.setText(ShowCalorie);
            }

            @Override
            public void onFailure(@NonNull Call<Task> call, @NonNull Throwable t) {

            }
        });
    }

    private String getSavedToken() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getString("token", "");
    }
}