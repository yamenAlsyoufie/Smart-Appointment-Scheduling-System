package com.example.demo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF because we are using JWT and WebSocket
                .csrf(csrf -> csrf.disable())

                // Stateless session (we use JWT)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints: API + H2 console + WebSocket handshake
                        .requestMatchers(
                                "/api/**",
                                "/h2-console/**",
                                "/ws/**"  ,
                                "/topic/**"// Allow WebSocket handshake
                        ).permitAll()

                        // Everything else requires JWT
                        .anyRequest().authenticated()
                )

                // Add JWT filter BEFORE UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                // Allow H2 console
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}
