package com.wise.dental_clinic.dto;

import com.wise.dental_clinic.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public class UserDTO {

    private Long id;

    @NotBlank(message = "Campo requerido")
    private String name;

    @NotBlank(message = "Campo requerido")
    private String cpf;

    @NotBlank(message = "Campo requerido")
    @Email(message = "Forneça um e-mail válido")
    private String email;

    @NotBlank(message = "Campo requerido")
    private String password;

    private Instant createdAt;
    private Instant lastLogin;
    private Boolean active;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String cpf, String email, String password, Instant createdAt, Instant lastLogin, Boolean active) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
        this.active = active;
    }

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.cpf = entity.getCpf();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.createdAt = entity.getCreatedAt();
        this.lastLogin = entity.getLastLogin();
        this.active = entity.getActive();
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
        return active;
    }
}
