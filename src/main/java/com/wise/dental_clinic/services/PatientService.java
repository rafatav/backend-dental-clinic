package com.wise.dental_clinic.services;

import com.wise.dental_clinic.dto.PatientDTO;
import com.wise.dental_clinic.entities.Patient;
import com.wise.dental_clinic.repositories.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<PatientDTO> findAll() {
        List<Patient> result = repository.findAll();
        return result.stream().map(PatientDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public PatientDTO findById(Long id) {
        Optional<Patient> result = repository.findById(id);
        Patient patient = result.orElseThrow();
        return new PatientDTO(patient);
    }

    @Transactional
    public PatientDTO insert(PatientDTO dto) {
        Patient entity = new Patient();
        entityToDto(entity, dto);
        return new PatientDTO(entity);
    }

    @Transactional
    public PatientDTO update(PatientDTO dto, Long id) {
        Optional<Patient> result = repository.findById(id);
        Patient entity = result.orElseThrow();
        entityToDto(entity, dto);
        return new PatientDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void entityToDto(Patient entity, PatientDTO dto) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setCpf(dto.getCpf());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setPhoneNumber(dto.getPhoneNumber());
        repository.save(entity);
    }
}
