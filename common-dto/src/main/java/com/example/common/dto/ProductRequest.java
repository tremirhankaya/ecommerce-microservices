package com.example.common.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ProductRequest {

    @NotBlank(message = "name cannot be blank")
    private String name;

    @Size(max = 1000, message = "description max 1000 chars")
    private String description;

    @NotNull(message = "price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "price must be >= 0")

    private BigDecimal price;

    @NotNull(message = "stock is required")
    @Min(value = 0, message = "stock must be >= 0")
    private Integer stock;

    // getters & setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
