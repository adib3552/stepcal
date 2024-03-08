package com.example.stepcal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.stepcal.API.ApiInterface;
import com.example.stepcal.DTO.Task;
import com.example.stepcal.R;
import com.example.stepcal.Retrofit.RetrofitClient;

import java.util.Map;

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

        apiInterface.getTask("Bearer "+authToken).enqueue(new Callback<Task>() {
            @Override
            public void onResponse(@NonNull Call<Task> call, @NonNull Response<Task> response) {
                if(response.isSuccessful()) {
                    Task task = response.body();
                    if (task != null) {
                        // Setting Target Calorie
                        TextView targetCalorieTextView = findViewById(R.id.target);
                        String showCalorie = "Target Calorie: " + task.getTarget_Calorie();
                        targetCalorieTextView.setText(showCalorie);

                        // Adding Exercises
                        LinearLayout exerciseLayout = findViewById(R.id.exerciseLayout);
                        Map<String, Double> exercises = task.getExercises();
                        if (exercises != null) {
                            for (Map.Entry<String, Double> entry : exercises.entrySet()) {
                                String exerciseName = entry.getKey();
                                Double calorieBurned = entry.getValue();

                                TextView exerciseTextView = new TextView(Tasks.this);
                                String exerciseText = exerciseName + ": " + calorieBurned + " calories burned";
                                exerciseTextView.setText(exerciseText);
                                exerciseLayout.addView(exerciseTextView);
                            }
                        }
                    }
                }
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
