package com.wise.dental_clinic.repositories;

import com.wise.dental_clinic.entities.User;
import com.wise.dental_clinic.projections.UserDetailsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = """
			SELECT users.email AS username, users.password, roles.id AS roleId, roles.authority
			FROM users
			INNER JOIN user_roles ON users.id = user_roles.user_id
			INNER JOIN roles ON roles.id = user_roles.role_id
			WHERE users.email = :email
		""")
    List<UserDetailsProjection> searchUserAndRolesByEmail(String email);

    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
