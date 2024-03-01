package com.example.stepcal.DTO;

import java.util.List;
import java.util.Map;

public class Task {
    private double Target_Calorie;
    private List<Map<String,Double>> Exercises;

    public Task(double target_Calorie, List<Map<String, Double>> exercises) {
        Target_Calorie = target_Calorie;
        Exercises = exercises;
    }

    public Task(){

    }

    public double getTarget_Calorie() {
        return Target_Calorie;
    }

    public void setTarget_Calorie(double target_Calorie) {
        Target_Calorie = target_Calorie;
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
                "Target_Calorie=" + Target_Calorie +
                ", Exercises=" + Exercises +
                '}';
    }
}
