package com.wise.dental_clinic.services;

import com.wise.dental_clinic.dto.UserDTO;
import com.wise.dental_clinic.entities.User;
import com.wise.dental_clinic.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

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
    public UserDTO save(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setCpf(dto.getCpf());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setCreatedAt(dto.getCreatedAt());
        user.setLastLogin(dto.getLastLogin());
        user.setActive(dto.getActive());
        return new UserDTO(repository.save(user));
    }

    @Transactional
    public UserDTO update(UserDTO dto) {
        Optional<User> result = repository.findById(dto.getId());
        User user = result.orElseThrow();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setCpf(dto.getCpf());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setCreatedAt(dto.getCreatedAt());
        user.setLastLogin(dto.getLastLogin());
        user.setActive(dto.getActive());
        return new UserDTO(user);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
 }
