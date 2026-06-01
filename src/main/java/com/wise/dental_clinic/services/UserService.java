package com.wise.dental_clinic.services;

import com.wise.dental_clinic.dto.UserDTO;
import com.wise.dental_clinic.entities.User;
import com.wise.dental_clinic.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        User user = result.orElseThrow();
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO insert(UserDTO dto) {
        User entity = new User();
        dtoToEntity(entity, dto);
        return new UserDTO(repository.save(entity));
    }

    @Transactional
    public UserDTO update(UserDTO dto, Long id) {
        Optional<User> result = repository.findById(id);
        User entity = result.orElseThrow();
        dtoToEntity(entity, dto);
        return new UserDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
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
