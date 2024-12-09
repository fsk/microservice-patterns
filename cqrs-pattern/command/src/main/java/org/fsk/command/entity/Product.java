package org.fsk.command.entity;

import java.math.BigDecimal;

import lombok.Builder;
import org.fsk.command.entity.enums.ProductCategory;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    
    @Builder.Default
    private boolean active = true;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

}
