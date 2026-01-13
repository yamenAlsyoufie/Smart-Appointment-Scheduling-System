package com.example.demo.Service;

import com.example.demo.Entity.WorkingSchedule;
import com.example.demo.Repository.WorkingScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Service
public class WorkingScheduleService {

    private final WorkingScheduleRepository repository;

    public WorkingScheduleService(WorkingScheduleRepository repository) {
        this.repository = repository;
    }

    public WorkingSchedule saveOrUpdate( DayOfWeek day,
                                        LocalTime start,
                                        LocalTime end,
                                        boolean holiday) {

        WorkingSchedule schedule = repository
                .findByDayOfWeek(day)
                .orElse(new WorkingSchedule());

        schedule.setDayOfWeek(day);
        schedule.setStartTime(start);
        schedule.setEndTime(end);
        schedule.setHoliday(holiday);

        return repository.save(schedule);
    }
}
