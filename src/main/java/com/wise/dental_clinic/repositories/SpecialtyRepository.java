package com.wise.dental_clinic.repositories;

import com.wise.dental_clinic.entities.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
}
