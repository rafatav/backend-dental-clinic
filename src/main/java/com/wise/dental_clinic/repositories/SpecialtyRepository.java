package com.wise.dental_clinic.repositories;

import com.wise.dental_clinic.entities.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

    Page<Specialty> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
