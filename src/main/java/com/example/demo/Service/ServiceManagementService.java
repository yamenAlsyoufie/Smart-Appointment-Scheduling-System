package com.example.demo.Service;

import com.example.demo.Entity.ServiceEntity;
import com.example.demo.Entity.User;
import com.example.demo.Repository.ServiceRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceManagementService {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    public ServiceManagementService(ServiceRepository serviceRepository,
                                    UserRepository userRepository) {
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
    }

    // إنشاء خدمة
    public ServiceEntity createService(String name, int duration, double price, Long providerId) {

        User provider = userRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("المقدم غير موجود"));

        ServiceEntity service = new ServiceEntity();
        service.setName(name);
        service.setDuration(duration);
        service.setPrice(price);
        service.setProvider(provider);

        return serviceRepository.save(service);
    }

    // تعديل خدمة
    public ServiceEntity updateService(Long serviceId, int duration, double price) {

        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("الخدمة غير موجودة"));

        service.setDuration(duration);
        service.setPrice(price);

        return serviceRepository.save(service);
    }
}
