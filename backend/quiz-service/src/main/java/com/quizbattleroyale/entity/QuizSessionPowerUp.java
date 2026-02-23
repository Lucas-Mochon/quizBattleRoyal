package com.quizbattleroyale.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "quiz_session_power_ups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizSessionPowerUp {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_session_id", nullable = false)
    private QuizSession quizSession;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "power_up_id", nullable = false)
    private PowerUp powerUp;
    
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    
    @Column(name = "used_at")
    private LocalDateTime usedAt;
}
