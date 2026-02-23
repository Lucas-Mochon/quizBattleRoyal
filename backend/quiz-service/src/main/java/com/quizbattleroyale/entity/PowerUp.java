package com.quizbattleroyale.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "power_ups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PowerUp {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "name", length = 150, nullable = false, unique = true)
    private String name;
    
    @Column(name = "effect", length = 255, nullable = false)
    private String effect;
    
    @Column(name = "effect_duration")
    private LocalTime effectDuration;
    
    @Column(name = "picture")
    @Lob
    private byte[] picture;
    
    @Column(name = "reload_time")
    private LocalTime reloadTime;
    
    @Column(name = "rarity", length = 50)
    private String rarity;
    
    @Column(name = "max_per_game")
    private Integer maxPerGame;
    
    @Column(name = "is_passive", nullable = false)
    private Boolean isPassive;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
