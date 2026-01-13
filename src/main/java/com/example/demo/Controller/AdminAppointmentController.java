package com.example.demo.Controller;

import com.example.demo.Entity.Appointment;
import com.example.demo.Service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/appointments")
public class AdminAppointmentController {

    private final AppointmentService appointmentService;

    public AdminAppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // ✅ APPROVE appointment
    @PutMapping("/{id}/approve")
    public ResponseEntity<Appointment> approve(@PathVariable Long id) {
        return ResponseEntity.ok(
                appointmentService.changeStatus(
                        id,
                        Appointment.AppointmentStatus.APPROVED
                )
        );
    }

    // ❌ CANCEL (REFUSE) appointment
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Appointment> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(
                appointmentService.changeStatus(
                        id,
                        Appointment.AppointmentStatus.CANCELLED
                )
        );
    }
}
