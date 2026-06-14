package com.travely.purchase_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tour_purchase_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourPurchaseToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String touristUsername;

    @Column(nullable = false)
    private Long tourId;

    @Column(nullable = false)
    private String tourName;

    @Column(nullable = false)
    private LocalDateTime purchasedAt;
}
