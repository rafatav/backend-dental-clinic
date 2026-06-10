package com.wise.dental_clinic.repositories;

import com.wise.dental_clinic.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Appointment a " +
            "WHERE a.dentist.id = :dentistId " +
            "AND a.status = 'SCHEDULED' " +
            "AND a.startTime < :endTime AND a.endTime > :startTime")
    boolean existsConflictingAppointment(@Param("dentistId") Long dentistId,
                                         @Param("startTime") LocalDateTime startTime,
                                         @Param("endTime") LocalDateTime endTime);
}
