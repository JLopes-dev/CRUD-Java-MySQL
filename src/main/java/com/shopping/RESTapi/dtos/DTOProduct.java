package com.shopping.RESTapi.dtos;

import com.shopping.RESTapi.enums.ProductType;
import com.shopping.RESTapi.models.Product;

public record DTOProduct(
        String name,
        Double price,
        Integer quantity,
        ProductType productType
) {

    public DTOProduct(Product data){
        this(data.getName(), data.getPrice(), data.getQuantity(), data.getProductType());
    }

}
