package com.example.demo.Controller;

import com.example.demo.Entity.ServiceEntity;
import com.example.demo.Service.ServiceManagementService;
import com.example.demo.dto.ServiceRequest;
import com.example.demo.dto.ServiceUpdateRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceManagementService serviceService;

    public ServiceController(ServiceManagementService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping
    public ServiceEntity create(@RequestBody ServiceRequest request) {
        return serviceService.createService(
                request.getName(),
                request.getDuration(),
                request.getPrice(),
                request.getProviderId()
        );
    }

    @PutMapping("/{id}")
    public ServiceEntity update(@PathVariable Long id,
                                @RequestBody ServiceUpdateRequest request) {
        return serviceService.updateService(
                id,
                request.getDuration(),
                request.getPrice()
        );
    }
}
