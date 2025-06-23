package org.example.msvcadmin.repositories;

import org.example.msvcadmin.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByUserId(String userId);
    boolean existsByUserId(String userId);
}
