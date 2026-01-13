package com.example.demo;


import com.example.demo.Entity.User;
import com.example.demo.Entity.WorkingSchedule;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.WorkingScheduleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.DayOfWeek;
import java.time.LocalTime;

@SpringBootApplication
public class AppointmentSystemApplication {
	public AppointmentSystemApplication() {
	}

	public static void main(String[] args) {
		SpringApplication.run(AppointmentSystemApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepository)
	{
		return (args) -> {
			User user = new User();
			user.setUsername("yamen");
			user.setEmail("yamen@gmail.com");
			user.setPassword("123456");
			userRepository.save(user);
			userRepository.findAll().forEach((u) -> {
				System.out.println(u.getUsername());
			});
		};
	}
	@Bean
	CommandLineRunner initSchedule(WorkingScheduleRepository repo) {
		return args -> {

			for (DayOfWeek day : DayOfWeek.values()) {
				WorkingSchedule ws = new WorkingSchedule();
				ws.setDayOfWeek(day);

				if (day == DayOfWeek.FRIDAY) {
					ws.setHoliday(true);
				} else {
					ws.setHoliday(false);
					ws.setStartTime(LocalTime.of(9, 0));
					ws.setEndTime(LocalTime.of(17, 0));
				}

				repo.save(ws);
			}
		};
	}

}
