package com.example.demo.Repository;


import com.example.demo.Entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ServiceRepository
        extends JpaRepository<ServiceEntity, Long> {
}
