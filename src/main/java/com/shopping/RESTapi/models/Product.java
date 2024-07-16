package com.shopping.RESTapi.models;

import com.shopping.RESTapi.dtos.DTOProduct;
import com.shopping.RESTapi.enums.ProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "products")
@Entity(name = "Product")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private ProductType product_type;

    public Product(DTOProduct data) {
        this.name = data.name();
        this.price = data.price();
        this.quantity = data.quantity();
        this.product_type = data.product_type();
    }

    public void updateProduct(DTOProduct data){
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.price() != null) {
            this.price = data.price();
        }
        if (data.quantity() != null){
            this.quantity = data.quantity();
        }
        if (data.product_type() != null){
            this.product_type = data.product_type();
        }
    }
}


