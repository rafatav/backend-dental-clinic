package com.wise.dental_clinic.services;

import com.wise.dental_clinic.dto.DentistDTO;
import com.wise.dental_clinic.entities.Dentist;
import com.wise.dental_clinic.repositories.DentistRepository;
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
public class DentistService {

    private final DentistRepository repository;

    public DentistService(DentistRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<DentistDTO> findAll() {
        List<Dentist> result = repository.findAll();
        return result.stream().map(DentistDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public DentistDTO findById(Long id) {
        Optional<Dentist> result = repository.findById(id);
        Dentist dentist = result.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
        return new DentistDTO(dentist);
    }

    @Transactional
    public DentistDTO insert(DentistDTO dto) {
        try {
            Dentist entity = new Dentist();
            entityToDto(entity, dto);
            return new DentistDTO(entity);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha nos dados");
        }
    }

    @Transactional
    public DentistDTO update(DentistDTO dto, Long id) {
        try {
            Optional<Dentist> result = repository.findById(id);
            Dentist entity = result.orElseThrow();
            entityToDto(entity, dto);
            return new DentistDTO(entity);
        } catch (NoSuchElementException e)  {
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

    private void entityToDto(Dentist entity, DentistDTO dto) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setEmail(dto.getEmail());
        entity.setCro(dto.getCro());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setActive(dto.getActive());
        repository.save(entity);
    }
}
