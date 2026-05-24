package com.wise.dental_clinic.repositories;

import com.wise.dental_clinic.entities.DentistSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DentistSpecialtyRepository extends JpaRepository<DentistSpecialty, Long> {
}
