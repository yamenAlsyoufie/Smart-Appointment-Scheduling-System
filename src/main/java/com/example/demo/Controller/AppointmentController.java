//package com.example.demo.Controller;
//
//import com.example.demo.Entity.Appointment;
//import com.example.demo.Entity.User;
//import com.example.demo.Service.AppointmentService;
//import com.example.demo.Repository.UserRepository;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/appointments")
//public class AppointmentController {
//
//    private final AppointmentService appointmentService;
//    private final UserRepository userRepository;
//
//    public AppointmentController(AppointmentService appointmentService,
//                                 UserRepository userRepository) {
//        this.appointmentService = appointmentService;
//        this.userRepository = userRepository;
//    }
//
//    @PostMapping("/book")
//    public Appointment bookAppointment(
//            @RequestBody Map<String, String> body,
//            Authentication authentication
//    ) {
//        // المستخدم الحالي (من JWT)
//        String username = authentication.getName();
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Long serviceId = Long.valueOf(body.get("serviceId"));
//        LocalDate date = LocalDate.parse(body.get("date"));
//        LocalTime startTime = LocalTime.parse(body.get("startTime"));
//
//        return appointmentService.createAppointment(
//                user,
//                serviceId,
//                date,
//                startTime
//        );
//    }
//
//    @PutMapping("/{id}/status")
//    public Appointment updateStatus(@PathVariable Long id,
//                                    @RequestParam Appointment.AppointmentStatus status) {
//        return appointmentService.changeStatus(id, status);
//    }
//
//}
// يامن
package com.example.demo.Controller;

import com.example.demo.Entity.Appointment;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.AppointmentService;
import com.example.demo.Security.JwtSecured;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final UserRepository userRepository;

    public AppointmentController(AppointmentService appointmentService,
                                 UserRepository userRepository) {
        this.appointmentService = appointmentService;
        this.userRepository = userRepository;
    }

    @PostMapping("/book")
    @JwtSecured
    public Appointment bookAppointment(
            @RequestBody Map<String, String> body,
            Authentication authentication
    ) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Unauthorized");
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long serviceId = Long.valueOf(body.get("serviceId"));
        LocalDate date = LocalDate.parse(body.get("date"));
        LocalTime startTime = LocalTime.parse(body.get("startTime"));

        return appointmentService.createAppointment(
                user,
                serviceId,
                date,
                startTime
        );
    }

    @PutMapping("/{id}/status")
    @JwtSecured
    public Appointment updateStatus(
            @PathVariable Long id,
            @RequestParam Appointment.AppointmentStatus status
    ) {
        return appointmentService.changeStatus(id, status);
    }
    @GetMapping("/suggest")
    public String suggestAppointment(
            @RequestParam String date,
            @RequestParam Long serviceId
    ) {
        return appointmentService.suggestBestAppointment(
                LocalDate.parse(date),
                serviceId
        );
    }
}
