package com.wise.dental_clinic.dto;

import jakarta.validation.constraints.NotBlank;

public class CancellationDTO {

    @NotBlank(message = "O motivo do cancelamento é obrigatório")
    private String reason;

    public CancellationDTO() {}

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
