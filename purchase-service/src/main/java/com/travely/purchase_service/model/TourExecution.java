package com.travely.purchase_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tour_executions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String touristUsername;

    @Column(nullable = false)
    private Long tourId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExecutionStatus status;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private LocalDateTime lastActivity;

    @OneToMany(mappedBy = "execution", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CompletedKeypoint> completedKeypoints = new ArrayList<>();
}
