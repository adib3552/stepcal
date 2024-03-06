package com.UniProject.Repository;

import com.UniProject.Entities.CompletedTask;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
public interface CompletedTaskRepository extends CrudRepository<CompletedTask,Long> {
    CompletedTask findByEmailAndDate(String email, LocalDate date);
    @Modifying
    @Query("Update CompletedTask task set task.calorie_burn=:burn")
    void updateCalorie_burn(@Param("burn") double burn);
    @Modifying
    @Query("Update CompletedTask task set task.calorie_intake=:intake")
    void updateCalorie_intake(@Param("intake") double intake);
    @Query("SELECT task FROM CompletedTask task WHERE task.email = :email")
    List<CompletedTask> findAllByEmail(@Param("email") String email);
}
