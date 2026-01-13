package com.example.demo.Repository;


import com.example.demo.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
        SELECT COUNT(a) > 0
        FROM Appointment a
        WHERE a.serviceEntity.provider.id = :providerId
          AND a.date = :date
          AND a.status IN (:activeStatuses)
          AND (:start < a.endTime AND :end > a.startTime)
    """)
    boolean existsOverlappingAppointment(
            @Param("providerId") Long providerId,
            @Param("date") LocalDate date,
            @Param("start") LocalTime start,
            @Param("end") LocalTime end,
            @Param("activeStatuses") List<Appointment.AppointmentStatus> activeStatuses
    );
    List<Appointment> findByDate(LocalDate date);
//    // لل test
//@Query("""
//SELECT COUNT(a) > 0 FROM Appointment a
//WHERE a.date = :date
//AND a.startTime < :endTime
//AND a.endTime > :startTime
//AND a.status IN ('PENDING','APPROVED')
//AND a.serviceEntity.provider.id = :providerId
//""")
//boolean existsConflict(
//        LocalDate date,
//        LocalTime startTime,
//        LocalTime endTime,
//        Long providerId
//);

}
