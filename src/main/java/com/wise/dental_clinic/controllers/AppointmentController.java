package com.wise.dental_clinic.controllers;

import com.wise.dental_clinic.dto.AppointmentDTO;
import com.wise.dental_clinic.dto.CancellationDTO;
import com.wise.dental_clinic.services.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DENTIST')")
    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<AppointmentDTO>> findAll(@RequestParam(value = "name", required = false) String name, Pageable pageable) {
        Page<AppointmentDTO> dto = service.findAll(name, pageable);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DENTIST')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<AppointmentDTO> findById(@PathVariable Long id) {
        AppointmentDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DENTIST')")
    @PostMapping
    public ResponseEntity<AppointmentDTO> insert(@Valid @RequestBody AppointmentDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DENTIST')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<AppointmentDTO> update(@Valid @RequestBody AppointmentDTO dto, @PathVariable Long id) {
        dto = service.update(dto, id);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id, @Valid @RequestBody CancellationDTO dto) {
        service.cancelAppointment(id, dto.getReason());
        return ResponseEntity.noContent().build();
    }
}
