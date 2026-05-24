package com.wise.dental_clinic.repositories;

import com.wise.dental_clinic.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
