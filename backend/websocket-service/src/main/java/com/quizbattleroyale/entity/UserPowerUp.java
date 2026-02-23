package com.quizbattleroyale.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_power_ups", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "power_up_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPowerUp {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    
    @Column(name = "power_up_id", nullable = false)
    private UUID powerUpId;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "last_used_at")
    private LocalDateTime lastUsedAt;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
