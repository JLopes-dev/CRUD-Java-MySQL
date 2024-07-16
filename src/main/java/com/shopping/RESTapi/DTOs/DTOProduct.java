package com.shopping.RESTapi.DTOs;

import com.shopping.RESTapi.Enums.ProductType;

public record DTOProduct(
        String name,
        Double price,
        Integer quantity,
        ProductType product_type
) {
}
