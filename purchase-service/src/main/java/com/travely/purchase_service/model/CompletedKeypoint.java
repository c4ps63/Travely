package com.travely.purchase_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "completed_keypoints")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompletedKeypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "execution_id", nullable = false)
    @JsonIgnore
    private TourExecution execution;

    @Column(nullable = false)
    private Long keypointId;

    @Column(nullable = false)
    private LocalDateTime completedAt;
}
