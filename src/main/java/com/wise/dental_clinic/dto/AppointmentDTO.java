package com.wise.dental_clinic.dto;

import com.wise.dental_clinic.entities.Appointment;
import com.wise.dental_clinic.entities.AppointmentStatus;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.time.LocalDateTime;

public class AppointmentDTO {

    private Long id;
    private PatientDTO patient;
    private DentistDTO dentist;
    private UserDTO user;

    @NotBlank(message = "Campo obrigatório")
    private String description;


    private String cancellationReason;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Instant bookedAt;
    private AppointmentStatus status;

    public AppointmentDTO() {
    }

    public AppointmentDTO(Long id, PatientDTO patient, DentistDTO dentist, UserDTO user, String description, String cancellationReason,
                       LocalDateTime startTime, LocalDateTime endTime, Instant bookedAt, AppointmentStatus status) {
        this.id = id;
        this.patient = patient;
        this.dentist = dentist;
        this.user = user;
        this.description = description;
        this.cancellationReason = cancellationReason;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookedAt = bookedAt;
        this.status = status;
    }

    public AppointmentDTO(Appointment entity) {
        this.id = entity.getId();
        this.patient = new PatientDTO(entity.getPatient());
        this.dentist = new DentistDTO(entity.getDentist());
        this.user = new UserDTO(entity.getUser());
        this.description = entity.getDescription();
        this.cancellationReason = entity.getCancellationReason();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
        this.bookedAt = entity.getBookedAt();
        this.status = entity.getStatus();
    }

    public Long getId() {
        return id;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public DentistDTO getDentist() {
        return dentist;
    }

    public UserDTO getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Instant getBookedAt() {
        return bookedAt;
    }

    public AppointmentStatus getStatus() {
        return status;
    }
}
