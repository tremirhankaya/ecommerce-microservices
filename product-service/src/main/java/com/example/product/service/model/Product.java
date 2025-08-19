package com.example.product.service.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;


@Entity
@Table(name = "products")
@Getter @Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(nullable = false)
    private
    String name;
    @Column(nullable = false)
    private
    String description;
    @Column(nullable = false)
    private
    BigDecimal price;
    @Column(nullable = false)
    private
    int stock;


    public Product() {

    }
    public Product(String name, String description, BigDecimal price, int stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

}
