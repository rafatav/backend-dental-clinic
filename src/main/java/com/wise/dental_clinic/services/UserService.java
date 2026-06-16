package com.wise.dental_clinic.services;

import com.wise.dental_clinic.dto.RoleDTO;
import com.wise.dental_clinic.dto.UserDTO;
import com.wise.dental_clinic.entities.Role;
import com.wise.dental_clinic.entities.User;
import com.wise.dental_clinic.projections.UserDetailsProjection;
import com.wise.dental_clinic.repositories.UserRepository;
import com.wise.dental_clinic.services.exceptions.DatabaseException;
import com.wise.dental_clinic.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(String name, Pageable pageable) {
        Page<User> result;
        if (name == null || name.isBlank()) {
            result = repository.findAll(pageable);
        } else {
            result = repository.findByNameContainingIgnoreCase(name, pageable);
        }
        return result.map(UserDTO::new);
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
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setLastLogin(dto.getLastLogin());
        entity.setActive(dto.getActive());
        for (RoleDTO role : dto.getRoles()) {
            entity.addRoles(new Role(role.getId(), role.getAuthority()));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        User user = new User();
        user.setEmail(result.getFirst().getUsername());
        user.setPassword(result.getFirst().getPassword());

        for (UserDetailsProjection projection : result) {
            user.addRoles(new Role(projection.getRoleId(), projection.getAuthority()));
        }

        return user;
    }
}
