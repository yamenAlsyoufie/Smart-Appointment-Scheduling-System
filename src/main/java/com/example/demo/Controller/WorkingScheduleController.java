package com.example.demo.Controller;

import com.example.demo.Entity.WorkingSchedule;
import com.example.demo.Service.WorkingScheduleService;
import com.example.demo.dto.ScheduleRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule")
public class WorkingScheduleController {

    private final WorkingScheduleService service;

    public WorkingScheduleController(WorkingScheduleService service) {
        this.service = service;
    }

    @PostMapping
    public WorkingSchedule setSchedule(@RequestBody ScheduleRequest request) {
        return service.saveOrUpdate(
                request.getDayOfWeek(),
                request.getStartTime(),
                request.getEndTime(),
                request.isHoliday()
        );
    }
}

