package com.example.retornosAPI.dtos;

import com.example.retornosAPI.models.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

public class ProductRequestDTO {

    @NotEmpty(message = "O nome não pode ser vazio.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String name;

    @DecimalMin(value = "0.01", message = "O valor deve ser maior que 0!")
    private Double price;

    @NotNull(message = "A quantidade não pode ser nula.")
    @Min(value = 0, message = "A quantidade em estoque deve ser maior ou igual a 0.")
    private int stockQuantity;

    @Enumerated(EnumType.STRING)
    private Category category;

    public ProductRequestDTO(Long id, String name, Double price, int stockQuantity, Category category) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
