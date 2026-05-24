package com.wise.dental_clinic.dto;

import com.wise.dental_clinic.entities.Dentist;

import java.time.Instant;

public class DentistDTO {

    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String cro;
    private Instant createdAt;
    private Boolean isActive;

    public DentistDTO() {
    }

    public DentistDTO(Long id, String name, String cpf, String email, String cro, Instant createdAt, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.cro = cro;
        this.createdAt = createdAt;
        this.isActive = isActive;
    }

    public DentistDTO(Dentist entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.cpf = entity.getCpf();
        this.email = entity.getEmail();
        this.cro = entity.getCro();
        this.createdAt = entity.getCreatedAt();
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

    public String getCro() {
        return cro;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Boolean getActive() {
        return isActive;
    }
}
