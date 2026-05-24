package com.wise.dental_clinic.dto;

import com.wise.dental_clinic.entities.User;

import java.time.Instant;

public class UserDTO {

    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String password;
    private Instant createdAt;
    private Instant lastLogin;
    private Boolean isActive;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String cpf, String email, String password, Instant createdAt, Instant lastLogin, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
        this.isActive = isActive;
    }

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.cpf = entity.getCpf();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.createdAt = entity.getCreatedAt();
        this.lastLogin = entity.getLastLogin();
        this.isActive = entity.getActive();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getLastLogin() {
        return lastLogin;
    }

    public Boolean getActive() {
        return isActive;
    }
}
