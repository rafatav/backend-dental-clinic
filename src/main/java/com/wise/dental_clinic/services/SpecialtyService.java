package com.wise.dental_clinic.services;

import com.wise.dental_clinic.dto.SpecialtyDTO;
import com.wise.dental_clinic.entities.Specialty;
import com.wise.dental_clinic.repositories.SpecialtyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        Specialty patient = result.orElseThrow();
        return new SpecialtyDTO(patient);
    }

    @Transactional
    public SpecialtyDTO insert(SpecialtyDTO dto) {
        Specialty entity = new Specialty();
        entity.setName(dto.getName());
        return new SpecialtyDTO(entity);
    }

    @Transactional
    public SpecialtyDTO update(SpecialtyDTO dto, Long id) {
        Optional<Specialty> result = repository.findById(id);
        Specialty entity = result.orElseThrow();
        entity.setName(dto.getName());
        return new SpecialtyDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
