package com.quizbattleroyale.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "quiz_session_participants", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "quiz_session_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizSessionParticipant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_session_id", nullable = false)
    private QuizSession quizSession;
    
    @Column(name = "is_winner")
    private Boolean isWinner;
    
    @Column(name = "score", nullable = false)
    private Integer score;
}
