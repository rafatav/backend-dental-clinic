package com.wise.dental_clinic.repositories;

import com.wise.dental_clinic.entities.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DentistRepository extends JpaRepository<Dentist, Long> {
}
