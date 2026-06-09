package com.wise.dental_clinic.services;

import com.wise.dental_clinic.dto.UserDTO;
import com.wise.dental_clinic.entities.User;
import com.wise.dental_clinic.repositories.UserRepository;
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
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> result = repository.findAll();
        return result.stream().map(UserDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> result = repository.findById(id);
        User user = result.orElseThrow(() ->new ResourceNotFoundException("Recurso não encontrado"));
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO insert(UserDTO dto) {
        try {
            User entity = new User();
            dtoToEntity(entity, dto);
            return new UserDTO(repository.save(entity));
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha nos dados");
        }
    }

    @Transactional
    public UserDTO update(UserDTO dto, Long id) {
        try {
            Optional<User> result = repository.findById(id);
            User entity = result.orElseThrow();
            dtoToEntity(entity, dto);
            return new UserDTO(entity);
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

    private void dtoToEntity(User entity, UserDTO dto) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setLastLogin(dto.getLastLogin());
        entity.setActive(dto.getActive());
    }
}
