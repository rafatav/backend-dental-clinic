package com.wise.dental_clinic.repositories;

import com.wise.dental_clinic.entities.Dentist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DentistRepository extends JpaRepository<Dentist, Long> {

    Page<Dentist> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
