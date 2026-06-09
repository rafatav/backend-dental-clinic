package com.wise.dental_clinic.services;

import com.wise.dental_clinic.dto.PatientDTO;
import com.wise.dental_clinic.entities.Patient;
import com.wise.dental_clinic.repositories.PatientRepository;
import com.wise.dental_clinic.services.exceptions.DatabaseException;
import com.wise.dental_clinic.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
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
        Patient patient = result.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
        return new PatientDTO(patient);
    }

    @Transactional
    public PatientDTO insert(PatientDTO dto) {
        try {
            Patient entity = new Patient();
            entityToDto(entity, dto);
            return new PatientDTO(entity);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha nos dados");
        }
    }

    @Transactional
    public PatientDTO update(PatientDTO dto, Long id) {
        try {
            Optional<Patient> result = repository.findById(id);
            Patient entity = result.orElseThrow();
            entityToDto(entity, dto);
            return new PatientDTO(entity);
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha nos dados");
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

    private void entityToDto(Patient entity, PatientDTO dto) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setCpf(dto.getCpf());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setPhoneNumber(dto.getPhoneNumber());
        repository.save(entity);
    }
}
