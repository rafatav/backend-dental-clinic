package com.wise.dental_clinic.dto;

import com.wise.dental_clinic.entities.Specialty;
import jakarta.validation.constraints.NotBlank;

public class SpecialtyDTO {

    private Long id;

    @NotBlank(message = "Campo requerido")
    private String name;

    public SpecialtyDTO() {
    }

    public SpecialtyDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SpecialtyDTO(Specialty entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
