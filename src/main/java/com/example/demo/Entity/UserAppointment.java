//package com.example.demo.Entity;
//
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//
//@Entity
//public class UserAppointment {
//    @Id
//    @GeneratedValue(
//            strategy = GenerationType.IDENTITY
//    )
//    private Long id;
//    @ManyToOne
//    @JoinColumn(
//            name = "user_id"
//    )
//    private User user;
//    @ManyToOne
//    @JoinColumn(
//            name = "section_id"
//    )
//    private ServiceEntity service;
//    @ManyToOne
//    @JoinColumn(
//            name = "appointment_id"
//    )
//    private Appointment appointment;
//    private String status = "pending";
//
//    public UserAppointment() {
//    }
//
//    public Long getId() {
//        return this.id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return this.user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public ServiceEntity getSection() {
//        return this.service;
//    }
//
//    public void setSection(ServiceEntity service) {
//        this.service = service;
//    }
//
//    public Appointment getAppointment() {
//        return this.appointment;
//    }
//
//    public void setAppointment(Appointment appointment) {
//        this.appointment = appointment;
//    }
//
//    public String getStatus() {
//        return this.status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//}
