package com.example.demo.Controller;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthService authService; // use AuthService for token

    public AuthController(UserRepository userRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          AuthService authService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    // ---------------- SHOW FORMS ----------------
    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/database")
    public String goToDatabase() {
        return "redirect:/h2-console";
    }

    // ---------------- SIGNUP ----------------
    @PostMapping("/signup")
    public String processSignup(@RequestParam String username,
                                @RequestParam String email,
                                @RequestParam String password,
                                RedirectAttributes redirectAttributes) {

        if (userRepository.existsByUsername(username)) {
            redirectAttributes.addFlashAttribute("error", "Username already exists");
            return "redirect:/signup";
        }

        if (userRepository.existsByEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "Email already exists");
            return "redirect:/signup";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("success", "Signup successful. Please login.");
        return "redirect:/login";
    }

    // ---------------- LOGIN ----------------
    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {

        User user = userRepository.findByUsername(username).orElse(null);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Generate JWT token using AuthService
            String token = authService.generateToken(user.getUsername());
            model.addAttribute("token", token);
            model.addAttribute("username", user.getUsername());
            return "dashboard"; // return your view
        }

        model.addAttribute("error", "Invalid username or password");
        return "login";
    }
}
