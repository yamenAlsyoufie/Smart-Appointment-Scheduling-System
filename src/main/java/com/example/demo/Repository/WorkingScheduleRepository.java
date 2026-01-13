package com.example.demo.Repository;

import com.example.demo.Entity.WorkingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.Optional;

public interface WorkingScheduleRepository
        extends JpaRepository<WorkingSchedule, Long> {

    Optional<WorkingSchedule> findByDayOfWeek(DayOfWeek dayOfWeek);

}

