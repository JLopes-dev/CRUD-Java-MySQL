package com.shopping.RESTapi.controllers;


import com.shopping.RESTapi.dtos.DTOProduct;
import com.shopping.RESTapi.models.Product;
import com.shopping.RESTapi.repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository repository;

    @GetMapping
    public ResponseEntity<Page<Product>> returnAllProducts(@PageableDefault(size = 20, sort = "name") Pageable pageable){
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @PostMapping
    @Transactional
    public ResponseEntity registerProduct(@RequestBody DTOProduct data, UriComponentsBuilder uriBuilder){
        Product productCreated = repository.save(new Product(data));
        URI uri = uriBuilder.path("/product/{id}").buildAndExpand(productCreated.getId()).toUri();
        return ResponseEntity.created(uri).body(new DTOProduct(productCreated));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateOneProduct(@PathVariable Long id, @RequestBody DTOProduct data){
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()){
            return ResponseEntity.status(404).body("Product not found");
        }
        product.get().updateProduct(data);
        return ResponseEntity.ok(new DTOProduct(product.get()));
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteOneProduct(@PathVariable Long id){
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()){
            return ResponseEntity.status(404).body("Product not found");
        }
        repository.deleteById(product.get().getId());
        return ResponseEntity.noContent().build();
    }

}
