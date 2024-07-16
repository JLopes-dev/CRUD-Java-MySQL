package com.shopping.RESTapi.controllers;


import com.shopping.RESTapi.DTOs.DTOProduct;
import com.shopping.RESTapi.models.Product;
import com.shopping.RESTapi.repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository repository;

    @GetMapping
    public Page<Product> returnAllProducts(@PageableDefault(size = 20, sort = "name") Pageable pageable){
        return repository.findAll(pageable);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> registerProduct(@RequestBody DTOProduct data){
        repository.save(new Product(data));
        return ResponseEntity.status(201).body("Successfully created product");
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> updateOneProduct(@PathVariable Long id, @RequestBody DTOProduct data){
        Optional<Product> product = repository.findById(id);
        if (product.isPresent()){
            product.get().updateProduct(data);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).body("Product not found");
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteOneProduct(@PathVariable Long id){
        Optional<Product> product = repository.findById(id);
        if (product.isPresent()){
            repository.deleteById(product.get().getId());
            return ResponseEntity.status(200).body("Product deleted successfully");
        }
        return ResponseEntity.status(404).body("Product not found");
    }

}
