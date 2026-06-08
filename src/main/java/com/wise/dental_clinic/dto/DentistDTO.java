package com.wise.dental_clinic.dto;

import com.wise.dental_clinic.entities.Dentist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public class DentistDTO {

    private Long id;

    @NotBlank(message = "Campo requerido")
    private String name;

    @NotBlank(message = "Campo requerido")
    private String cpf;

    @NotBlank(message = "Campo requerido")
    @Email(message = "Forneça um email válido")
    private String email;

    @NotBlank(message = "Campo requerido")
    private String cro;

    private Instant createdAt;
    private Boolean active;

    public DentistDTO() {
    }

    public DentistDTO(Long id, String name, String cpf, String email, String cro, Instant createdAt, Boolean active) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.cro = cro;
        this.createdAt = createdAt;
        this.active = active;
    }

    public DentistDTO(Dentist entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.cpf = entity.getCpf();
        this.email = entity.getEmail();
        this.cro = entity.getCro();
        this.createdAt = entity.getCreatedAt();
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

    public String getCro() {
        return cro;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Boolean getActive() {
        return active;
    }
}
