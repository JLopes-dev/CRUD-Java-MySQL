package com.shopping.RESTapi.dtos;

import com.shopping.RESTapi.enums.ProductType;

public record DTOProduct(
        String name,
        Double price,
        Integer quantity,
        ProductType product_type
) {
}
