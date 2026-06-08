package com.wise.dental_clinic.services;

import com.wise.dental_clinic.dto.SpecialtyDTO;
import com.wise.dental_clinic.entities.Specialty;
import com.wise.dental_clinic.repositories.SpecialtyRepository;
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
public class SpecialtyService {

    private final SpecialtyRepository repository;

    public SpecialtyService(SpecialtyRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<SpecialtyDTO> findAll() {
        List<Specialty> result = repository.findAll();
        return result.stream().map(SpecialtyDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public SpecialtyDTO findById(Long id) {
        Optional<Specialty> result = repository.findById(id);
        Specialty patient = result.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
        return new SpecialtyDTO(patient);
    }

    @Transactional
    public SpecialtyDTO insert(SpecialtyDTO dto) {
        Specialty entity = new Specialty();
        dtoToEntity(entity, dto);
        return new SpecialtyDTO(entity);
    }

    @Transactional
    public SpecialtyDTO update(SpecialtyDTO dto, Long id) {
        try {
            Optional<Specialty> result = repository.findById(id);
            Specialty entity = result.orElseThrow();
            dtoToEntity(entity, dto);
            return new SpecialtyDTO(entity);
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

    private void dtoToEntity(Specialty entity, SpecialtyDTO dto) {
        entity.setName(dto.getName());
        repository.save(entity);
    }
}
