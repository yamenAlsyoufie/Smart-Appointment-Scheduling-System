package com.example.demo.Controller;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthService jwtUtil;

    public AuthRestController(UserRepository userRepository,
                              BCryptPasswordEncoder passwordEncoder,
                              AuthService jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ---------------- SIGNUP ----------------
    @PostMapping("/signup")
    public Map<String, String> signup(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String email = body.get("email");
        String password = body.get("password");

        Map<String, String> response = new HashMap<>();

        if (userRepository.existsByUsername(username)) {
            response.put("error", "Username exists");
            return response;
        }

        if (userRepository.existsByEmail(email)) {
            response.put("error", "Email exists");
            return response;
        }

        User user = new User(username, email, passwordEncoder.encode(password));
        userRepository.save(user);

        response.put("message", "User registered successfully");
        return response;
    }

    // ---------------- LOGIN ----------------
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        User user = userRepository.findByUsername(username).orElse(null);
        Map<String, String> response = new HashMap<>();

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());
            response.put("token", token);
            response.put("username", user.getUsername());
            return response;
        }

        response.put("error", "Invalid credentials");
        return response;
    }
}
