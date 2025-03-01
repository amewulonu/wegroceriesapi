package com.wegroceries.wegroceriesapi.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Find a role by its name.
     *
     * @param name The name of the role (e.g., ROLE_CUSTOMER).
     * @return An Optional containing the Role if found, otherwise empty.
     */
    Optional<Role> findByName(ERole name);
}