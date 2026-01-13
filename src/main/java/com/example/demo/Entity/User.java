package com.example.demo.Entity;


import jakarta.persistence.*;
import org.apache.catalina.Role;

@Entity
@Table(
        name = "users"
)
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            unique = true
    )
    private String username;
    @Column(
            unique = true
    )
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String toString() {
        return "User{id=" + this.id + ", username='" + this.username + "', email='" + this.email + "'}";
    }
    public enum Role {
        ADMIN,
        STAFF,
        CUSTOMER
    }

}
