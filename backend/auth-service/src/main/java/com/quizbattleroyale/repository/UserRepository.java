package com.quizbattleroyale.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quizbattleroyale.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

    @Modifying
    @Transactional
    @Query("update User u set u.refreshToken = :refreshToken where u.id = :userId")
    void setRefreshToken(@Param("userId") UUID userId, @Param("refreshToken") String refreshToken);

    @Query("select u.refreshToken from User u where u.id = :userId")
    Optional<String> getRefreshToken(@Param("userId") UUID userId);
}
