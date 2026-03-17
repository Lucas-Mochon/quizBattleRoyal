package com.quizbattleroyale.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quizbattleroyale.entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    UserRole findByName(String name);
}
