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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;
    private final PatientService patientService;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final DentistRepository dentistRepository;
    private final UserService userService;
    private final DentistService dentistService;

    public AppointmentService(AppointmentRepository repository, PatientService patientService, PatientRepository patientRepository, UserService userService,
                              DentistService dentistService, UserRepository userRepository, DentistRepository dentistRepository) {
        this.repository = repository;
        this.patientService = patientService;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.dentistRepository = dentistRepository;
        this.userService = userService;
        this.dentistService = dentistService;
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> findAll() {
        List<Appointment> result = repository.findAll();
        return result.stream().map(AppointmentDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public AppointmentDTO findById(Long id) {
        Optional<Appointment> result = repository.findById(id);
        Appointment appointment = result.orElseThrow();
        return new AppointmentDTO(appointment);
    }

    @Transactional
    public AppointmentDTO insert(AppointmentDTO dto) {
        Appointment entity = new Appointment();
        dtoToEntity(entity, dto);
        return new AppointmentDTO(repository.save(entity));
    }

    @Transactional
    public AppointmentDTO update(AppointmentDTO dto, Long id) {
        Optional<Appointment> result = repository.findById(id);
        Appointment entity = result.orElseThrow();
        dtoToEntity(entity, dto);
        return new AppointmentDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void dtoToEntity(Appointment entity, AppointmentDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatient().getId()).get();
        Dentist dentist = dentistRepository.findById(dto.getDentist().getId()).get();
        User user = userRepository.findById(dto.getUser().getId()).get();
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
