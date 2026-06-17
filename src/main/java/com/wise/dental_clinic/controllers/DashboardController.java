package com.wise.dental_clinic.controllers;

import com.wise.dental_clinic.dto.DashboardDTO;
import com.wise.dental_clinic.entities.User;
import com.wise.dental_clinic.repositories.AppointmentRepository;
import com.wise.dental_clinic.repositories.DentistRepository;
import com.wise.dental_clinic.repositories.PatientRepository;
import com.wise.dental_clinic.repositories.UserRepository;
import com.wise.dental_clinic.services.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DENTIST')")
public class DashboardController {

    private final PatientRepository patientRepository;
    private final DentistRepository dentistRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public DashboardController(PatientRepository patientRepository, DentistRepository dentistRepository,
                               AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.dentistRepository = dentistRepository;
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/metrics")
    @Transactional(readOnly = true)
    public ResponseEntity<DashboardDTO> getMetrics() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String loggedUsername = jwt.getClaimAsString("username");

        User user = userRepository.findByEmail(loggedUsername).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        boolean isAdmin = user.getRoles().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        long patients = patientRepository.count();
        long dentists = dentistRepository.countByActiveTrue();
        long users = userRepository.count();
        long appointments;
        if (isAdmin) {
            appointments = appointmentRepository.count();
        } else {
            appointments = appointmentRepository.countByUser(user);
        }
        return ResponseEntity.ok(new DashboardDTO(patients, dentists, appointments, users));
    }
}
