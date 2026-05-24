package com.wise.dental_clinic.dto;

import com.wise.dental_clinic.entities.Patient;

import java.time.Instant;

public class PatientDTO {

    private Long id;
    private String name;
    private String email;
    private String cpf;
    private Instant createdAt;
    private String phoneNumber;

    public PatientDTO() {
    }

    public PatientDTO(Long id, String name, String email, String cpf, Instant createdAt, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.createdAt = createdAt;
        this.phoneNumber = phoneNumber;
    }

    public PatientDTO(Patient entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.cpf = entity.getCpf();
        this.createdAt = entity.getCreatedAt();
        this.phoneNumber = entity.getPhoneNumber();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
