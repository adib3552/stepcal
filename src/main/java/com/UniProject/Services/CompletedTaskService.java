package com.UniProject.Services;

import com.UniProject.Entities.CompletedTask;
import com.UniProject.Repository.CompletedTaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CompletedTaskService {

    @Autowired
    private CompletedTaskRepository completedTaskRepository;

    @Transactional
    public void saveTask(CompletedTask task){
        CompletedTask completedTask=completedTaskRepository.findByEmailAndDate(task.getEmail(),task.getDate());
        if(completedTask==null){
            System.out.println(task);
            completedTaskRepository.save(task);
        }
        else{
            double burn=completedTask.getCalorie_burn()+task.getCalorie_burn();
            double intake=completedTask.getCalorie_intake()+task.getCalorie_intake();
            completedTaskRepository.updateCalorie_burn(burn);
            completedTaskRepository.updateCalorie_intake(intake);
        }
    }
    public CompletedTask getTask(String email, LocalDate date){
        return completedTaskRepository.findByEmailAndDate(email,date);
    }
    public List<CompletedTask>taskHistory(String email){
        return completedTaskRepository.findAllByEmail(email);
    }
}
