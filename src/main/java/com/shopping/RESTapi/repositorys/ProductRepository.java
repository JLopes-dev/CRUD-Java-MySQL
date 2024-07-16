package com.shopping.RESTapi.repositorys;

import com.shopping.RESTapi.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
