package com.wise.dental_clinic.controllers;

import com.wise.dental_clinic.dto.DashboardDTO;
import com.wise.dental_clinic.repositories.AppointmentRepository;
import com.wise.dental_clinic.repositories.DentistRepository;
import com.wise.dental_clinic.repositories.PatientRepository;
import com.wise.dental_clinic.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final PatientRepository patientRepository;
    private final DentistRepository dentistRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public DashboardController(PatientRepository patientRepository, DentistRepository dentistRepository, AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.dentistRepository = dentistRepository;
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/metrics")
    public ResponseEntity<DashboardDTO> getMetrics() {
        long patients = patientRepository.count();
        long dentists = dentistRepository.count();
        long appointments = appointmentRepository.count();
        long users = userRepository.count();

        DashboardDTO metrics = new DashboardDTO(patients, dentists, appointments, users);
        return ResponseEntity.ok(metrics);
    }
}
