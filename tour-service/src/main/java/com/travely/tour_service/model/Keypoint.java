package com.travely.tour_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "keypoints")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Keypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tourId;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String image;
}
