package com.travely.purchase_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String touristUsername;

    @Column(nullable = false)
    private Double totalPrice;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();

    public void recalculateTotal() {
        this.totalPrice = items.stream().mapToDouble(OrderItem::getPrice).sum();
    }
}
