package com.shopping.RESTapi.product;

public record DTOProduct(
        String name,
        Double price,
        Integer quantity,
        ProductType product_type
) {
}
