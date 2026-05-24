package com.wise.dental_clinic.repositories;

import com.wise.dental_clinic.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
