package com.example.stepcal.DTO;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;

public class Task {
    private double target_Calorie;
    private List<Map<String,Double>> Exercises;

    public Task(double target_Calorie, List<Map<String, Double>> exercises) {
        this.target_Calorie = target_Calorie;
        Exercises = exercises;
    }
    public Task(){

    }

    public double getTarget_Calorie() {
        return target_Calorie;
    }

    public void setTarget_Calorie(double target_Calorie) {
        this.target_Calorie = target_Calorie;
    }

    public List<Map<String, Double>> getExercises() {
        return Exercises;
    }

    public void setExercises(List<Map<String, Double>> exercises) {
        Exercises = exercises;
    }

    @Override
    public String toString() {
        return "Task{" +
                "target_Calorie=" + target_Calorie +
                ", Exercises=" + Exercises +
                '}';
    }
}
