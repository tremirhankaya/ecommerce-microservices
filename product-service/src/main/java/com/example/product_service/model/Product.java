package com.example.product_service.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;


@Entity
@Table(name = "products")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String description;
    @Column(nullable = false)
    BigDecimal price;
    @Column(nullable = false)
    int stock;
}
