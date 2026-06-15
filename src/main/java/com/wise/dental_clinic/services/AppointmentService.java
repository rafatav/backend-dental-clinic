package com.wise.dental_clinic.services;

import com.wise.dental_clinic.dto.AppointmentDTO;
import com.wise.dental_clinic.entities.Appointment;
import com.wise.dental_clinic.entities.Dentist;
import com.wise.dental_clinic.entities.Patient;
import com.wise.dental_clinic.entities.User;
import com.wise.dental_clinic.repositories.AppointmentRepository;
import com.wise.dental_clinic.repositories.DentistRepository;
import com.wise.dental_clinic.repositories.PatientRepository;
import com.wise.dental_clinic.repositories.UserRepository;
import com.wise.dental_clinic.services.exceptions.DatabaseException;
import com.wise.dental_clinic.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final DentistRepository dentistRepository;

    public AppointmentService(AppointmentRepository repository, PatientService patientService, PatientRepository patientRepository, UserService userService,
                              DentistService dentistService, UserRepository userRepository, DentistRepository dentistRepository) {
        this.repository = repository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.dentistRepository = dentistRepository;
    }

    @Transactional(readOnly = true)
    public Page<AppointmentDTO> findAll(String name, Pageable pageable) {
        Page<Appointment> result;
        if (name == null || name.isBlank()) {
            result = repository.findAll(pageable);
        } else {
            result = repository.findByPatient_NameContainingIgnoreCase(name, pageable);
        }
        return result.map(AppointmentDTO::new);
    }

    @Transactional(readOnly = true)
    public AppointmentDTO findById(Long id) {
        Optional<Appointment> result = repository.findById(id);
        Appointment appointment = result.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
        return new AppointmentDTO(appointment);
    }

    @Transactional
    public AppointmentDTO insert(AppointmentDTO dto) {
        boolean hasConflict = repository.existsConflictingAppointment(
                dto.getDentist().getId(),
                dto.getStartTime(),
                dto.getEndTime()
        );
        if (hasConflict) {
            throw new IllegalArgumentException("O dentista já possui uma consulta marcada neste horário.");
        }
        Appointment entity = new Appointment();
        dtoToEntity(entity, dto);
        return new AppointmentDTO(repository.save(entity));
    }

    @Transactional
    public AppointmentDTO update(AppointmentDTO dto, Long id) {
        try {
            Optional<Appointment> result = repository.findById(id);
            Appointment entity = result.orElseThrow();
            dtoToEntity(entity, dto);
            return new AppointmentDTO(entity);
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void dtoToEntity(Appointment entity, AppointmentDTO dto) {
        Patient patient = patientRepository.getReferenceById(dto.getPatient().getId());
        Dentist dentist = dentistRepository.getReferenceById(dto.getDentist().getId());
        User user = userRepository.getReferenceById(dto.getUser().getId());
        entity.setPatient(patient);
        entity.setDentist(dentist);
        entity.setUser(user);
        entity.setDescription(dto.getDescription());
        entity.setCancellationReason(dto.getCancellationReason());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setBookedAt(dto.getBookedAt());
        entity.setStatus(dto.getStatus());
    }
}
